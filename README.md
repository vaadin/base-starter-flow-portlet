# Vaadin Portlet Starter Project
Project to get started with a Vaadin application that is running inside a portlet in a portal based on the Java Portlet API 3.0. 

The starter has been tested with Pluto Portal 3.1.0. Use `liferay` branch to run this starter in Liferay 7 Portal. 

**Prime** Vaadin subscription is required for this base starter and for all the applications using Vaadin Portlet. 
You also need to have Java 8 or 11 installed.

Clone the repository and import the project to the IDE of your choice as a Maven project.

The documentation for Vaadin Portlet support is available [here](https://vaadin.com/docs/v14/flow/integrations/portlet).

## Running the portlet

Before the portlet application can be run, it must be deployed to a portal. 
We currently support Apache Pluto (https://portals.apache.org/pluto/). The
easiest way to try out your application is to run a Maven goal which starts an 
embedded Tomcat 8 serving the Pluto Portal driver:

`mvn package cargo:run -Pdemo`

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

`mvn clean verify -Pdemo,integration-tests` 

## Production build
Before deploying your portlet for production for the first time, you will
probably want to change `portlet-name` in `portal.xml` from `TestPortlet1` to
something else, as well as the tag returned by `MyPortlet.PORTLET_TAG` from
`portlet-content` to something else (stock Pluto already contains portlets with
these names). Then build the production .war:

`mvn package -Pproduction`

Copy both `portlet-starter.war` and `vaadin-portlet-static.war` from `/target`
folder into the `webapps` folder on a Tomcat web server with Pluto. Restart
the web server. To add the portlet to a page, use Pluto's "Pluto Admin" 
interface as detailed in "Running the portlet".

## Portlet development in servlet mode

It is possible to develop portlets as normal single route Vaadin applications.

**Note:**
When developing using servlet mode no Portlet specific methods can be used
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

### Creating a Portlet with servlet mode

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
