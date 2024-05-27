package com.teixeirarios.mad.lib.domain.entities.skills.forcefield;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ForceFieldManager implements AbstractSkillManager {

    private static String category;
    private Boolean isActive, isAnimated;
    private int level, width, height, speed, interval, lifeTime, frame_amount;
    private int damage;
    private String spritesheet;
    private ArrayList<ForceFieldUnit> activeSkills;
    private GameStatus gameStatus;
    private EventManager eventManager;
    private EnemyManager enemyManager;
    private SpriteBatch batch;

    public ForceFieldManager(SpriteBatch batch) {
        category = "Force Field";
        this.isActive = true;
        this.level = 1;
        this.width = 152*2;
        this.height = 152*2;
        this.speed = 0;
        this.damage = 1;
        this.spritesheet = "skills/force_field_1.png";
        this.interval = 100;

        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.isAnimated = true;
        this.frame_amount = 4;

        this.enemyManager = EnemyManager.getInstance();
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    @Override
    public void startSpawn(Player player, EnemyManager enemyManager) {
        spawn(player, enemyManager);
    }

    @Override
    public void spawn(Player player, EnemyManager enemyManager) {

        ForceFieldUnit forceFieldUnit = new ForceFieldUnit(
            player.getPosX() - (width / 2),
            player.getPosY() + (height / 2),
            player.getPosX() - (width / 2),
            player.getPosY() + (height / 2),
            width,
            height,
            damage,
            speed,
            isAnimated,
            spritesheet,
            frame_amount,
            lifeTime,
            batch
        );

        activeSkills.add(forceFieldUnit);
    }

    @Override
    public void update(EnemyManager enemyManager) {
        move();
        checkSkillsCollision();
    }

    @Override
    public void upgrade() {
        damage += 1;
        level += 1;

        if (level <= 5) {
            this.spritesheet = "skills/force_field_" + level + ".png";
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{180*2, 180*2});
        dimensions.put(3, new int[]{200*2, 200*2});
        dimensions.put(4, new int[]{250*2, 250*2});
        dimensions.put(5, new int[]{300*2, 300*2});

        if (dimensions.containsKey(level)) {
            int[] size = dimensions.get(level);
            width = size[0];
            height = size[1];
        }

        activeSkills.clear();
        startSpawn(Player.getInstance(), enemyManager);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCategory() {
        return category;
    }

    private void move() {
        for (int i = 0; i < activeSkills.size(); i++) {
            ForceFieldUnit activeSkill = activeSkills.get(i);
            activeSkill.move();
        }
    }

    private void checkSkillsCollision() {
        if (this.activeSkills.isEmpty()) return;
        for (int i = 0; i < this.activeSkills.size(); i++) {
            ForceFieldUnit activeSkill = this.activeSkills.get(i);
            activeSkill.checkCollision(
                    enemyManager.getEnemies(),
                    this::collision
            );
        }
    }

    public interface CollisionCallback {
        void collision(AbstractSkill skill, Enemy enemy);
    }

    private void collision(AbstractSkill skill, Enemy enemy) {
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    private void addEventListeners() {
        eventManager.on("player:levelup:forcefield", args -> {
            upgrade();
        });
    }
}
