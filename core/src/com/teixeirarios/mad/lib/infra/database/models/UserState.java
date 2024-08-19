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
    public Float luck;
    public int currentStage;

    public UserState() {}

    public UserState(
        long id,
        long money,
        int level,
        long experience,
        long nextLevelUp,
        int points,
        long health,
        float strength,
        float dexterity,
        float luck,
        int currentStage
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
        this.luck = luck;
        this.currentStage = currentStage;
    }
}
