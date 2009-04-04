Seam in Action - Book source code
=================================
SVN snapshot

Book: Seam in Action, Manning Publications
Author: Dan Allen
Book site: http://manning.com/dallen
Project site: http://code.google.com/p/seaminaction

Getting started
---------------

Here are the tasks that you will likely need to do to get started:

 - Download Seam and JBoss AS
 - Add the required libraries to the example projects
 - Replace references to /home/twoputt with your home folder
 - Seed the H2 database for Open 18 (lauch the H2 admin console to browse)
 - Update a project stage and run it

You can accomplish all of these tasks using the Ant build script in this
folder. I have even provided the ant distribution so that you don't need to
have it on your system (though you do have to have Java properly installed).

To discover which tasks you can execute, start out by typing:

   ant -projecthelp

The commands of most interest are shown here:

   rehome           Replace any absolute paths with the current directory (or -Dnew.home=XXX argument)
   get-seam         Downloads Seam and extracts it to the software directory (i.e., opt)
   get-jboss-as     Downloads JBoss AS and extracts it to the software directory (i.e., opt)
   build-db         Build and seed the initial H2 database for Open 18
   update-project   Update the libraries for the selected project (menu provided)
   quickstart       Executes get-seam, get-jboss-as, build-db, and update-project

You run each command by prefix it with ant, as shown here:

   ant get-seam

Please refer to appendix A for information about how to get JBoss AS, Seam, and
other supporting software installed. By "installed", I mean nothing more than
downloading and extracting. No system-level changes are required. That's the
beauty of Java after all. 

Recommended versions to retrieve:

 - JBoss AS - 4.2.2.GA
 - Seam - 2.0.3.CR1 (until GA is released)
 - Java SE - Sun JDK 5 (or 6)

** IMPORTANT **
Make sure that your JBoss AS directory is jboss-as-4.2.2.GA and not
jboss-4.2.2.GA. I have renamed it to better distinguish it from Seam,
which is also a JBoss project.
** IMPORTANT **

Changing the home directory
---------------------------

Several configuration files refer to the home directory /home/twoputt. You will
need to update those references in order to deploy the application property.
The files in each project that you need to update are:

   build.properties (location of JBoss AS)
   hibernate-console.properties (used for Eclipse Hibernate Console plugin)
   resources/*-ds.xml
   resources/glassfish-datasource.xml

You can update these files across the entire source tree using the location of
the Seam in Action source code (the current directory) by running the command:

   ant rehome

You can also supply the location where the Seam in Action sample code
resides on your harddrive explicitly using the the -Dnew.home setting:

   ant rehome -Dnew.home=C:/seaminaction

Please use forward slashes, even on Windows, to avoid backslash gymnastics.

Running a project stage
-----------------------

The stages/ directory contains a snapshot of the projects at the end of each
chapter (e.g., projects-ch04), or in the case of part 1 and part 3, at the end
of the part (e.g., projects-part1). Development is progressive, so the projects
accumulate features as the part and chapter numbers increase. If you want to
see the application in its entirety, then use projects-ch13.

Each projects-chXX or projects-partX folder contains a file named
chapter-developments.txt or part-developments.txt, respectively, which
describes what setup was done and what changes were made to the application in
that chapter or part.

Before you can use these applications, you must "inflate" them with the jar
files from the Seam distribution (this is done to keep the source code download
size to a minimum). The h2.jar will also be copied to the JBoss AS installation
during this process, though the ./seam setup command also performs this task.

To inflate (or update) a project, ensure that you have downloaded and extracted
Seam, then run:

   ant update-project

You will be provided a menu of projects and you can choose which one to update.
Assuming that you started with the command rehome followed by quickstart you
can deploy the selected project with the following command:

  ant -f stages/projects-ch13/open18/build.xml explode

You then start JBoss AS by entering the opt/jboss-as-4.2.2.GA folder and running
one of the following two commands:

  run (Windows)
  ./run.sh (Unix/Linux/Mac)

Seeding the database for Open18
-------------------------------

Chapter 2 mentions that the DBA provides you with an H2 database that will be
used to create the course directory module of the Open18 application. Those
two archives are located in the databases/ directory.

   open18-db-initial-empty.tar.gz - A database with only the golf course directory schema

   open18-db-initial-seeded.tar.gz - A fully populated golf course directory database (26 facilities/courses)

Both of these archives will extract to a folder named open18-db.

You can use either one of these archives, depending on whether you want seed
data in the database or not. The DDL file is also included in this directory,
which you can use as a reference to prepare the schema for your database of
choice.

If you want to create the database from scratch, run the following command:

   ant build-db

This target will execute the following two commands to initialize the database
and load the seed data:

   java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 \
      -user open18 -password tiger -script etc/schema/open18-initial-schema.sql
   java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 \
      -user open18 -password tiger -script etc/schema/open18-initial-seed-data.sql

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

I have also provided a custom log4j configuration that should speed up JBoss AS
significantly while not taking away any important information your application
has to tell you.

  cp etc/jboss-as-conf/jboss-log4j.xml jboss-as-4.2.2.GA/server/default/conf/

These steps are handled for you by using the following command:

   ant get-jboss-as

*** IMPORTANT ***
Make sure that the H2 version in server/default/lib is the same as the one you
used to build the H2 database. Otherwise, strange errors may occur.
seam-gen copies h2.jar to server/default/lib during ./seam setup, but if you
upgrade later the h2.jar on the JBoss server could be out of date.
*** IMPORTANT ***

Launching the H2 console
------------------------

You can launch the administration console for the H2 database with this command:

   ant launch-h2-console

The admin console is started by executing the Server class from the H2 JAR file
in web mode:

   java -cp lib/h2.jar org.h2.tools.Server -web

You also have the option of executing the Console class, which will put an
entry in the status bar, allowing you to shutdown the application more
gracefully:

   java -cp lib/h2.jar org.h2.tools.Console

Either command will instruct you to open the H2 console URL in your browser:

   http://localhost:8082

