package com.vaadin.starter.portlet;

import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
public class MyPortletContentExporter
        extends WebComponentExporter<MyPortletContent> {

    public MyPortletContentExporter() {
        super("portlet-content");
    }

    protected void configureInstance(WebComponent<MyPortletContent> webComp,
                                     MyPortletContent comp) {
        // NOOP
    }
}
