package com.vaadin.starter.portlet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.portal.VaadinLiferayPortlet;
import com.vaadin.flow.portal.VaadinPortlet;
import com.vaadin.mpr.LegacyWrapper;
import com.vaadin.ui.TextField;

public class MyPortletContent extends VerticalLayout {

    public MyPortletContent() {
        VaadinPortlet portlet = VaadinLiferayPortlet.getCurrent();
        String name = portlet.getPortletName();
        String serverInfo = portlet.getPortletContext().getServerInfo();
        Button button = new Button("Click me", event -> Notification.show(
                "Hello from " + name + " running in " + serverInfo + "!"));
        add(button);

        TextField textField = new TextField("My text field");
        LegacyWrapper legacyWrapper = new LegacyWrapper(textField);
        add(legacyWrapper);
    }
}
