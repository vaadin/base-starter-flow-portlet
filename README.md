# Vaadin Portlet Starter Project
Project to get started with a Vaadin application that is running inside a 
portlet in a portal, as defined in the JSR-286 (Java Portlet API 2.0) standard.
Clone the repository and import the project to the IDE of your choice as a Maven
project. You need to have Java 8 or 11 installed.

## Running the portlet

Before the portlet application can be run, it must be deployed to a portal. 
We currently support Apache Pluto (https://portals.apache.org/pluto/). The
easiest way to try out your application is to run a Maven goal which starts an 
embedded Tomcat 8 serving the Pluto Portal driver:

`mvn package cargo:run -Pdemo,production`

Visit http://localhost:8080/pluto, and log in as `pluto`, password `pluto`.

The deployed portlet needs to be added to portal page. Do this by
1) Selecting `Pluto Admin` page
2) Select `About Apache Pluto` from the drop-down under "Portal Pages"
3) Select `/portlet-starter` from the left drop-down under "Portlet Applications"
4) Select `MyPortlet1` from the drop-down on the right
5) Click the `Add Portlet` button

Once you navigate to `About Apache Pluto` page, the starter portlet should be
visible on the page, displaying a "Click me" button. 

## Remote debugging for Portal

Remote debugging (JDWP) is available on port 8000 (to activate
in IntelliJ, choose `Run -> Attach to Process...`). 

## Integration tests
To run the integration tests:

`mvn clean verify -Pdemo,production,integration-tests` 

## Production build
Before deploying your portlet for production for the first time, you will
probably want to change `portlet-name` in `portal.xml` from `TestPortlet1` to
something else, as well as the tag returned by `MyPortlet.PORTLET_TAG` from
`portlet-content` to something else (stock Pluto already contains portlets with
these names). Then build the production .war:

`mvn package -Pproduction`

After the build has finished, copy the files in `target/classes/META-INF/VAADIN/build/` 
into Pluto tomcat ROOT folder e.g.` pluto-3.0.0/webapps/ROOT/VAADIN/build/`

Next you need to unzip `flow-client-2.1.portal-SNAPSHOT.jar` from `target/[war_name]/WEB-INF/lib/` 
and copy the files in `META-INF/VAADIN/static/client` into tomcat ROOT e.g. `pluto-3.0.0/webapps/ROOT/VAADIN/static/client`.

Copy both .war files from the `target` directory into Pluto's `webapps` 
directory and restart Pluto. To add the portlet to a page, use Pluto's 
"Pluto Admin" interface as detailed in "Running the portlet".

## Portlet development in servlet mode

It is possible to develop portlets as normal single route Vaadin applications.

[NOTE] when developing using servlet mode no Portlet specific methods can be used
in the view for servlet mode. 

To develop portlets in servlet mode using jetty create a Route for portlet content e.g.

```java
package com.vaadin.starter.portlet;

import com.vaadin.flow.router.Route;

@Route("")
public class ServletDevelopment extends MyPortletContent {
}
```

Then run the project as `mvn clean jetty:run -Pservlet`

## Creating a Portlet with servlet mode

To create an application that can be used both as a portlet and a servlet application
you need to check at generation time the `Request` type.

For instance `MyPortletContent` could be setup as

```java
public class MyPortletContent extends VerticalLayout {
    public MyPortletContent() {
        String message;
        if (VaadinRequest.getCurrent() instanceof PortletRequest) {
            VaadinPortlet portlet = VaadinPortlet.getCurrent();
            String name = portlet.getPortletName();
            String serverInfo = portlet.getPortletContext().getServerInfo();
            message = String
                    .format("Hello from %s running in %s!", name, serverInfo);
        } else {
            message = String
                    .format("Hello from %s running in a servlet container",
                            getClass().getSimpleName());
        }
        Button button = new Button("Click me",
                event -> Notification.show(message));
        add(button);
    }
}
```

## Notes about the project

Vaadin 14+ portlet support feature is still under development and changes to
both the API and this starter project are possible. For example, 
`MyComponentContentExporter` is currently a workaround and the requirement
for implementing a `WebComponentExporter` should fade away in the future.
