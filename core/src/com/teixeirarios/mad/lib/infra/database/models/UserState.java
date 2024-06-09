package com.teixeirarios.mad.lib.infra.database.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserState {

    public UserState() {}

    public UserState(Long id, Long money) {
        this.id = id;
        this.money = money;
    }

    @Id
    public Long id;
    public Long money;
}
