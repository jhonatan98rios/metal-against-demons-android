package com.teixeirarios.mad.lib.domain.entities.stage;


import com.teixeirarios.mad.lib.domain.entities.enemy.AbstractEcosystemFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class StageModel {
    private final int id;
    private final int maxEnemies;
    private final int totalEnemies;
    private final int spawnInterval;
    private final float baseDamage;
    private final float baseHealth;
    private final HashMap<AbstractEcosystemFactory, Integer> enemies;
    private final ArrayList<AbstractEcosystemFactory> bosses;

    public StageModel(
            int id,
            int maxEnemies,
            int totalEnemies,
            int spawnInterval,
            float baseDamage,
            float baseHealth,
            HashMap<AbstractEcosystemFactory, Integer> enemies,
            ArrayList<AbstractEcosystemFactory> bosses
    ) {
        this.id = id;
        this.maxEnemies = maxEnemies;
        this.totalEnemies = totalEnemies;
        this.spawnInterval = spawnInterval;
        this.baseDamage = baseDamage;
        this.baseHealth = baseHealth;
        this.enemies = enemies;
        this.bosses = bosses;
    }

    // Getters
    public int getId() { return id; }
    public int getMaxEnemies() { return maxEnemies; }
    public int getTotalEnemies() { return totalEnemies; }
    public int getSpawnInterval() { return spawnInterval; }
    public float getBaseDamage() { return baseDamage; }
    public float getBaseHealth() { return baseHealth; }
    public HashMap<AbstractEcosystemFactory, Integer> getEnemies() { return enemies; }

    public ArrayList<AbstractEcosystemFactory> getBosses() {
        return bosses;
    }
}
