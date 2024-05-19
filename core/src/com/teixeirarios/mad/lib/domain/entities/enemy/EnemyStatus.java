package com.teixeirarios.mad.lib.domain.entities.enemy;

public class EnemyStatus {

    public int maxHealth, currentHealth, damage;

    public EnemyStatus(int maxHealth, int damage) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
    }
}
