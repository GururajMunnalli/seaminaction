# Overview #

The example code was written for Seam 2.0.3.CR1. This page describes the steps required to migrate the example code to work with Seam 2.1.1.GA. Changes involve primarily the refactor of the document store components and the introduction of Seam's new security API.

## Automated approach ##

The Ant build script (build.xml) that ships with the source code includes a target to migrate a project stage to Seam 2.1. To use it, do the following.

Open build.properties and modify the seam.version property as follows:

`seam.version=2.1.1.GA`

Next, retrieve Seam from the web and extract it.

`ant get-seam`

Then, run the upgrade task and choose a project stage from the list to upgrade it to Seam 2.1.

`ant upgrade-project`

Finally, inflate the same project stage and you'll be ready to deploy it.

`ant inflate-project`

## Manual approach ##

Here is the manual approach and detailed steps that must be taken to migrate the project to Seam 2.1. Consult the upgrade-project task in the Ant build script for the precise details.

  * change all occurrences of -2.0.xsd to -2.1.xsd in the following files: resources/WEB-INF/components.xml, resources/WEB-INF/pages.xml, view/`*`.page.xml, view/admin/`*`.page.xml
  * change the package org.jboss.seam.pdf to org.jboss.seam.document in the following files: resources/WEB-INF/web.xml, src/action/org/open18/report/ReportGenerator.java
  * change the constructor new DocumentData to new ByteArrayDocumentData (and import type) in the following files: src/action/org/open18/report/ReportGenerator.java
  * change the namespace http://jboss.com/products/seam/pdf to http://jboss.com/products/seam/document in the following files: resources/WEB-INF/components.xml
  * change the namespace alias xmlns:pdf to xmlns:document in the following files: resources/WEB-INF/components.xml
  * change the element <pdf:document-store to <document:document-store in the following files: resources/WEB-INF/components.xml
  * use setEjbql(), setRestrictionExpressionStrings(), and setMaxResults() in constructor of EntityQuery classes
  * change the package org.jboss.seam.security.PermissionCheck to org.jboss.seam.security.permission.PermissionCheck in the following files: resources/security.drl
  * replace the security.drl with etc/security-seam21-compatible.drl in stages ch11 and beyond
  * cannot use EL in the ejbql attribute on 

&lt;framework:entity-query&gt;

 in resources/WEB-INF/components.xml (ch14)
  * add 

&lt;security:rule-based-permission-resolver/&gt;

 to resources/WEB-INF/components.xml
  * add itext-rtf.jar to deployed-jars.list if itext.jar is present
  * add jxl.jar to deployed-jars.list