package com.teixeirarios.mad.lib.domain.entities.skills.abstracts;

import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;

public interface AbstractSkillManager {

    void startSpawn(Player player, EnemyManager enemyManager);
    void spawn(Player player, EnemyManager enemyManager);
    void update(EnemyManager enemyManager);
    AbstractSkillManager upgrade();
    void stop();

    String getCategory();
}
