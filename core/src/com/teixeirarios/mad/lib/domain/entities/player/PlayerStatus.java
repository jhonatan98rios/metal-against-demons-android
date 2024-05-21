package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.infra.events.EventManager;

public class PlayerStatus {
    public int level, maxHealth, currentHealth, currentXP, nextLevelXp, totalXP;
    private final EventManager eventManager;

    public PlayerStatus() {
        level = 1;
        maxHealth = 500;
        currentHealth = 500;
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
            return;
        }

//        if (!this.vulnerable) return;
//        this.vulnerable = false;
//        setTimeout(() => {
//                this.vulnerable = true
//        }, 1000)
    }

    public void takeXp(int xp) {
        int updateCurrentXP = this.currentXP + xp;

        if (updateCurrentXP >= this.nextLevelXp) {
            this.levelup(this.nextLevelXp - updateCurrentXP);
            return;
        }

        this.currentXP += xp;
        this.totalXP += xp;
    }

    public void levelup(int remainingXp) {
        this.level += 1;
        this.nextLevelXp += (int) (this.nextLevelXp * 0.2);
        this.currentXP = remainingXp;
        this.maxHealth += 1;
        this.currentHealth += 1;
        this.eventManager.emit("player:levelup");
    }

    public void die() {
        this.eventManager.emit("player:die");
    }
}
