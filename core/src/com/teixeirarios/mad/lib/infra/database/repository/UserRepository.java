package com.teixeirarios.mad.lib.infra.database.repository;

import com.teixeirarios.mad.lib.infra.database.models.MyObjectBox;
import com.teixeirarios.mad.lib.infra.database.models.UserState;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class UserRepository {
    private static UserRepository instance;
    private final Box<UserState> userStateBox;

    public static synchronized UserRepository getInstance(AppContext context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserRepository must be initialized");
        }
        return instance;
    }

    private UserRepository(AppContext context) {
        BoxStore boxStore = MyObjectBox
            .builder()
            .androidContext(context.getApplicationContext())
            .name("teste-1")
            .build();

        //BoxStore boxStore = null;
        userStateBox = boxStore.boxFor(UserState.class);
    }

    public UserState getUserState() {
        return userStateBox.getAll().isEmpty() ? null : userStateBox.getAll().get(0);
    }

    public void setUserState(UserState userState) {
        userStateBox.removeAll();
        userStateBox.put(userState);
    }
}