# About this Glossary #

Due to the quota of 550 pages for Seam in Action, there was some auxiliary information I just could not squeeze between its covers. Fortunately, there is no limit to this wiki and I can share that information with you here. One of those addendum is this glossary.

There are two sections below, one of Seam terms and of golf terms. You should know from reading the book that there is an underlying theme of golf weaved through each chapter. I did this both for entertainment and for demonstration. However, for those people who haven't had a lot of exposure to golf, you may find this glossary useful.

# Seam-related Terms #

**component** - a reusable object that can be called upon by client code to perform work as defined by its interface (even if only implied); it is characterized by its service interface, the configurable properties it exposes, its transactional boundaries, and the events it can fire and/or process; it can be assembled with other components by an infrastructural container to form a complete working system

see also: [WhatIsAComponent](WhatIsAComponent.md)

**configuration by exception** - (also called _convention over configuration_) responds to the question "if you are typing configuration and what you are typing seems obvious, then why type it?" let's the framework imply the configuration and you only configure when you need it to do something different than the default

**host component** - the component on which the subject property or method resides. This term often comes up when discussing outjection and factory methods, which inherit the scope from the component on which they reside if a scope is not specified. The host component could also be described as the current or owning component.

**interceptor** - a class that can be used to intercept runtime events that occur in a program to "advise" the code by weaving in cross-cutting concerns; the main event to which an interceptor is applied is a method invocation.

**lazy loading** - a technique used in ORMs to avoid loading more information than is necessary into memory. Without lazy loading, loading an object would mean loading the object and all of its associated objects into memory recursively. In the case that all entities are linked, this would result in the entire database being loaded into memory when retrieving an object. To avoid this situation, an ORM will only load the object or object graph up to a certain level. This level is defined statically and/or dynamically by the developer (using fetch directives). If the program reaches one of the lazy associations, the ORM will load the needed object graph section on demand, transparently.

**meta-annotation** - an annotation that is applied to an annotation interface (ElementType.ANNOTATION\_TYPE). Thus, the annotation is applied to a concrete type by way of the annotation on which it is defined.

**open session in view** - an architecture pattern that keeps a Hibernate session (persistence context) open during the rendering of the view in an MVC application; typically, the session is closed after executing the business logic, leaving the view unable to cross lazy associations in a Hibernate-instrumented entity instance, leading to the dreaded LazyInitializationException

**persistence unit** - the combination of entity metadata (annotated classes or XML mappings) and the persistence manager factory, an object that is runtime representation of this configuration

**persistence manager factory** - an object that is a runtime representation of the persistence unit configuration; can be used to create application-managed persistence managers (continues to act as their parent); thread-safe; expensive to create

**persistence manager** - an API for managing the life cycle of entity instance (create, update, remove, find by id, query); maintains an internal persistence context of instances; produces a set of SQL queries from changes in the persistence context that are executed against the database using write-behind semantics

**persistence context** - a cache of persistent entity instances; instances which have been changed are considered "dirty" and the pending changes are flushed to the database when flush() is called on the persistence manager (or automatic flushing occurs); the persistence context has a one-to-one association with a persistence manager

> When JPA/Hibernate executes a query, it reads the result set of the query and marshals the entity objects that are returned to the application. During this process, JPA/Hibernate interacts with the persistence context by attempting to resolve every entity object from the cache by its identifier. Only if the instance can't be found there does JPA/Hibernate construct a new instance by reading the values from the row in the JDBC result set

**persistence life cycle** - the different persistence states that an entity instance goes through: transient, persistent, detached, removed

**persistent cookie** - a cookie that is not removed when the browser closes; typically used for an authentication token

**Seam Application Framework** - a framework of classes for quickly building application components that perform CRUD and query operations on entities.

**unit of work** - a set of operations, made atomic by a transaction; in this text, the operations are performed on the entity instance and the persistence manager
also: Unit of work ( http://www.martinfowler.com/eaaCatalog/unitOfWork.html ) - keeps track of everything you do during a business transaction that can affect the database. When you're done, it figures out everything that needs to be done to alter the database as a result of your work.

**transaction** - two or more (database) operations that depend on one another; granted there is that whole ACID thing too, but at the core it is about linking operations

**JAR** (Java Archive file : .jar extension) stores of Java classes, resources, auxiliary files, etc. Java source files are compiled and then distributed in jar format.

**WAR** (Web Archive File : .war extension) are intended to contain complete Web applications. A Web Application is a collection of files, classes, resources, .jar files that can be packaged and accessed as one servlet context. Usually, sample web applications are distributed using war format.

**EAR** (Enterprise Archive file : .ear extension) are intended to contain complete Enterprise applications. In this context, an enterprise application is defined as a collection of .jar files, resources, classes, and multiple Web applications.

## External Seam-related Glossaries ##

[Glossary for Hibernate EntityManager](http://www.hibernate.org/hib_docs/entitymanager/reference/en/html/architecture.html)

# Golf-related Terms #

**golf facility** - the place of business and conglomerate of courses

**golf course** - a collection of holes which are played sequentially during a golf round

**hole** - the playing area between the tee box and a physical hole in the ground; the objective of the game is to get the ball into the hole

**tee set** - the position of the tee box in relation to other tee boxes for a given hole; a tee set represents the collective sum of all such tee boxes on a given course

**tee** (tee box) - the physical area where play begins on a hole; there is a tee for each tee set