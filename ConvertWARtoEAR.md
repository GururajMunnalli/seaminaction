# Introduction #

One of the limitations of seam-gen is that you cannot easily switch between an WAR and an EAR. That's just the nature of an Ant build. I suppose it would be easier to make this switch with Maven 2. Regardless, this page details the steps of making the switch. The instructions on this page were contributed by Valarie Griffin.

# Details #

I used the following steps to create open18ee (ear) and move code from open18 (war):

```
seam setup
```
project is open18ee, deployment is war, skin is something different to make testing obvious.

```
seam create-project
```

copy the following from open18 to open18ee:

```
resources/seam-gen.reveng.xml
src/action/org
src/model/org
src/test/org
```

copy regular files from view

copy view/layout

edit open18ee/view/layout/template.xhtml changing open18 to open18ee in lines with "head title" and "ui:param projectName"

If you didn't change skins, copy view/stylesheet/theme.css

If you did change skins, copy the edits that were added at the end of the file.

If you added urlrewritefilter.jar to deployed-jars.list, add it to deployed-jars-ear.list

Note that there is no EJB code at this point. It's now possible to deploy open18ee and to work on the rest of the tutorial, including the EJB stuff.

It's MUCH easier to make any such transition BEFORE you've created actions because actions in the ear want to be interface-and-Bean pairs. If you do have actions, use the steps outlined in 4.6.2 to convert each one.