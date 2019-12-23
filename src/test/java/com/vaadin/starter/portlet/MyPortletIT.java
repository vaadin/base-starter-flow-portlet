package com.vaadin.starter.portlet;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class MyPortletIT extends AbstractPlutoPortalTest {

    public MyPortletIT() {
        super("portlet-starter", "MyPortlet1");
    }

    @Test
    public void clickingButtonShowsNotification() {
        Assert.assertFalse($(NotificationElement.class).exists());

        getFirstPortlet().$(ButtonElement.class).first().click();

        Assert.assertTrue($(NotificationElement.class).waitForFirst().isOpen());
    }
}
