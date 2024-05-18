package com.teixeirarios.mad.lib.domain.entities.skills;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManager1;

import java.util.ArrayList;
import java.util.Optional;

public class SkillManager {
    private static SkillManager instance;
    public ArrayList<AbstractSkill> activeSkills;
    public ArrayList<AbstractSkillManager> availableSkills;


    private SkillManager(SpriteBatch batch) {
        this.activeSkills = new ArrayList<>();
        this.availableSkills = new ArrayList<>();
        this.availableSkills.add(
                new SoundAttackManager1(batch)
        );
    }

    public static SkillManager getInstance(SpriteBatch batch) {
        if (SkillManager.instance == null) {
            SkillManager.instance = new SkillManager(batch);
        }
        return SkillManager.instance;
    }

    public void startSpawn(Player player, EnemyManager enemyService, Optional<String> category) {

        for (AbstractSkillManager availableSkill : availableSkills) {
            if (category != null && !availableSkill.getCategory().equals(category)) {
                continue;
            }
            availableSkill.startSpawn(player, enemyService);
        }
    }

    public void update(EnemyManager enemyManager) {
        for (AbstractSkillManager availableSkill : availableSkills) {
            availableSkill.update(enemyManager);
        }
    }

    public void getUpgradeOptions() {
        throw new RuntimeException("Not implemented");
    }

    public void buyOrUpgradeSkill(AbstractSkillManager newSkill) {
        throw new RuntimeException("Not implemented");
    }
}
