package com.teixeirarios.mad.lib.domain.entities.skills;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.domain.entities.skills.forcefield.ForceFieldManager;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManager;

import java.util.ArrayList;

public class SkillManager {
    private static SkillManager instance;
    public ArrayList<AbstractSkill> activeSkills;
    public ArrayList<AbstractSkillManager> availableSkills;

    private SkillManager(SpriteBatch batch) {
        this.activeSkills = new ArrayList<>();
        this.availableSkills = new ArrayList<>();
        this.availableSkills.add(new ForceFieldManager(batch));
        this.availableSkills.add(new SoundAttackManager(batch));
    }

    public static SkillManager getInstance(SpriteBatch batch) {
        if (SkillManager.instance == null) {
            SkillManager.instance = new SkillManager(batch);
        }
        return SkillManager.instance;
    }

    public void startSpawn(Player player, EnemyManager enemyService) {
        for (int i = 0; i < availableSkills.size(); i++) {
            AbstractSkillManager availableSkill = availableSkills.get(i);
            availableSkill.startSpawn(player, enemyService);
            System.out.println(availableSkill.getCategory() + " started");
        }
    }

    public void update(EnemyManager enemyManager) {
        for (int i = 0; i < availableSkills.size(); i++) {
            AbstractSkillManager availableSkill = availableSkills.get(i);
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
