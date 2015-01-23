package com.heapstack.groupbowl;

/**
 * Created by Jong on 1/20/15.
 */
public class CurrentGroup {

    public static String currentGroupName;
    public static String currentTitle;

    public static void setCurrentGroupName(String name) {
        currentGroupName = name;
    }

    public static String getCurrentGroupName() {
        return currentGroupName;
    }

    public static void setCurrentTitle(String title) {
        currentTitle = title;
    }

    public static String getCurrentTitle() {
        return currentTitle;
    }

}
