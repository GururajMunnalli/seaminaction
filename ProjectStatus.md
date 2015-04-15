# Purpose #

The purpose of this page is to give you an idea of what is going on in SVN without having to poke around in the dark. As of right now, the final manuscript is not yet complete, and thus the source code is still progressing as well. This page gives updates as to which chapters are expected to be working and which chapters are still theoretical (there is code in the book and on my harddrive but not yet available in this project).

## Status ##

|**Chapter/Part**|**SVN Status**|
|:---------------|:-------------|
|pt01|available; includes ch01 and ch02; book provides necessary information to create from scratch|
|ch03|available|
|ch04|available|
|ch05|available|
|ch06|available|
|pt03|available; includes ch07, ch08, ch09, ch10|
|ch11|available|
|ch12|available|
|ch13|available|
|prototype|mostly complete, should be able to match up most examples from book|

## Certified versions of Seam ##

  * JBoss Seam 2.0.3.CR1 (until GA is available)

Unfortunately, there was a bug in JBoss Seam 2.0.0.GA that prevented seam-gen from properly reverse engineering an H2 database. This is now fixed in Seam >= 2.0.1.

## Notes to reviewers ##

I encourage you to file bug reports using the [Issue Tracker](http://code.google.com/p/seaminaction/issues/list) when you encounter errors. I ask for your patience as I synchronize the code with the current draft that you have been provided. At the time of writing this note, chapter 2, 3, and 4 should work as expected. The code from the other chapters will be available shortly.

## Log ##

_Aug 28, 2008 - Just updated all source code so that it matches the book and finished a build script to get started with development and for me to create a distribution_

_May 13, 2008_ - The source code commits have been on hold while frantically getting the manuscript polished. I just committed the source code that accompanies chapter 01. More commits to come.

_Jan 29, 2008_ - A major commit of the prototype. The prototype contains just about all of the source code from the book and then some. However, several patches must be made to Seam in order to get it working correctly.

_Jan 07, 2008_ - Working on getting basic information up on project site; uploaded source code sent to final reviewers