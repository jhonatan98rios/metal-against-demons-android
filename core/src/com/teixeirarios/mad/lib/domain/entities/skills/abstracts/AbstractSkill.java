package com.teixeirarios.mad.lib.domain.entities.skills.abstracts;

import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;

import java.util.ArrayList;
import java.util.UUID;

public interface AbstractSkill {
    void move();
    void checkCollision(ArrayList<Enemy> enemies, CollisionCallback callback);
    void spawn(Player player, EnemyManager enemyManager);
    UUID getId();

    interface CollisionCallback {
        void collision(UUID id, Enemy enemy);
    }
}
