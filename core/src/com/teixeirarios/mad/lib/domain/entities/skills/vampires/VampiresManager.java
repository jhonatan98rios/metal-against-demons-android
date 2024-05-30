package com.teixeirarios.mad.lib.domain.entities.skills.vampires;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class VampiresManager implements AbstractSkillManager {

    private static String category;
    private final int frame_amount;
    private final ArrayList<VampiresUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final SpriteBatch batch;
    private int level, width, height, damage, interval, lifeTime;
    private float speed;
    private String spritesheet;
    private Texture texture;

    public VampiresManager(SpriteBatch batch) {
        category = "Vampires Horde";
        this.level = 1;
        this.width = 48;
        this.height = 48;
        this.speed = 0.05f;
        this.damage = 5;
        this.interval = 7000;
        this.lifeTime = 600;

        this.spritesheet = "skills/bat_attack_1.png";
        this.texture = new Texture(this.spritesheet);
        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.frame_amount = 2;

        this.enemyManager = EnemyManager.getInstance();
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    @Override
    public void startSpawn(Player player, EnemyManager enemyManager) {
        this.intervaledSpawn(player, enemyManager);
    }

    private void intervaledSpawn(Player player, EnemyManager enemyManager) {
        if (gameStatus.isPlaying()) {
            spawn(player, enemyManager);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                intervaledSpawn(player, enemyManager);
            }
        }, interval);
    }

    @Override
    public void spawn(Player player, EnemyManager enemyManager) {

        Array<Enemy> enemies = enemyManager.getEnemies();

        if (player == null || enemies.isEmpty()) return;

        VampiresUnit unit = new VampiresUnit(
            player.getPosX() + (player.getWidth() / 2),
            player.getPosY() + (player.getHeight() / 2),
            enemies.get(0).getPosX(),
            enemies.get(0).getPosY(),
            width,
            height,
            damage,
            speed,
            texture,
            frame_amount,
            lifeTime,
            batch
        );

        this.addActiveSkills(unit);
    }

    @Override
    public void update(EnemyManager enemyManager) {
        move();
        updateLifeTime();
        checkSkillsCollision();
        checkLifeTime();
    }

    private void move() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            VampiresUnit unit = this.activeSkills.get(i);
            unit.move();
        }
    }

    private void updateLifeTime() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            VampiresUnit unit = this.activeSkills.get(i);
            unit.updateLifeTime();
        }
    }

    private void checkSkillsCollision() {
        if (this.activeSkills.isEmpty()) return;
        for (int i = 0; i < this.activeSkills.size(); i++) {
            VampiresUnit unit = this.activeSkills.get(i);
            unit.checkCollision(
                    enemyManager.getEnemies(),
                    this::collision
            );
        }
    }

    private void collision(UUID id, Enemy enemy) {
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    private void checkLifeTime() {
        List<VampiresUnit> toRemove = new ArrayList<>();

        // Atualize a vida útil dos objetos
        for (int i = 0; i < this.activeSkills.size(); i++) {
            VampiresUnit skill = this.activeSkills.get(i);
            skill.updateLifeTime();
            if (skill.getLifeTime() <= 0) {
                toRemove.add(skill);
            }
        }

        activeSkills.removeAll(toRemove);
    }

    private void remove(UUID id) {
        Iterator<VampiresUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            VampiresUnit skill = iterator.next();
            if (skill.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void upgrade() {

        activeSkills.clear();

        level += 1;
        damage += 1;
        interval -= 50;
        lifeTime += 100;

        if (level <= 5) {
            this.spritesheet = "skills/bat_attack_" + level + ".png";
            this.texture = new Texture(this.spritesheet);
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{56, 56});
        dimensions.put(3, new int[]{80, 80});
        dimensions.put(4, new int[]{112, 112});
        dimensions.put(5, new int[]{160, 160});

        if (dimensions.containsKey(level)) {
            int[] size = dimensions.get(level);
            width = size[0];
            height = size[1];
        }
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void addActiveSkills(VampiresUnit activeSkill) {
        this.activeSkills.add(activeSkill);
    }

    @Override
    public void addEventListeners() {
        eventManager.on("player:levelup:vampires", args -> {
            upgrade();
        });
    }
}
