package com.vaadin.starter.portlet;

import com.vaadin.flow.portal.VaadinPortlet;

public class MyPortlet extends VaadinPortlet<MyPortletContent> {

    @Override
    public String getTag() {
        return "portlet-content";
    }

}
