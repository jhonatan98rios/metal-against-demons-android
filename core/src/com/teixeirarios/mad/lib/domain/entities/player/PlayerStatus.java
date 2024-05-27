package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.infra.events.EventManager;

public class PlayerStatus {
    public int level;
    public float maxHealth, currentHealth, totalXP, nextLevelXp, currentXP;
    private final EventManager eventManager;

    public PlayerStatus() {
        level = 1;
        maxHealth = 1000;
        currentHealth = 1000;
        currentXP = 0;
        nextLevelXp = 20;
        totalXP = 0;

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void addEventListeners() {
        eventManager.on("enemy:collision", args -> {
            takeDamage((int) args[0]);
        });
    }

    public void takeDamage(int damage) {
        //this.currentHealth -= damage;

        if (this.currentHealth <= 0) {
            die();
        }
    }

    public void takeXp(float xp) {
        float updateCurrentXP = this.currentXP + xp;

        if (updateCurrentXP >= this.nextLevelXp) {
            this.levelup(this.nextLevelXp - updateCurrentXP);
            return;
        }

        this.currentXP += xp;
        this.totalXP += xp;
    }

    public void levelup(float remainingXp) {
        this.level += 1;
        this.nextLevelXp = (float) (this.nextLevelXp * 1.5);
        this.currentXP = remainingXp;
        this.maxHealth += 1;
        this.currentHealth += 1;
        this.eventManager.emit("player:levelup");
    }

    public void die() {
        this.eventManager.emit("player:die");
    }
}
