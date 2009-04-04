The directory contains both the schema and seed data for the open18 database.

If you are starting with a fresh database, use one of the following schema
scripts to create the initial tables according to which database you are using.
Files without a database type are assumed to be for H2.

open18-initial-schema.sql
open18-initial-schema-mysql.sql

You can then seed the database with facilities.

open18-initial-seed-data.sql
open18-initial-seed-data-mysql.sql

If you are using H2, you can simply extract one of the two zip files, which
contain ready-made H2 database files.

open18-db-initial-empty.zip
open18-db-initial-seeded.zip

As you progress through the book, you can either set hibernate.hbm2ddl.auto to
update so that Hibernate will create the tables for you from the metadata on
your JPA entity classes or you can use the incremental scripts to apply the
changes.

open18-incremental-ch04.sql
open18-incremental-ch04-mysql.sql

You can also seed the new tables added with some sample data.

open18-member-seed-data-ch04.sql
open18-member-seed-data-ch04-mysql.sql
