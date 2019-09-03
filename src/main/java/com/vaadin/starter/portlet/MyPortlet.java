package com.vaadin.starter.portlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.portal.VaadinPortlet;

public class MyPortlet extends VaadinPortlet {

    public static Logger logger = LoggerFactory.getLogger(MyPortlet.class);

    public static String PORTLET_TAG = "portlet-content";
}
