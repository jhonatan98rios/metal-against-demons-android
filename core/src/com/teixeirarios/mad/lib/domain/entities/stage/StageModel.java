package com.teixeirarios.mad.lib.domain.entities.stage;


public class StageModel {
    private final int id;
    private final int maxEnemies;
    private final int totalEnemies;
    private final int spawnInterval;
    private final float baseDamage;
    private final float baseHealth;

    public StageModel(int id, int maxEnemies, int totalEnemies, int spawnInterval, float baseDamage, float baseHealth) {
        this.id = id;
        this.maxEnemies = maxEnemies;
        this.totalEnemies = totalEnemies;
        this.spawnInterval = spawnInterval;
        this.baseDamage = baseDamage;
        this.baseHealth = baseHealth;
    }

    // Getters
    public int getId() { return id; }
    public int getMaxEnemies() { return maxEnemies; }
    public int getTotalEnemies() { return totalEnemies; }
    public int getSpawnInterval() { return spawnInterval; }
    public float getBaseDamage() { return baseDamage; }
    public float getBaseHealth() { return baseHealth; }
}
