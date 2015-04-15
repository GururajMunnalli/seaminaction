# Introduction #

The sample application for Seam in Action uses the H2 database. Unfortunately, H2 is not recognized out of the box by Eclipse/JBossTools. This page describes how to register the H2 driver and use it to create a connection.

# Steps #

When using the new Seam Web Project wizard in JBoss Tools, you will arrive at the screen that asks you to select the database and connection type. H2 is now supported as a database type, but not yet as a standard driver with Eclipse. Therefore, you need to click on the "New..." button next to the "Connection Profile..." label.

![http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step0.png](http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step0.png)

In the window that comes up, define new connection type named "H2" from "Generic JDBC"

![http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step1.png](http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step1.png)

On next screen, click the button to define a "New Driver Definition"
On the screen that pops up, click on "Generic JDBC Driver" and rename to "H2"

![http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step2.png](http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step2.png)

Click on the "Jar List" tab and add the H2 JAR to the list

![http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step4.png](http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step4.png)

On the "Properties" tab you can set the following defaults, most importantly the driver class:

![http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step5.png](http://seaminaction.googlecode.com/svn/wiki/images/h2-connection-jbtools/step5.png)

Click "OK"

Now you should be able to select H2 from the list of Drivers
Enter the proper JDBC URL for H2 (somethink like jdbc:h2:[file:///home/twoputt/databases/open18-db/h2](file:///home/twoputt/databases/open18-db/h2))
Click Test Connection

Remember, when using H2 in embedded mode, you can only have a single connection open. I encourage you to research how to use H2 in TCP mode (like a regular database) because it's hard to control when Eclipse opens/closes connections to the database.