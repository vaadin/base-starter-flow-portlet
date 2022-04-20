package com.vaadin.starter.portlet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.portal.VaadinLiferayPortlet;
import com.vaadin.flow.portal.VaadinPortlet;

public class MyPortletContent extends VerticalLayout {

    public MyPortletContent() {
        VaadinPortlet portlet = VaadinLiferayPortlet.getCurrent();
        String name = portlet.getPortletName();
        String serverInfo = portlet.getPortletContext().getServerInfo();
        Button button = new Button("Click me", event -> Notification.show(
                "Hello from " + name + " running in " + serverInfo + "!"));
        add(button);
    }
}
