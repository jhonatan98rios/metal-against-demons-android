package com.teixeirarios.mad.lib.domain.entities.player;

public class PlayerStatus {
    public int level, maxHealth, currentHealth, currentXP, nextLevelXp;

    public PlayerStatus() {
        level = 1;
        maxHealth = 100;
        currentHealth = maxHealth;
        currentXP = 0;
        nextLevelXp = 100;
    }
}
