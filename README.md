# Vaadin Portlet Starter Project
Project to get started with a Vaadin 23.1+ application that is running inside a portlet in a Liferay 7 Portal.

**Prime** Vaadin subscription is required for this starter project and for all the applications using Vaadin Portlet.
You also need to have Java 11 (or later) installed.

Clone the repository and import the project to the IDE of your choice as a Maven project.

The documentation for Vaadin Portlet support is available [here](https://vaadin.com/docs/latest/flow/integrations/portlet).

## Project demo
Run the following profile to automatically download and set up the Liferay bundle:

`mvn clean package -Pdemo`

Start the Liferay with your portlet:

`mvn cargo:run`

## Set up Liferay manually and running the portlet

Before the portlet application can be run, it must be deployed to a [Liferay](https://www.liferay.com/downloads-community):

1. Build the whole project using `mvn install -DskipTests` in the root

2. We assume Liferay is running in http://localhost:8080/, an easy way to run a local
   copy of Liferay is to use their official [docker images](https://hub.docker.com/r/liferay/portal).
   Below is an example of a docker-compose file you can use.

````
version: "2.2"
services:
    liferay-dev:
        image: liferay/portal:7.2.1-ga2
        environment:
            - LIFERAY_JAVASCRIPT_PERIOD_SINGLE_PERIOD_PAGE_PERIOD_APPLICATION_PERIOD_ENABLED=false
        ports:
            - 8080:8080
            - 8000:8000
        volumes:
            - ./deploy:/mnt/liferay/deploy
            - ./files:/mnt/liferay/files
````

   The following steps describe how to set up the Liferay regardless of what way 
of Liferay distribution you choose: bundle or Docker image. 

3. Add the following to the end of the last line in Tomcat's `setenv.sh`
   (`/var/liferay/tomcat-<version>/bin`) before starting Liferay. When
   using the above docker-compose file place an edited copy of `setenv.sh`
   in `./files/tomcat/bin`.

````
 -Dvaadin.portlet.static.resources.mapping=/o/vaadin-portlet-static/
````

4. Download and add the Jna dependency JARs of a certain version into
   `/var/liferay/tomcat-<version>/webapps/ROOT/WEB-INF/lib`:
    - [net.java.dev.jna:jna:5.7.0](https://mvnrepository.com/artifact/net.java.dev.jna/jna/5.7.0)
    - [net.java.dev.jna:jna-platform:5.7.0](https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform/5.7.0)

   How to copy these files is described [here](https://learn.liferay.com/dxp/latest/en/installation-and-upgrades/installing-liferay/using-liferay-docker-images/providing-files-to-the-container.html#using-docker-cp)

   This is needed because Liferay uses an older version of Jna and running
   Vaadin Portlet in dev mode causes a conflict of dependencies used by Liferay
   and Vaadin License Checker (`NoClassDefFound` exception).

   Here is a useful [docs](https://learn.liferay.com/dxp/latest/en/building-applications/reference/jars-excluded-from-wabs.html) describing how to add third-party dependency version you want.

5. Run `docker-compose up`

6. Deploy all wars: `vaadin-portlet-static.war` and `portlet-starter.war`
   to your docker container by copying them to `./deploy/` (the copied files should disappear when deployed).

7. Wait for the bundles to start, then visit http://localhost:8080/.
   Set up a new user if you're running Liferay for the first time. Default is `test@liferay.com`/`test`.
   Log in into Liferay.

8. The deployed portlet needs to be added to a portal page. Do this by
- Selecting the Plus or the Pen icon near top right of the page (exact
  placement and look
  varies by Liferay version) add elements to the current page.
- Under Widgets on the right sidebar find Vaadin Liferay Portlet category under which
  you will find entry for MyPortlet1, drag and drop it onto the page.
- If at the top right of the page, in edit mode, you see a Publish button,
  use it to make your changes public (7.3+).
  
## Remote debugging for Liferay

In order to remote debug your portlet under Liferay add the following to the end of the last line in
Tomcat's `setenv.sh` (`/var/liferay/tomcat-<version>/bin`) before starting Liferay. When using the
above docker-compose file place an edited copy of `setenv.sh` in `./files/tomcat/bin` before
`docker-compose up`.

````
 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000
````

Remote debugging (JDWP) is now available on port 8000 (to activate
in IntelliJ, choose `Run -> Attach to Process...`).

## Production build
Before deploying your portlet for production for the first time, you will
probably want to change `portlet-name` in `portal.xml` from `TestPortlet1` to
something else, as well as the tag returned by `MyPortlet.PORTLET_TAG` from
`portlet-content` to something else (stock Pluto already contains portlets with
these names). Then build the production .war:

`mvn package -Pproduction`

Copy both `portlet-starter.war` and `vaadin-portlet-static.war` from `/target`
folder into the `deploy` folder of a Liferay container.

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

### Current known issues running under Liferay

See Vaadin Portlet [release notes](https://github.com/vaadin/portlet/releases) for a limitation and known issues list.
