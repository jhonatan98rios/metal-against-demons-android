package com.teixeirarios.mad.lib.domain.entities.skills.soundattack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SoundAttackManager implements AbstractSkillManager {

    private static String category;
    private final int lifeTime, frame_amount, speed;
    private final ArrayList<SoundAttackUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final SpriteBatch batch;
    private int level, width, height, damage, interval, range;
    private String spritesheet;
    private Texture texture;

    public SoundAttackManager(SpriteBatch batch) {
        category = "Sound Attack";
        this.level = 1;
        this.width = 26;
        this.height = 26;
        this.speed = 3;
        this.damage = 100;
        this.range = 500;

        this.spritesheet = "skills/sound_attack_1.png";
        this.texture = new Texture(this.spritesheet);

        this.interval = 1000;
        this.lifeTime = 60 * 5;
        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.frame_amount = 4;

        this.enemyManager = EnemyManager.getInstance();
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    @Override
    public void startSpawn(Player player , EnemyManager enemyManager) {
        this.intervaledSpawn(player, enemyManager);
    }

    private void intervaledSpawn(Player player, EnemyManager enemyManager) {
        if (this.gameStatus.isPlaying()) {
            this.spawn(player, enemyManager);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                intervaledSpawn(player, enemyManager);
            }
        }, this.interval);
    }

    @Override
    public void spawn(Player player, EnemyManager enemyManager) {
        if (player == null || enemyManager == null || enemyManager.getEnemies().size == 0) return;

        Array<Enemy> nearbyEnemies = getNearbyEnemies(player, enemyManager);

        if (!nearbyEnemies.isEmpty()) {
            Enemy targetEnemy = nearbyEnemies.get(0);

            SoundAttackUnit soundAttackUnit = new SoundAttackUnit(
                    player.getPosX() + (player.getWidth() / 2),
                    player.getPosY() + (player.getHeight() / 2),
                    targetEnemy.getPosX() + (targetEnemy.getWidth() / 2),
                    targetEnemy.getPosY() + (targetEnemy.getHeight() / 2),
                    width,
                    height,
                    damage,
                    speed,
                    texture,
                    frame_amount,
                    lifeTime,
                    batch
            );

            this.addActiveSkills(soundAttackUnit);
        }
    }

    private Array<Enemy> getNearbyEnemies(Player player, EnemyManager enemyManager) {
        SoundAttackManager.RangeArea rangeArea = new SoundAttackManager.RangeArea(
                player.getPosX() - range,
                player.getPosY() - range,
                player.getPosX() + range,
                player.getPosY() + range
        );

        Array<Enemy> nearbyEnemies = new Array<>();
        Array<Enemy> enemies = enemyManager.getEnemies();

        if (enemies == null) {
            System.err.println("Error: enemies array is null");
            return nearbyEnemies;
        }

        if (enemies.size > 1) {
            sortEnemiesByDistance(enemies, player);
        }

        for (int index = 0; index < enemies.size; index++) {
            Enemy enemy = enemies.get(index);

            if (enemy.getPosX() >= rangeArea.left &&
                    enemy.getPosX() <= rangeArea.right &&
                    enemy.getPosY() >= rangeArea.top &&
                    enemy.getPosY() <= rangeArea.bottom) {
                nearbyEnemies.add(enemy);
            }
        }
        return nearbyEnemies;
    }

    private void sortEnemiesByDistance(Array<Enemy> enemies, Player player) {
        ListUtils.bubbleSort(enemies, player);
    }

    private static class RangeArea {
        public float left, top, right, bottom;

        public RangeArea(float left, float top, float right, float bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }

    @Override
    public void update(EnemyManager enemyManager) {
        this.move();
        this.updateLifeTime();
        this.checkSkillsCollision();
        this.checkLifeTime();
    }

    private void move() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.move();
        }
    }

    private void updateLifeTime() {
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.updateLifeTime();
        }
    }

    private void checkSkillsCollision() {
        if (this.activeSkills.isEmpty()) return;
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit activeSkill = this.activeSkills.get(i);
            activeSkill.checkCollision(
                    enemyManager.getEnemies(),
                    this::collision
            );
        }
    }

    private void collision(UUID id, Enemy enemy) {
        this.remove(id);
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    private void checkLifeTime() {
        List<SoundAttackUnit> toRemove = new ArrayList<>();

        // Atualize a vida útil dos objetos
        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit skill = this.activeSkills.get(i);
            skill.updateLifeTime();
            if (skill.getLifeTime() <= 0) {
                toRemove.add(skill);
            }
        }

        activeSkills.removeAll(toRemove);
    }

    private void remove(UUID id) {
        Iterator<SoundAttackUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            SoundAttackUnit skill = iterator.next();
            if (skill.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void upgrade() {
        damage += 50;
        interval -= 50;
        level += 1;
        range += 100;

        if (level <= 5) {
            this.spritesheet = "skills/sound_attack_" + level + ".png";
            this.texture = new Texture(this.spritesheet);
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{34, 35});
        dimensions.put(3, new int[]{47, 47});
        dimensions.put(4, new int[]{47, 47});
        dimensions.put(5, new int[]{48, 48});

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

    public void addActiveSkills(SoundAttackUnit activeSkill) {
        this.activeSkills.add(activeSkill);
    }

    private void addEventListeners() {
        eventManager.on("player:levelup:sound", args -> {
            upgrade();
        });
    }
}