package com.cs246.johnmeyer.hello;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ListView;

import junit.framework.Assert;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by edoyle on 2/24/16.
 */

public class TOCTests {

    @Test
    public void testTopicsArray() {
        assertNotNull((new TableOfContents()).CONTENT);
    }

    @Test
    public void testNextPageIndicator(){
       assertEquals((new TableOfContents()).getNextPageIndicator(), 2);
    }

    @Test
    public void testListLayout() {
        assertNotNull((new TableOfContents()).getLayout());
    }

    @Test
    public void testAdapter() {
        assertNotNull((new TableOfContents()).getAdapter());
    }
}
