# Reltio Workflow Service Public Repository

This repository contains:

- Public Api for Workflow Custom Jars
- Source code of default Reltio service task delegates (Approve Change Request, Delete Entity and etc) 
- Default process definitions that are available on every tenant
- Samples of new/customized process definitions with JAVA source code of additional classes


Reltio Workflow Public libraries are hosted on Reltio Maven Repository.

You can add dependencies for  in your pom.xml file:
```
<!-- public api -->
<dependency>
    <groupId>com.reltio.workflow</groupId>
    <artifactId>workflow-api</artifactId>
    <version>[2026.1.0.0,]</version>
    <scope>provided</scope>
</dependency>

<!-- public custom classes -->
<dependency>
    <groupId>com.reltio.workflow</groupId>
    <artifactId>workflow-custom</artifactId>
    <version>[2026.1.0.0,]</version>
    <scope>provided</scope>
</dependency>
```

Also you need to add repositories section to your pom.xml file:
```
<repositories>
    <repository>
        <id>reltio-libs-release</id>
        <name>Reltio release repository</name>
        <url>https://repo-dev.reltio.com/content/repositories/public</url>
    </repository>
</repositories>
```
Don't forget to add the credentials for the reltio-libs-release server in the global settings.xml (%USER_HOME%/.m2/settings.xml):
```
<servers>
   <server>
      <id>reltio-libs-release</id>
      <username>read.only.workflow</username>
      <password>pbBmG3jbdYUazoj7</password>
   </server>
</servers>
```
