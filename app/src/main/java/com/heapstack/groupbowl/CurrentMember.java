package com.heapstack.groupbowl;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jong on 1/20/15.
 */
public class CurrentMember {

    public static String userEmail;
    public static String userPhone;
    public static String userName;
    public static ArrayList userGroup;
    public static ParseObject user;


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

    public static void setUserGroup(ArrayList group) {
        userGroup = group;
    }

    public static ArrayList getUserGroup() {
        return userGroup;
    }

    public static void setUser(ParseObject inputUser) {
        user = inputUser;
    }

    public static ParseObject getUser() {
        return user;
    }



}
