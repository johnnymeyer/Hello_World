package com.cs246.johnmeyer.hello;

import android.provider.BaseColumns;

/**
 * Created by edoyle on 2/20/16.
 */
public class PageData {
    public PageData(){

    }
    public static abstract class PageInfo implements BaseColumns{
        public static final String PAGE_NAME = "page_name";
        public static final String NAV_PREV = "page_name-1";
        public static final String NAV_NEXT = "page_name-2";




    }
}
