package com.teixeirarios.mad.lib.domain.entities.skills.abstracts;

import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManager.CollisionCallback;

import java.util.UUID;

public interface AbstractSkill {
    void move();
    void checkCollision(Array<Enemy> enemies, CollisionCallback callback);
    void startSpawn(Player player, EnemyManager enemyManager);
    void spawn(Player player, EnemyManager enemyManager);
    UUID getId();
}
