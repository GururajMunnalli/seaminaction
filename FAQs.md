
---


  * **When do I use @In vs @PersistenceContext to inject an EntityManager**

> Please see [this FAQ](http://seamframework.org/Documentation/WhenDoIUseInVsPersistenceContextToInjectAnEntityManager) on the Seam wiki.

  * **Why is the @DataModelSelection property annotated with @Out and not @In? (golftips example)**

> The @DataModelSelection is a variation of the @In annotation. The @Out annotation turns around and pushes the selected item injected by @DataModelSelection back out into a context as a top-level variable. Without @Out, the selection would only be available while the method is executing.

  * **Why does the first row of the DataModel sometimes get selected (and outjected) before any explicit selection is performed?**

> This commonly happens when you are using @Out and @DataModelSelection on the same field. You see, the @DataModelSelection is populated the next time bijection occurs on a component after the @DataModel is outjected. That can happen while the page is still rendering, not necessarily on the postback. For instance, let's say that the toString() method is called on the component. Seam will populated the @DataModelSelection field and then subsequently outject the value as defined by the @Out annotation.

> My recommendation is to always use a different field for @Out and @DataModelSelection so that you can programmatically control when the object reference is transferred.

> As for why the first row is always selected, when no row of a DataModel is explicitly selected, JSF leaves the pointer on the first row :(

  * **I get an error when using @RequestParameter on a field with a primitive type. Why?**

> The @RequestParameter annotation does not require a value by default (in fact, there isn't even a setting). If the request parameter is missing, Seam will attempt to assign a null value to the field. If the field is a primitive type, Seam will be unable to perform the assignment in this case and, as a result, an error will be thrown.

> Therefore, if the field you are adding the @RequestParameter annotation to is a primitive type (e.g. int), change the field type to a primitive wrapper class (e.g. Integer). You will then have to be prepared for a null value, but that you can handle in your business logic.

  * **When do I use @Remove and @Destroy on a SFSB?**

> In Seam 2, if you have exactly one @Remove method, it's automatically made the @Destroy method. If you have multiple @Remove methods, you need @Destroy. Also @Remove @Destroy methods must have no parameters.

  * **How does the default locale work? When is it used?**

> Please see [this FAQ](http://seamframework.org/Documentation/HowDoesTheDefaultLocaleWorkWhenIsItUsed) on the Seam wiki.

  * **Why do the collections on the entities in the sample application appear more than once?**

> This problem is related to the hibernate.hbm2ddl.auto setting. If the option is set to "none" (and the database is replaced with the initial one from the sample code download) no duplicate mappings are present. If the setting is set to "update", then generate creates duplicate mappings after the next server restart.

> It is actually a bug in Hibernate [HHH-3532](http://opensource.atlassian.com/projects/hibernate/browse/HHH-3532) that I am lobbying to get corrected. It just doesn't detect the foreign keys correctly and keeps creating them over and over.

> This issue has really bitten me hard and made setting up the sample application a little harder than it had to be. A workaround is to go into the tables and delete the duplicate foreign keys.

  * **What is the difference between components.properties and seam.properties?**

> Both files use the Java properties syntax (i.e., name=value pair). However, Seam uses them for different purposes.

> Seam uses the values in components.properties to replace Ant-style tokens in the Seam component descriptor (e.g., WEB-INF/components.xml). For instance, if components.properties contained the following line:

```
debug=true
```

> Seam would replace the @debug@ token in the following excerpt with the value true:

```
<core:init debug="@debug@"/>
```


> Seam uses the values in seam.properties to assign initial values to component definitions. The key consists of the component name plus the property name, joined by a '.' character. The property value becomes the initial value. For instance, you can disable Seam's transaction management as follows:

```
org.jboss.seam.core.init.transactionManagementEnabled=false
```

> Recall that both files must be located on the classpath of either the JAR (in the case of an EAR) or WEB-INF/classes (in the case of a standalone WAR).

  * **What if I need to perform business validations in a service layer?**

> Seam is not saying "never use middleman components, they are evil" but rather that you can do away with them when they serve no purpose. If you have a need to validate business logic using an external service or layer, then introduce a view-oriented component than can translate those violations into user interface messages and/or routing. Basically, do what feels most natural. Remember, you can inject your service into your Seam component using @In. In fact, your service might even be a Seam component itself.

> You can also use the Hibernate Validator or Bean Validation in the service layer to validate. The tricky part is how to validate a populated object and still be able to present the user with the form again with the validation error messages. For that, you may want to check out the [graphValidator](http://livedemo.exadel.com/richfaces-demo/richfaces/graphValidator.jsf?c=graphValidator&tab=usage) component from RichFaces.

  * **I use Seam and I still get a LazyInitializationException**

> Despite the fact that Seam curtails the appearance of this exception, it doesn't eliminate it entirely. After all, the exception exists for a reason. If you attempt to access an uninitialized proxy or collection on an entity instance which is no longer being managed by an active Hibernate Session / JPA EntityManager (perhaps because it is stored in the HTTP session), then this exception will be raised. There are two ways to deal with this problem:

  1. **Save the id, not the instance** - Instead of storing the actual instance in a long term scope, such as the HTTP session, store the id of the entity. Then, when you need to access the instance, have Hibernate/JPA load that instance on demand. This is done using the combination of an EntityHome and an event-scoped factory:
```
<framework:entity-home name="typeHome" entity-class="org.example.model.Type">
    <framework:id>#{sessionScopedId}</framework:id>
</framework:entity-home>
```
  1. **Prune the instance** - If you need to store the instance in a long-term scope and don't want to be afraid of accidentally hitting an uninitialized proxy or collection, you can prune the instance after loading it. One library you can use for this purpose is [entity-pruner](http://code.google.com/p/entity-pruner/).

  * **After I add or edit a record, I don't see the changes in the list**

> It's a fairly typical scenario to use a conversation to add or edit a record and another conversation to display/sort/search the list of records. But since you are using two different conversations, they don't affect each other and thus you have to be careful with caching.

> The most common problem people experience is that the list is not updated after a new record is added or an existing record modified. That's because you need to inform the component that manages the list to refresh the data to get the most recent changes. One approach is to raise an event in the editor component which the list component can observe and refresh the list. Another approach is to invoke a method call that refreshes the list in the navigation transition from the editor to the list page.

> Don't confuse a stale list from the database not being updated (or the entity manager not being flushed). If you restart your application (or just create a new session) and see that the data was updated appropriately, you likely have a variable-scope caching problem.

  * **How do I send e-mail with Java?**

> Personally I dreaded sending e-mail in Java until I started using Seam. In fairness, Spring supporters could say the same thing about their framework. jBPM is yet another interface. Those are the APIs to the mail you are calling for.

> I would discourage anyone from using yet another homegrown wrapper around the JavaMail API and instead use e-mail support from their framework. I even consider the commons-email a bit too one-offish. For instance, in Seam you can compose e-mails using XHTML templates. To me, that is the type of ease one should have when composing an email. Reason being that email is probably the most ubiqutous technology.
