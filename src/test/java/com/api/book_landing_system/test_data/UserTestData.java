package com.api.book_landing_system.test_data;

import com.api.book_landing_system.model.UserEntity;

import java.util.ArrayList;

public class UserTestData {
    private static final String USERNAME = "John";
    private static final String PASSWORD = "12345";
    public static UserEntity getTestUser(){
        return UserEntity.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .roles(new ArrayList<>())
                .loans(new ArrayList<>())
                .build();
    }
}
