package com.heapstack.groupbowl;

import java.lang.reflect.Array;

/**
 * Created by Jong on 1/20/15.
 */
public class CurrentMember {

    public static String userEmail;
    public static String userPhone;
    public static String userName;
    public static Array userGroup;


    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserPhone(String phone) {
        userPhone = phone;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static void setUserGroup(Array group) {
        userGroup = group;
    }

    public static Array getUserGroup() {
        return userGroup;
    }




}
