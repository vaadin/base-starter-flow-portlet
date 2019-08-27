# Vaadin Portlet Starter Project
Project to get started with a Vaadin application that is running inside a 
portlet in a portal, as defined in the JSR-286 (Java Portlet API 2.0) standard.
Clone the repository and import the project to the IDE of your choice as a Maven
project. You need to have Java 8 or 11 installed.

Before the portlet application can be run, it must be deployed to a portal. 
We currently support Apache Pluto (https://portals.apache.org/pluto/). The
easiest way to try out your application is to run a Maven goal which starts an 
embedded Tomcat 8 serving the Pluto Portal driver:

`mvn -Pdemo package cargo:run`

Visit http://localhost:8080/pluto, and log in as `pluto`, password `pluto`.
The starter portlet (`TestPortlet1`) appears to the right of the "About" 
portlet. Remote debugging (JDWP) is available on port 8000 (to activate
in IntelliJ, choose `Run -> Attach to Process...`). 

To run the integration tests:

`mvn -Pdemo -Pintegration-tests verify` 

Before deploying your portlet for production for the first time, you will
probably want to change `portlet-name` in `portal.xml` from `TestPortlet1` to
something else, as well as the name returned by `MyPortlet::getName` from
`testsuite` to something else (stock Pluto already contains portlets with
these names). Then build the production .war:

`mvn -Pproduction package`

Copy the .war generated in the `target` directory to Pluto's `webapps` 
directory and restart Pluto. To add the portlet to a page, use Pluto's 
"Pluto Admin" interface.