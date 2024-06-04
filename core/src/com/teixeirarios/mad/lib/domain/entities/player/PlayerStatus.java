package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.math.BigInteger;

public class PlayerStatus {
    public int level;
    public float maxHealth, currentHealth, nextLevelXp, currentXP;

    public BigInteger totalXP;
    private final EventManager eventManager;

    public PlayerStatus() {
        level = 1;
        maxHealth = 1000;
        currentHealth = 1000;
        currentXP = 0;
        nextLevelXp = 100;
        totalXP = BigInteger.valueOf(0);

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void addEventListeners() {
        eventManager.on("enemy:collision", args -> {
            takeDamage((int) args[0]);
        });
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;

        if (this.currentHealth <= 0) {
            die();
        }
    }

    public void takeXp(float xp) {
        float updateCurrentXP = this.currentXP + xp;

        if (updateCurrentXP >= this.nextLevelXp) {
            updateCurrentXP -= this.nextLevelXp;
            this.levelup();
        }

        this.currentXP = updateCurrentXP;
        this.totalXP = this.totalXP.add(BigInteger.valueOf((long) xp));
    }

    public void levelup() {
        this.level += 1;
        this.nextLevelXp = this.nextLevelXp * 1.5f;
        this.maxHealth += 1;
        this.currentHealth += 1;
        this.eventManager.emit("player:levelup");
        //eventManager.emit("player:levelup:forcefield");
    }

    public void die() {
        this.eventManager.emit("player:die");
    }
}
