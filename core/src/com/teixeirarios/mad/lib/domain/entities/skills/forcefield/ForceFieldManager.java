package com.teixeirarios.mad.lib.domain.entities.skills.forcefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ForceFieldManager implements AbstractSkillManager {

    private static String category;
    private int level, width, height, frame_amount;
    private int damage;
    private String spritesheet;
    private Texture texture;
    private ArrayList<ForceFieldUnit> activeSkills;
    private EventManager eventManager;
    private EnemyManager enemyManager;
    private SpriteBatch batch;

    public ForceFieldManager(SpriteBatch batch) {
        category = "Force Field";
        this.level = 1;
        this.width = 240;
        this.height = 240;
        this.damage = 1;

        this.spritesheet = "skills/force_field_1.png";
        this.texture = new Texture(this.spritesheet);

        this.activeSkills = new ArrayList<>();
        this.batch = batch;
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

        ForceFieldUnit unit = new ForceFieldUnit(
            player.getPosX() - width,
            player.getPosY() + height,
            width,
            height,
            damage,
            texture,
            frame_amount,
            batch
        );

        activeSkills.add(unit);
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
            this.texture = new Texture(this.spritesheet);
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{320, 320});
        dimensions.put(3, new int[]{400, 400});
        dimensions.put(4, new int[]{480, 480});
        dimensions.put(5, new int[]{560, 560});

        if (dimensions.containsKey(level)) {
            int[] size = dimensions.get(level);
            width = size[0];
            height = size[1];
        }

        activeSkills.clear();
        startSpawn(Player.getInstance(), enemyManager);
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

    private void collision(UUID id, Enemy enemy) {
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    @Override
    public void addEventListeners() {
        eventManager.on("player:levelup:forcefield", args -> {
            upgrade();
        });
    }
}
