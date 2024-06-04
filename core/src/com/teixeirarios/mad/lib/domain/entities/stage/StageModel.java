package com.teixeirarios.mad.lib.domain.entities.stage;

public class StageModel {
    private String id;
    private int maxEnemies;
    private int spawnInterval;
    private float baseDamage;
    private float baseHealth;

    public StageModel(String id, int maxEnemies, int spawnInterval, float baseDamage, float baseHealth) {
        this.id = id;
        this.maxEnemies = maxEnemies;
        this.spawnInterval = spawnInterval;
        this.baseDamage = baseDamage;
        this.baseHealth = baseHealth;
    }

    // Getters
    public String getId() { return id; }
    public int getMaxEnemies() { return maxEnemies; }
    public int getSpawnInterval() { return spawnInterval; }
    public float getBaseDamage() { return baseDamage; }
    public float getBaseHealth() { return baseHealth; }
}
