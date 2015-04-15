# Introduction #

There are two ways to inject one EJB into another EJB. This page explains the Java EE way and the Seam way. The Seam way actually shows injecting an EJB into a JavaBean to emphasize that it doesn't matter what type of component the recipient is in that case.

If you read through chapter 4 and the first part of chapter 6 carefully, you should have no trouble with this challenge. In fact, it's almost impossible to get out of chapter 4 without a crystal clear understanding of how Seam handles components. You may still have problems, but at least you will **understand** them.

## The Java EE way ##

If you are injecting one EJB into another EJB, you can use the @EJB annotation. However, that's the only case this annotation is useful and the injection is handled by the Java EE container.

```
@Stateless
public class StatelessBean1 implements StatelessLocal1{ ... }
```

```
@Stateless
public class StatelessBean2 implements StatelessLocal2 {
    @EJB
    private StatelessLocal1 stateless1; // name of propery doesn't matter here
}
```

## The Seam way ##

If you are injecting an EJB into any Seam component (EJB or JavaBean), you can use @In. However, the EJB must also be a Seam component. First Seam looks up the component, then the component looks up the EJB proxy.

```
@Stateless
@Name("stateless1")
public class StatelessBean1 implements StatelessLocal1{ ... }
```

```
@Name("javaBean1")
public class JavaBean1 {
    @In(create = true)
    private StatelessLocal1 stateless1; // notice this matches the component name of the EJB
}
```

You only need "create=true" if the component being injected is not an autocreate component. You can make a component autocreate by adding @AutoCreate to the class definition.

## Debug advice ##

If for some reason Seam is not finding the EJB, then it could be that the JNDI settings are messed up, another topic discussed in Seam in Action. You should always test the lookup by just including the name of the component in a value expression in a Facelet template:

#{stateless1}