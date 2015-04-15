# Overview #

In versions prior to Seam 2.1.2.GA, seam-gen creates projects that deploy to JBoss AS out of the box. However, a Seam application can run on any Java EE application server or servlet container with the proper configuration. This section explains how to configure a seam-gen project to simultaneously support deployment to JBoss AS and GlassFish, as well as be able to start and stop the GlassFish server. The instructions here pertain to a seam-gen WAR project.

You can also watch two presentations I gave on Aquarium TV covering things you need to know about running [Seam on GlassFish](http://wikis.sun.com/display/TheAquarium/Seam+and+WebBeans). I talk specifically about EAR projects in those talks.

But if you just want to get GlassFish support without doing any work at all, just check out Seam 2.1.2.GA (or the SVN trunk if Seam 2.1.2.GA has not yet been released). seam-gen now supports deployment to both JBoss AS and GlassFish out of the box. I talk about the upgrade in [this blog entry](http://in.relation.to/Bloggers/JBossAS5AndGlassFishSupportAddedToSeamgen).

## Background ##

All of the GlassFish-related commands use the prefix `gf-`. You use these commands exclusively when controlling and deploying to GlassFish. However, the semantics of the commands changes a bit. See the Commands section below for details.

## Preparation ##

To get started with GlassFish, you must have the GlassFish distribution. The deployment has been tested on GlassFish V2. Once the distribution is extracted, set the glassfish.home and glassfish.domain properties in build.properties.

```
glassfish.home=???
glassfish.domain=???
```

Don't worry, the build will tell you if they are not set ;)

Next, you need to grab the build file with the GlassFish commands and also the modified build.xml that imports this extended build file. The file [glassfish-build.xml](http://seaminaction.googlecode.com/svn/trunk/etc/glassfish-deployment) is stored in the Seam in Action SVN repository. You import it by putting the following Ant import command outside of the project target:

```
<import file="${basedir}/glassfish-build.xml"/>
```

Before deploying the project to GlassFish, Hibernate needs to be deployed to GlassFish as a JPA provider. (It's possible to use TopLink Essentials as the JPA provider, which is bundled with GlassFish, but the fact that that provider does not support MANUAL flushing doesn't fit with how seam-gen generates code).

```
ant gf-prepare
```

At this point, you are ready to deploy your project (open18).

## Changes to project files ##

GlassFish uses a different JNDI pattern than JBoss AS. (Truthfully, JBoss AS uses a strange proprietary prefix that is best to disable for compatibility). To prep the pattern so that it works on both servers, make the following to change to the -ds.xml files.

  * Add `<use-java-context>false</use-java-context>` to each `<datasource>` definition

Then make the following change in each persistence-`*`.xml file

  * Remove the java:/ prefix where JNDI datasource from the `<jta-data-source>` value

Several changes had to be made to project files that deal with the persistence unit accommodate switching between two servers. First, Ant filter tokens have been added to the following two files:

```
resources/META-INF/persistence-%profile%-war.xml
resources/WEB-INF/components.xml
```

The values for the filter tokens are defined in the Ant build file and are used to customize the Java persistence settings and load behavior. Below are the default values, which are defined just inside the `war` target and used in a JBoss AS deployment.

```
<property name="seam_bootstrap_pu" value="true"/>
<property name="seam_emf" value="#{entityManagerFactory}"/>
<property name="pu_jndi_name" value=""/>
<property name="transaction_manager_lookup_class" value="org.hibernate.transaction.JBossTransactionManagerLookup"/>
<property name="show_sql" value="true"/>
```

For GlassFish, overrides are established in the `gf-init` target. These overrides are as follows:

```
<property name="transaction_manager_lookup_class" value="org.hibernate.transaction.SunONETransactionManagerLookup"/>
<property name="seam_bootstrap_pu" value="false"/>
<property name="seam_emf" value="#{null}"/>
<property name="pu_jndi_name" value="java:comp/env/${project.name}/emf"/>
```

These properties are applied using the Ant `<filterset>` command in two places. First, when copying the persistence unit descriptor inside the `war` target:

```
<copy tofile="${war.dir}/WEB-INF/classes/META-INF/persistence.xml" 
  file="${basedir}/resources/META-INF/persistence-${profile}-war.xml"
  overwrite="true">
  <filterset>
    <filter token="transaction_manager_lookup_class" value="${transaction_manager_lookup_class}"/>
    <filter token="show_sql" value="${show_sql}"/>
  </filterset>
</copy>
```

The also need to be appended to the `<filterset>` used when copying the resources/WEB-INF folder, also inside the `war` target:

```
<copy todir="${war.dir}/WEB-INF">
  <fileset dir="${basedir}/resources/WEB-INF">
    ...
  </fileset>
  <filterset>
    ...
    <filter token="seam_bootstrap_pu" value="${seam_bootstrap_pu}"/>
    <filter token="seam_emf" value="${seam_emf}"/>
    <filter token="pu_jndi_name" value="${pu_jndi_name}"/>
  </filterset>
</copy>
```

As you see in the pu\_jndi\_name property, we are using the standard Java EE mechanism to load the persistence unit when deploying to GlassFish. To support that, a persistence unit resource reference had to be added to resources/WEB-INF/web.xml.

```
<persistence-unit-ref>
  <persistence-unit-ref-name>open18/emf</persistence-unit-ref-name>
  <persistence-unit-name>open18</persistence-unit-name>
</persistence-unit-ref>
```

The presence of this resource reference does not affect JBoss AS 4.x.

## Deployment ##

Deployment followed by hot deploy are two different targets under GlassFish, whereas there is only a single target for JBoss AS. First, you need to deploy the exploded archive:

```
ant gf-explode
```

Any change you make to a static web resource or Facelets template is picked up automatically, there is no need to explode again like with JBoss AS. You do need to run a target to hot deploy Java components:

```
ant gf-hotdeploy
```

When you are all done for the day, you can undeploy. In GlassFish, undeploy is
the same regardless of whether you are running an exploded or packaged archive:

```
ant gf-undeploy
```

That's all folks! Happy coding.

## Command reference ##

```
gf-start - Starts GlassFish
gf-stop - Stops GlassFish
gf-restart - Restarts GlassFish
gf-datasource - Registers the datasource and connection pool for the active environment
gf-explode - Deploys the exploded archive to GlassFish (initial)
gf-hotdeploy - Hot deploys Java classes and components
gf-deploy - Deploys the packaged archive to GlassFish
gf-undeploy - Undeploys the exploded or packaged archive from GlassFish
gf-prepare - Prepares GlassFish for a seam-gen project deployment (calls gf-deploy-hibernate)
gf-deploy-hibernate - Deploys Hibernate as a JPA provider to GlassFish
```