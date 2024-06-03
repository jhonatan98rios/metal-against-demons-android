package com.teixeirarios.mad.lib.domain.entities.skills;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.domain.entities.skills.forcefield.ForceFieldManager;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManager;
import com.teixeirarios.mad.lib.domain.entities.skills.vampires.VampiresManager;

import java.util.ArrayList;

public class SkillManager {
    public ArrayList<AbstractSkill> activeSkills;
    public ArrayList<AbstractSkillManager> availableSkills;

    public SkillManager(SpriteBatch batch, EnemyManager enemyManager) {
        this.activeSkills = new ArrayList<>();
        this.availableSkills = new ArrayList<>();
        this.availableSkills.add(new SoundAttackManager(batch, enemyManager));
        this.availableSkills.add(new ForceFieldManager(batch, enemyManager));
        this.availableSkills.add(new VampiresManager(batch, enemyManager));
    }

    public void startSpawn(Player player) {
        for (int i = 0; i < availableSkills.size(); i++) {
            AbstractSkillManager availableSkill = availableSkills.get(i);
            availableSkill.spawn(player);
        }
    }

    public void update() {
        for (int i = 0; i < availableSkills.size(); i++) {
            AbstractSkillManager availableSkill = availableSkills.get(i);
            availableSkill.update();
        }
    }

    public void getUpgradeOptions() {
        throw new RuntimeException("Not implemented");
    }

    public void buyOrUpgradeSkill(AbstractSkillManager newSkill) {
        throw new RuntimeException("Not implemented");
    }
}
