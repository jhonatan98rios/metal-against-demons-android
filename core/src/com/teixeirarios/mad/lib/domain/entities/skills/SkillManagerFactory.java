package com.teixeirarios.mad.lib.domain.entities.skills;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;

public class SkillManagerFactory {

    public static SkillManager create(Player player, SpriteBatch batch) {
        SkillManager skillManager = SkillManager.getInstance(batch);
        EnemyManager enemyManager = EnemyManager.getInstance();
        skillManager.startSpawn(player, enemyManager);
        return skillManager;
    }
}
