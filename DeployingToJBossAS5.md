# Overview #

JBoss AS 5 is a fully compliant Java EE 5 application server. It was first released to the community in December 2008, post dating the release of the book by several months. This page documents migration problems that may be encountered running the sample application on JBoss AS 5 and guidance on how to get the deployment working.

# Persistence unit problems #

If you take the WAR version of the sample application and attempt to deploy it to JBoss AS 5, you likely get the following exception when you attempt to look at a listing page:

```
Caused by: java.lang.IllegalArgumentException: org.hibernate.hql.ast.QuerySyntaxException: Facility is not mapped [select facility from Facility facility]
```

The problem stems from the fact that JBoss AS 5 handles the deployment of persistence units different than JBoss AS 4. Namely, JBoss AS 5 will load any persistence unit defined in META-INF/persistence.xml automatically. That means when Seam loads the persistence unit, there are now two runtime instances of the persistence unit. (In addition, JBoss AS 5 doesn't scan for entity classes in WEB-INF/classes when bootstrapping the persistence unit manually or in Seam). Either way, you only need to load the persistence unit once.

To fix the problem of how the persistence unit is loaded, make the following changes to the project.

  * Instruct the Hibernate JPA provider to bind the runtime persistence unit to JNDI by adding this property to the resources/META-INF/persistence-`*`.xml files
```
<property name="jboss.entity.manager.factory.jndi.name" value="java:/open18EntityManagerFactory"/>
```
  * Disable the Seam-managed persistence unit in resources/WEB-INF/components.xml (or just remove this component definition altogether)
```
<persistence:entity-manager-factory name="open18EntityManagerFactory" persistence-unit-name="open18" installed="false"/>
```
  * Switch the Seam-managed persistence context to use the persistence unit in JNDI by modifying this component definition in resources/WEB-INF/components.xml
```
<persistence:managed-persistence-context name="entityManager" auto-create="true" entity-manager-factory="#{open18EntityManagerFactory}" persistence-unit-jndi-name="java:/open18EntityManagerFactory"/>
```

This solution may seem a bit "hackish" because you are resorting to the proprietary JNDI binding that I spoke about in chapter 9. If you want to do it right, simply follow the instructions I give in chapter 9 for binding a persistence unit to JNDI on a Java EE 5 compliant server. Namely, define a persistence unit reference in web.xml:

```
<persistence-unit-ref>
    <persistence-unit-ref-name>open18/emf</persistence-unit-ref-name>
    <persistence-unit-name>open18</persistence-unit-name>   
</persistence-unit-ref>
```

Then use the following JNDI name in components.xml to refer to it:

```
java:comp/env/open18/emf
```

Retrieving the persistence unit runtime from JNDI is the only way in JBoss AS 5 to ensure the persistence unit loads once and Seam uses it. I am not sure at this point how to prevent JBoss AS 5 from automatically loading the persistence unit.

GlassFish handles this more correctly, in my mind. If the persistence unit reference is not defined in web.xml, then GlassFish doesn't load the persistence unit.

## Further explanation ##

The reason this problem crops up is because Seam was designed under the assumption that the application server would not load persistence units in a WAR project (I talk about this in depth in chapter 9). JBoss AS 5 is compliant with Java EE 5 and therefore does load the persistence unit (well, the spec is loose here and JBoss AS 5 doesn't wait for a persistence unit reference to give the green light).

## Resources ##

  * [Running Seam Examples on JBoss AS](http://www.seamframework.org/Documentation/RunningSeamExamplesWithJBossApplicationServer5) - This page on the seamframework.org Knowledge Base gives the latest status of running the Seam distribution examples on JBoss AS. You'll notice that most problems surround the persistence issue described above.