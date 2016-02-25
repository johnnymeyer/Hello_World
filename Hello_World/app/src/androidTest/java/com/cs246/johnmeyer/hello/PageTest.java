package com.cs246.johnmeyer.hello;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Klenton on 2/24/2016.
 */
public class PageTest {
    public void getMyTitleTest() {
        Page test = new Page();
        assertNull(test.getMyTitle());
    }

    public void setMyTitleTest() {
        Page test = new Page();
        test.setMyTitle("Page 1");
        assertNotNull((test.getMyTitle()));
    }

    public void test-getContent() {
        Page test = new Page();
        assertNull(test.getContent());
    }

    public void test-fetchContent() {
        Page test = new Page();
        test.fetchContent();
        assertNotNull(test.getContent());
    }
}
