# Introduction #

Open 18 is a golf course application and golf community site. The initial database schema only includes the golf course directory tables and data.

## Business domain ##

This section discusses the business domain of the schema.

### Directory ###

**facility:** The directory consists of golf course data. The top level entity is the facility. A facility is a conglomerate of golf courses and represents the physical location of those courses. A golf facility can have multiple courses, but more times than not there is only a single course.

**course:** The course is where you play the game of golf. The course consists of either 9 or 18 holes (though there are exceptions).

**hole:** These holes are played in succession. At each hole there are a set of tee boxes from where the golfer starts.

**tee set:** Each tee box is represented by a color. The set of tees for a particular color spans the number of holes on the course. Once you select a tee box color, you stick with it throughout the round.

**tee:** A tee is the starting point for a particular tee set on a single hole.

## ER Diagrams ##

This section includes the entity-relationship diagrams for the two main parts of the schema, the directory and the community.

### Directory ###

![http://seaminaction.googlecode.com/svn/wiki/images/directory-er-diagram.png](http://seaminaction.googlecode.com/svn/wiki/images/directory-er-diagram.png)

### Community ###

![http://seaminaction.googlecode.com/svn/wiki/images/community-er-diagram.png](http://seaminaction.googlecode.com/svn/wiki/images/community-er-diagram.png)