# Introduction #

This page includes some notes about Seam remoting that didn't make it into the book.

## Stub matrix ##

Lists conditions used to determine stub variety. The first three columns represent the characteristics of the server-side component and the last two columns detail the type of stub and the name assigned to it.

| **Component type** | **Has @Name?** | **Has @WebRemote method?** | **Stub variety** | **Stub name** |
|:-------------------|:---------------|:---------------------------|:-----------------|:--------------|
| EJB session bean | either | yes, on local interface | executable | component name |
| EJB session bean | either | no, not on local interface | executable, no methods |component name |
| EJB session bean | either | no, don't have local interface | _runtime exception_ |
| Entity class | yes | n/a | local component | component name |
| Entity class | no | n/a | local type | entity class name |
| JavaBean | yes |yes | executable | component name |
| JavaBean | no |yes | executable | component name |
| JavaBean | yes |no | local component | component name |
| JavaBean | no |no | local type | component class name |