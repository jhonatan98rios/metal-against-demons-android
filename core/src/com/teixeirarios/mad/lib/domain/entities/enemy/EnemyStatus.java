package com.teixeirarios.mad.lib.domain.entities.enemy;

public class EnemyStatus {

    public float maxHealth, currentHealth, damage;

    public EnemyStatus(int maxHealth, float damage) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
    }
}
