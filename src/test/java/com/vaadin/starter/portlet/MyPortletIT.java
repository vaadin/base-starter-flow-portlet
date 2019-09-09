package com.vaadin.starter.portlet;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.SelectElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;

public class MyPortletIT extends AbstractPlutoPortalTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        // Go to admin page
        findElement(By.xpath("//*[@id=\"navigation\"]/li[4]/a")).click();
        Map<String, SelectElement> nameMap = $(SelectElement.class).all().stream().collect(Collectors
                .toMap(selectElement -> selectElement.getAttribute("name"),
                        Function.identity(), (oldValue, newValue) -> oldValue));
        nameMap.get("page").selectByText("About Apache Pluto");
        nameMap.get("applications").selectByText("/portlet-starter");
        nameMap.get("availablePortlets").selectByText("MyPortlet1");
        findElement(By.id("addButton")).click();

        // go to
        findElement(By.xpath("//*[@id=\"navigation\"]/li[1]/a")).click();
    }

    @Test
    public void clickingButtonShowsNotification() {
        Assert.assertFalse($(NotificationElement.class).exists());

        $(ButtonElement.class).first().click();

        Assert.assertTrue($(NotificationElement.class).waitForFirst().isOpen());
    }
}
