package com.cs246.johnmeyer.hello;

/**
 * Created by edoyle on 2/20/16.
 */
public class PageData {
        private String nav_prev = "0";
        private String nav_next = "0";

    public void setNav_next(String nav_next) {
        this.nav_next = nav_next;
    }

    public void setNav_prev(String nav_prev) {
        this.nav_prev = nav_prev;
    }

    public String getNav_next() {
        return nav_next;
    }

    public String getNav_prev() {
        return nav_prev;
    }
}
