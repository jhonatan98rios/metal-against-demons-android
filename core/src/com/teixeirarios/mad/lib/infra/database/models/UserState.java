package com.teixeirarios.mad.lib.infra.database.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserState {

    @Id
    public long id;
    public long money;
    public Integer level;
    public long experience;
    public long nextLevelUp;
    public Integer points;
    public long health;
    public Float strength;
    public Float dexterity;
    public Float speed;
    public Float luck;

    public UserState() {}

    public UserState(
        long id,
        long money,
        Integer level,
        long experience,
        long nextLevelUp,
        Integer points,
        long health,
        Float strength,
        Float dexterity,
        Float speed,
        Float luck
    ) {
        this.id = id;
        this.money = money;
        this.level = level;
        this.experience = experience;
        this.nextLevelUp = nextLevelUp;
        this.points = points;
        this.health = health;
        this.strength = strength;
        this.dexterity = dexterity;
        this.speed = speed;
        this.luck = luck;
    }
}
