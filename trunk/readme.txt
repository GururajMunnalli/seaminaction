Seam in Action - Book source code
=================================
SVN snapshot

Getting started
---------------

Please refer to Appendix A for an orientation of the directory structure used
for the source code that accompanies this book. Please refer to Appendix B for
how to get JBoss AS, JBoss Seam, and other supporting software installed.  By
"installed", I mean nothing more than to download and extract. No system-level
changes are required. That is the beauty of Java after all. 

Expected versions to retrieve:

   JBoss AS - 4.2.2.GA
   JBoss Seam - 2.0.0.GA
   Java SE - Sun JDK 1.6.0 

After you have JBoss AS and JBoss Seam prepared, you need to update Seam to fix
a problem with the H2 database support. Please see the section below named
"Patching Seam to fix H2 support" for instructions.

-------------------------------------------------------------------------------
Please note that it is important to use Sun Java 6 when running seam-gen if you
want to get the same results as what is found in the example source code. The
reason is that Hibernate does not explicitly sort the entities that it derives
from the database, so the sort is JVM specific. It's not a huge deal, but if
you are confused as to why there are differences, that is likely the cause.
-------------------------------------------------------------------------------

The stages/ directory contains a snapshot of the projects at the end of each
chapter. Each projects-chxx folder contains a file named
chapter-developments.txt which describes what setup was done and what changes
were made to the application in that chapter.

Before you can use these applications, you must "inflate" them with
the jar files from the Seam distribution (this is done to keep the source code
download size to a minimum).

Ensure that the property seam.runtime in build.xml points to your Seam
distribution. Then run:

   ant inflate-all

To start using a project, copy it (or use a symlink) from the stages/ directory
into the projects/ directory. The projects/ directory serves as your workspace.

Several configuration files refer to the home directory /home/twoputt. You will
need to update those references in order to deploy the application property.
The files in each project that you need to update are:

   build.properties (location of JBoss AS)
   hibernate-console.properties (used for Eclipse Hibernate Console plugin)
   resources/*-ds.xml

Course directory databases for Open18
--------------------------------------

Chapter 2 mentions that the DBA provides you with an H2 database that will be
used to create the course directory module of the Open18 application.  Those
two archives are located in the databases/ directory.

   open18-db-initial-empty.tar.gz - A database with only the golf course directory schema

   open18-db-initial-seeded.tar.gz - A fully populated golf course directory database (26 facilities/courses)

Both of these archives will extract to a folder named open18-db.

You can use either one of these archives, depending on whether you want seed
data in the database or not. The DDL file is also included in this directory,
which you can use as a reference to prepare the schema for your database of
choice.

If you want to create the database from scratch, the following two commands
will initialize the database and load the seed data:

   java -cp /home/twoputt/lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:/home/twoputt/databases/open18-db/h2 -user open18 -password tiger -script /home/twoputt/etc/schema/open18-initial-schema.sql
   java -cp /home/twoputt/lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:/home/twoputt/databases/open18-db/h2 -user open18 -password tiger -script /home/twoputt/etc/schema/open18-seed-data.sql

Configuring JBoss AS
--------------------

JBoss AS should be download and extracted to:

   /home/twoputt/opt/jboss-as-4.2.2.GA

You should also copy the custom launch configuration file into the JBoss AS
installation directory:

  cp /home/twoputt/etc/jboss-as-conf/run.conf /home/twoputt/opt/jboss-as-4.2.2.GA/bin/

This launch configuration provides memory settings that should avoid permgen
errors when running on a Sun JVM and it also properly sets the JBOSS_HOME
environment variable.

Patching Seam to fix H2 support
-------------------------------

After extracting the JBoss Seam 2.0.0.GA distribution, you need to update the
hibernate-tools.jar used by seam-gen so that the reverse engineering of an H2
database works properly:

   cp /home/twoputt/lib/hibernate-tools.jar /home/twoputt/opt/jboss-seam-2.0.0.GA/seam-gen/lib

Now seam-gen is ready to use with H2.
