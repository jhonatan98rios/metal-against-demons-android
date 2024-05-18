package com.teixeirarios.mad.lib.domain.entities.skills.soundattack;

import com.badlogic.gdx.graphics.Texture;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SoundAttackManagerBase implements AbstractSkillManager {

    static String category;
    Boolean isActive;
    String name;
    int width;
    int height;
    int speed;
    int damage;
    Texture spritesheet;
    int interval;
    int lifeTime;
    ArrayList<SoundAttackUnit> activeSkills;
    GameStatus gameStatus;

    EventManager eventManager;

    public SoundAttackManagerBase() {
        this.isActive = true;
        this.name = "Basic Sound Attack";
        this.category = "Sound Attack";
        this.width = 26;
        this.height = 26;
        this.speed = 3;
        this.damage = 10;
        this.spritesheet = null;
        this.interval = 500;
        this.lifeTime = 1000;
        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.eventManager = EventManager.getInstance();
    }

    @Override
    public void startSpawn(Player player , EnemyManager enemyManager) {
        this.intervaledSpawn(player, enemyManager);
    }

    public void intervaledSpawn(Player player, EnemyManager enemyManager) {

        if (this.isActive) {

            if (this.gameStatus.isPlaying()) {
                this.spawn(player, enemyManager);
                System.out.println("Spawn");
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    intervaledSpawn(player, enemyManager);
                }
            }, this.interval);
        }
    }

    public void spawn(Player player, EnemyManager enemyManager) {
        return;
    }

    @Override
    public void update(EnemyManager enemyManager) {
        this.move();
        this.updateLifeTime();
        this.checkSkillsCollision(enemyManager);
        this.checkLifeTime();
    }

    public void move() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.move();
        }
    }

    public void updateLifeTime() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.updateLifeTime();
        }
    }

    public void checkSkillsCollision(EnemyManager enemyManager) {
        if (this.activeSkills.isEmpty()) return;
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.checkCollision(
                    enemyManager.getEnemies(),
                    this::collision
            );
        }
    }

    public interface CollisionCallback {
        void collision(AbstractSkill skill, Enemy enemy);
    }

    public void collision(AbstractSkill skill, Enemy enemy) {
        this.remove(skill.getId());
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    public void checkLifeTime() {
        Iterator<SoundAttackUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            SoundAttackUnit skill = iterator.next();
            if (skill.lifeTime <= 0) {
                iterator.remove();
            }
        }
    }

    public void remove(UUID id) {
        Iterator<SoundAttackUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            SoundAttackUnit skill = iterator.next();
            if (skill.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void stop() {
        this.isActive = false;
    }

    @Override
    public AbstractSkillManager upgrade() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getCategory() {
        return category;
    }
}
