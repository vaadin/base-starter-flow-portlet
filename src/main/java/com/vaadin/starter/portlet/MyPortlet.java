package com.vaadin.starter.portlet;

import jakarta.portlet.annotations.Dependency;
import jakarta.portlet.annotations.PortletConfiguration;

import com.vaadin.flow.portal.VaadinLiferayPortlet;

@PortletConfiguration(
        portletName = "MyPortlet",
        dependencies = @Dependency(name = "PortletHub", scope = "jakarta.portlet", version = "3.0.0")
)
public class MyPortlet extends VaadinLiferayPortlet<MyPortletContent> {

    @Override
    public String getPortletTag() {
        return "portlet-content";
    }

}
