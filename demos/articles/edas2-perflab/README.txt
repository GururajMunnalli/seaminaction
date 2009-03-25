= Getting started =

To get started, run the following command to populate the lib directory with
the required library dependencies:

  ant inflate

Next, update the location of your JBoss AS installation in build.properties:

  jboss.home=/path/to/jboss-as

Update the location to your project in resources/edas2-perflab-dev-ds.xml

  jdbc:h2:file:/path/to/edas2-perflab/database/h2 

Then deploy the application to JBoss AS as an exploded archive:

  ant explode

Finally, start JBoss AS and pull up the application in your browser:

  /path/to/jboss-as/bin/run.sh (or run)

The database will be populated with sample data when the application starts.

= Database notes =

Please note that this demo was originally developed against the EDAS2 Oracle
database schema. Therefore, some of the references, such as the reverse
engineering configuration, pertains to the use of that schema. The demo is
setup to run against the embedded H2 database, which is created automatically
when the application starts up.

= Ivy notes =

By setting sync="true" on the ivy:retrieve Ant task, Ivy will not only copy the
necessary files, but it will also remove the files which do not need to be
there.

Can save disk-space by creating symbolic links by setting symlink="true" on the
ivy:retrieve Ant task. The default behavior is to copy the artifacts.
