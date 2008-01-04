Seam in Action - Book source code
=================================
SVN snapshot

Book: Seam in Action, Manning Publications
Author: Dan Allen
Project site: http://code.google.com/p/seaminaction

Getting started
---------------

Please refer to appendix A for information about how to get JBoss AS, JBoss
Seam, and other supporting software installed.  By "installed", I mean nothing
more than to download and extract. No system-level changes are required. That
is the beauty of Java after all. 

Expected versions to retrieve:

   JBoss AS - 4.2.2.GA
   JBoss Seam - 2.0.1.CR1
   Java SE - Sun JDK 1.6.0 

** IMPORTANT **
Make sure that your JBoss AS directory is jboss-as-4.2.2.GA and not
jboss-4.2.2.GA. I have renamed it to better distinguish it from Seam,
which is also a JBoss project.
** IMPORTANT **

The stages/ directory contains a snapshot of the projects at the end of each
chapter. Each projects-chXX folder contains a file named
chapter-developments.txt which describes what setup was done and what changes
were made to the application in that chapter.

Before you can use these applications, you must "inflate" them with the jar
files from the Seam distribution (this is done to keep the source code download
size to a minimum). The h2.jar will also be copied to the JBoss AS installation
during this process, though the ./seam setup command will also perform this
task.

To inflate the project stages, ensure that the property seam.runtime in
build.xml points to your Seam distribution, then run:

   ant inflate-all

To start using a project, copy it (or use a symlink) from the stages/ directory
into the projects/ directory. The projects/ directory serves as your workspace.

Several configuration files refer to the home directory /home/twoputt. You will
need to update those references in order to deploy the application property.
The files in each project that you need to update are:

   build.properties (location of JBoss AS)
   hibernate-console.properties (used for Eclipse Hibernate Console plugin)
   resources/*-ds.xml
   resources/glassfish-datasource.xml

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

If you want to create the database from scratch, run the following two commands
from this directory to initialize the database and load the seed data:

   java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 -user open18 -password tiger -script etc/schema/open18-initial-schema.sql
   java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 -user open18 -password tiger -script etc/schema/open18-seed-data.sql

Configuring JBoss AS
--------------------

JBoss AS should be download and extracted to:

   /home/twoputt/opt/jboss-as-4.2.2.GA

You should also copy the custom launch configuration file into the JBoss AS
installation directory:

  cp etc/jboss-as-conf/run.conf jboss-as-4.2.2.GA/bin/

This launch configuration provides memory settings that should avoid permgen
errors when running on a Sun JVM and it also properly sets the JBOSS_HOME
environment variable.

