# Overview #

This page provides information about developing with Groovy in your Seam and seam-gen projects.

## Java -> Groovy ##

Currently, in a WAR seam-gen project, you can happily create Groovy classes (including Groovy Seam components) and have them use other Java classes (including Java Seam components) in your project. However, if you want to refer to a Groovy class from a Java class, you need to use the joint compiler (groovyc). To do that, you have to disable debug mode in the seam-gen project build. Depending on whether you are using an exploded or packaged archive, you run one of the two commands:

```
ant -Ddebug=false explode
```

```
ant -Ddebug=false deploy
```

You can also disable debug mode (for the dev profile) by modifying the property in build-dev.properties

```
debug=false
```

## EAR project support ##

At this time, Groovy support is not available in an EAR seam-gen project. We will try to get this in for the 2.1 release.