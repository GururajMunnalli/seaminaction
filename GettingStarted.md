# Introduction #

First of all, I encourage you (read as "urge you") to [purchase](http://mojavelinux.com/seaminaction) the book to get all of the gory details on how to set up, develop, and deploy the sample application. That being said, even once you have the book, there is going to be extra information that I am going to want to relate to you. I will do that on this wiki, starting with this introductory page.

## Prerequisites ##

  * Java SE Development Kit (JDK) 5.0 (or better)
  * JBoss Application Server (AS) 4.2.2.GA (or better)
  * Seam 2.0.0.GA (or better, though example doesn't fully support 2.1 yet)
  * Apache Ant 1.7.0 (to execute the build from the project)
  * IDE - Eclipse, NetBeans, or IntelliJ IDEA (to follow along with chapter 2)

Before you scour the web to find this software, you should know that the build script that comes with the example code includes targets which automatically download and extract JBoss AS and Seam. And although the script is an Ant build script, you don't need Ant initially since it comes bundled with the example code. The only hard requirement is that you must have a Java SE Development Kit (JDK) installed.

## Verifying your Java installation ##

In order to work with any of the software discussed in the book, you must have a Java SE Development Kit (JDK) installed. Note that there is a distinction between a Java **Development Kit** and a Java **Runtime**. If you have things setup right, you should be able to open a command window and execute the following two commands:

```
java -version
javac -version
```

Both commands should complete successfully and report the same version of Java. You should also verify that you have the JAVA\_HOME environment variable set, which is required by some Java software like Ant. Google should be able to point you in the [right direction](http://www.atlassian.com/software/jira/docs/latest/java.html).

## Obtaining the source code ##

You can obtain the example code by going to [Project Home](http://code.google.com/p/seaminaction/) and clicking on the Featured Download. That is where you will find the official release.

Of course, I'll likely still tinker with the software as I discover bugs or new ideas. I also have plans to provide a migration path to Seam 2.1. Therefore, if you want to spy on me as I work, you can grab the source from SVN. To learn how to check out the project from SVN, click on the [Source](http://code.google.com/p/seaminaction/source) tab.

## Source code layout ##

I'm sure you are eager to get going. However, before jumping into things, I want to give you an overview of how the example code is structured so you understand the instructions further down. I'll first introduce you to the idea of the "Home" directory, which I refer to several times in the book and then where the example files go within it.

### _The directory you call "home"_ ###

Your home directory is your development root. If this happens to be your operating system home directory, then the last path in the directory is typically the same as your username. The book uses the home directory of a fictional developer, Tommy Twoputt, whose username is twoputt. He is also appears as the fictional golfer in the book. You might recognize his avatar:

![http://seaminaction.googlecode.com/svn/wiki/images/twoputt-avatar.png](http://seaminaction.googlecode.com/svn/wiki/images/twoputt-avatar.png)

Within the text, the home directory will be referred to as ${user.home} to make it generic across systems. The terminal output included in the listings has been generated on a Linux system, but you can look beyond this detail since it makes no difference which operating system you use for developing Seam applications.

The table below shows the home directory for twoputt as it would appear on several different operating systems. Whenever you see twoputt's home directory used in the book, replace it with your own home directory.

| **Operating system** | **Home area** |
|:---------------------|:--------------|
| Linux | /home/twoputt |
| Max OSX | /Users/twoputt |
| Windows | C:/twoputt |

If this configuration is not how you want to setup your example code, don't worry. The script included with the example code has a target that allows you change the "Home" directory to any directory of your choice. In the end, the "Home" directory is nothing more than the directory that you extracted the source code.

Notice that I use the forward slash for the path on Windows. Backslash (\) is an escape character in Java. Fortunately, Java transposes the slash according to the system it's running on, so you can avoid the hassle of working around the Java backslash (i.e., using a double backslash) by always using forward slashes in paths.

### _Structuring your home_ ###

I make recommendations about where to put libraries and how to organize your development space. You don't have to take these recommendations. But being a reader, I have always found source code distributions from books to be cold and disconnected.

As you add new software, your home area can very quickly become chaotic. To help you keep things in order, I recommend a structure that I adhere to throughout the book. The table below lists several folders, along with their purpose, that I like to set up when doing development. You will recognize these directories from the book's source code.

| **Folder** | **What it contains** |
|:-----------|:---------------------|
| databases | File-based databases for H2 |
| etc | Database scripts, extra configuration files |
| lib | JAR files not included with Seam, such as H2 driver and Ant |
| opt | Directory where third-party applications are extracted, such as JBoss AS and Seam |
| projects | The workspace home where you can have seam-gen create projects |
| prototype | My working version of the applications |
| stages | Snapshot of projects at the end of each chapter or part |

Appendix A shows you how to get all of the software installed that is needed to use the examples in this book, including Seam. After all of the downloading is complete, your home directory should contain the directories listed in table below, populated with the required software:

```
twoputt/
|-- databases/
|   `-- open18-db/
|-- lib/
|   `-- h2.jar
|-- opt/
|   |-- apache-ant-1.7.0/
|   |-- jboss-as-4.2.2.GA/
|   `-- jboss-seam-2.0.3.CR1/
|-- stages/
|   |-- projects-part1
|   |-- projects-ch03
|   |-- ...
`-- projects/
    `-- open18/
```

The location of individual applications will be referred to throughout the book using a variable notion. For instance, the JBoss AS directory is tokenized as ${jboss.home} and the Seam distribution as ${seam.dist}.

## Getting started ##

Here are the tasks that you will likely need to do to get started:

  1. Replace references to /home/twoputt with your home folder
  1. Download Seam and JBoss AS
  1. Seed the H2 database for Open 18
  1. Launch the H2 administration console
  1. Add the required libraries to a select project in the stages directory
  1. Deploy the selected project

You can accomplish all of these tasks (with the exception of the last one) using the Ant build script in the root of the example code. I have even provided the Ant distribution so that you don't need to have it installed on your system (though, as mentioned earlier, you do have to have Java properly installed).

To discover which tasks you can execute, execute from the root of the example code:

```
ant -projecthelp
```

The commands of most interest are shown here:

| rehome | Replace any absolute paths with the current directory (or -Dnew.home=XXX argument) |
|:-------|:-----------------------------------------------------------------------------------|
| get-seam | Downloads Seam and extracts it to the software directory (i.e., opt) |
| get-jboss-as | Downloads JBoss AS and extracts it to the software directory (i.e., opt) |
| build-db | Build and seed the initial H2 database for Open 18 |
| update-project | Update the libraries for the selected project (menu provided) |
| quickstart | Perform all the initial tasks to get started (run rehome first) |

You run each command by prefix it with ant, as shown here:

```
ant get-seam
```


---

**IMPORTANT** Make sure that your JBoss AS directory is jboss-as-4.2.2.GA and not jboss-4.2.2.GA. I have renamed it to better distinguish it from Seam, which is also a JBoss project.

---


### Changing the home directory ###

Several configuration files refer to the home directory /home/twoputt. You will
need to update those references in order to deploy the application property.
The files in each project that you need to update are:

  * build.properties (location of JBoss AS)
  * hibernate-console.properties (used for Eclipse Hibernate Console plugin)
  * resources/`*`-ds.xml
  * resources/glassfish-datasource.xml

You can update these files across the entire source tree using the location of
the Seam in Action source code (the current directory) by running the command:

> ant rehome

You can also supply the location where the Seam in Action sample code
resides on your harddrive explicitly using the the -Dnew.home setting:

> ant rehome -Dnew.home=C:/seaminaction

Please use forward slashes, even on Windows, to avoid backslash gymnastics.

### Running a project stage ###

The stages/ directory contains a snapshot of the projects at the end of each chapter (e.g., projects-ch04), or in the case of part 1 and part 3, at the end of the part (e.g., projects-part1). Development is progressive, so the projects accumulate features as the part and chapter numbers increase. If you want to see the application in its entirety, then use projects-ch13.

Each projects-chXX or projects-partX folder contains a file named chapter-developments.txt or part-developments.txt, respectively, which describes what setup was done and what changes were made to the application in that chapter or part.

Before you can use these applications, you must "inflate" them with the jar files from the Seam distribution (this is done to keep the source code download size to a minimum). The h2.jar will also be copied to the JBoss AS installation during this process, though the ./seam setup command also performs this task.

To inflate (or update) a project, ensure that you have downloaded and extracted Seam, then run:

```
ant update-project
```

You will be provided a menu of projects and you can choose which one to update. Once the project is inflated, you can use your IDE to open the project as described in chapter 2.

Assuming that you started with the command rehome followed by quickstart you
can deploy the selected project with the following command:

> ant -f stages/projects-ch13/open18/build.xml explode

You then start JBoss AS by entering the opt/jboss-as-4.2.2.GA folder and running
one of the following two commands:

> run (Windows)
> ./run.sh (Unix/Linux/Mac)

If you have Ant installed, you can also change to the directory of the project and deploy it by running:

```
ant explode
```

### Seeding the H2 database for Open18 ###

Chapter 2 mentions that the DBA provides you with an H2 database that will be
used to create the course directory module of the Open18 application. Those
two archives are located in the databases/ directory.

  * open18-db-initial-empty.tar.gz - A database with only the golf course directory schema
  * open18-db-initial-seeded.tar.gz - A fully populated golf course directory database (26 facilities/courses)

Both of these archives will extract to a folder named open18-db.

You can use either one of these archives, depending on whether you want seed
data in the database or not. The DDL file is also included in this directory,
which you can use as a reference to prepare the schema for your database of
choice.

If you want to create the database from scratch, run the following command:

```
ant build-db
```

This target will execute the following two commands to initialize the database
and load the seed data:

```
java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 \
  -user open18 -password tiger -script etc/schema/open18-initial-schema.sql
java -cp lib/h2.jar org.h2.tools.RunScript -url jdbc:h2:file:databases/open18-db/h2 \
  -user open18 -password tiger -script etc/schema/open18-initial-seed-data.sql
```

### Using MySQL instead of H2 ###

Of course, you can use any database supported by Hibernate (or the JPA provider of your choice) to develop a database-driven Seam application. But each database requires its own unique schema. That's why I have provided a schema for MySQL in addition to the H2 schema, which can be found in the etc/schema directory of the example code. I'll describe the changes you need to make to switch the application over to MySQL.

First, make sure that you have MySQL installed. Next, create a database named `open18` and create a user by the same name with the password `tiger` (just to be consistent).

Next, import the Open 18 schema for MySQL into the `open18` database using the following command:

```
mysql -u open18 -p open18 < etc/schema/open18-initial-schema-mysql.sql
```

Once the schema is imported, you can see it with the set of facilities:

```
mysql -u open18 -p open18 < etc/schema/open18-initial-seed-data-mysql.sql
```

As you progress through the application, you can find additional seed data for MySQL in the etc/schema directory.

To get the application to use the MySQL database instead of the H2 database, you only have to make two changes. First, open up the resources/open18-dev-ds.xml file in the open18 for any stage and modify the `<local-tx-datasource>` element as follows:

```
<local-tx-datasource>
    <jndi-name>open18Datasource</jndi-name>
    <use-java-context>false</use-java-context>
    <connection-url>jdbc:mysql://localhost/open18</connection-url>
    <driver-class>org.mysql.jdbc.Driver</driver-class>
    <user-name>open18</user-name>
    <password>tiger</password>
</local-tx-datasource>
```

Next, open up the resources/META-INF/persistence-dev-war.xml file and comment out the `hibernate.dialect` property (Hibernate will figure it out automatically):

```
<!-- <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/> -->
```

You could also set it to `org.hibernate.dialect.MySQL5InnoDBDialect`.

You should also make sure that the `hibernate.hbm2ddl.auto` property is set to update so that the database will be updated as you add new JPA entities in later chapters:

```
<property name="hibernate.hbm2ddl.auto" value="update"/>
```

Now you are using MySQL!

### Configuring JBoss AS ###

JBoss AS should be download and extracted to:

```
/home/twoputt/opt/jboss-as-4.2.2.GA
```

You should also copy the custom launch configuration file and the Log4j configuration into the JBoss AS installation directory:

```
cp etc/jboss-as-conf/run.conf jboss-as-4.2.2.GA/bin/
cp etc/jboss-as-conf/jboss-log4j.xml jboss-as-4.2.2.GA/server/default/conf/
```

The launch configuration provides memory settings that should avoid permgen errors when running on a Sun JVM and it also properly sets the JBOSS\_HOME environment variable.

The custom log4j configuration should speed up JBoss AS significantly while not taking away any important information your application has to tell you.

Fortunately, these steps are handled for you by using the following command:

```
ant get-jboss-as
```


---

**IMPORTANT** Make sure that the H2 version in server/default/lib is the same as the one you used to build the H2 database. Otherwise, strange errors may occur. seam-gen copies h2.jar to server/default/lib during ./seam setup, but if you upgrade later the h2.jar on the JBoss server could be out of date.

---


### Launching the H2 console ###

You can launch the administration console for the H2 database with this command:

```
ant launch-h2-console
```

The admin console is started by executing the Server class from the H2 JAR file in web mode:

```
java -cp lib/h2.jar org.h2.tools.Server -web
```

You also have the option of executing the Console class, which will put an entry in the status bar, allowing you to shutdown the application more gracefully:

```
java -cp lib/h2.jar org.h2.tools.Console
```

Either command will instruct you to open the H2 console URL in your browser:

```
http://localhost:8082
```

Of course, if you follow along with chapter 2, you actually don't start with the stages but rather from an existing database and a blank project workspace.