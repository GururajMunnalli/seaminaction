# Overview #

The output that seam-gen produces is not fixed in stone. As I mention in the book, seam-gen is no more than a glorified Ant script that uses FreeMarker templates and Hibernate's reverse engineering tool to generate content. What that means is that you can easily modify seam-gen to suit your needs.

## Getting started ##

The first step is to download the Seam distribution and extract it. From there, your focus will be on the seam-gen folder, which hosts the templates that Seam uses to create the project and generate code. The remainder of the instructions on this page apply to the files in that folder.

## Handling boolean types in Oracle ##

It is conventional to represent a boolean type in oracle using a CHAR(1) column with the values "y" or "n" to represent true and false. JPA cannot accommodate this mapping, but it is possible by using a Hibernate extension annotation. Here is an example of a boolean property named "excluded" mapped to a CHAR(1) column in Oracle using the Hibernate "yes\_no" custom type.

```
private boolean excluded;

@Column(name = "EXCLUDED_YN", length = 1)
@Type(type = "yes_no")
public boolean isExclude() {
    return this.exclude;
}

public void setExclude(boolean exclude) {
    this.exclude = exclude;
}
```

If you want this annotation applied automatically during the seam-gen reverse engineering, edit the file seam-gen/pojo/GetPropertyAnnotation.ftl in the seam distribution and replace it with the following contents:

```
<#include "Ejb3PropertyGetAnnotation.ftl"/>
<#if !property.equals(pojo.identifierProperty) && property.type.name == 'boolean'
  && cfg.properties['database.type'] == 'oracle'>
    @${pojo.importType("org.hibernate.annotations.Type")}(type = "yes_no")
</#if>
<#if property.columnSpan == 1>
<#assign column = property.getColumnIterator().next()/>
<#if !c2h.isManyToOne(property) && !c2h.isTemporalValue(property) && column.length != 255
  && property.type.name != "character" && property.type.name != "boolean">
    @${pojo.importType("org.hibernate.validator.Length")}(max=${column.length?c})
</#if>
</#if>
```

## Making entity restrictions compliant with DB2 ##

seam-gen creates entity queries that use syntax for each restriction similar to the following:

```
lower(person.firstName) LIKE concat(lower(#{personList.person.firstName}), '%')
```

However, this results in the following syntax in the SQL query:

```
where
    (
        lower(person0_.FIRST_NAME) like lower(?)||'%'
    )
```

Using DB2 v9.5, this results in an error, SQLState 42610, _A parameter marker is not allowed_. The problem is that DB2 does not allow parameter markers as function arguments without a cast.

One way around this is to leverage the parameterized methods feature of JBoss EL to convert the value to lowercase and concat the % in the EL:

```
lower(person.firstName) LIKE #{personList.person.firstName.toLowerCase().concat('%')}
```

A simpler way is to just reverse the order of the lower() and concat() functions:

```
lower(person.firstName) LIKE lower(concat(#{personList.person.firstName}, '%'))
```

## Adding a serialVersionUID to generated entities ##

JPA entities are required to implement the Serializable interface, yet seam-gen does not add the serialVersionUID property by default. Fortunately, it's possible. Simply create the file pojo/Pojo.ftl and populate it with the following contents:

```
${pojo.getPackageDeclaration()}
// Generated ${date} by Hibernate Tools ${version}
<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {
<#if !pojo.isInterface()>
private static final long serialVersionUID = 1L;
<#include "PojoFields.ftl"/>
<#include "PojoConstructors.ftl"/>
<#include "PojoPropertyAccessors.ftl"/>
<#include "PojoToString.ftl"/>
<#include "PojoEqualsHashcode.ftl"/>
<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>
</#if>
<#include "PojoExtraClassCode.ftl"/>
}
</#assign>
${pojo.generateImports()}
${classbody}
```

You can easily use a FreeMarker function/macro to generate a value if you don't want to make it a constant as I do here. Notice that you can also remove that pesky generated comment if you don't like it appearing in your entity classes.