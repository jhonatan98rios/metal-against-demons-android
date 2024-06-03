package com.teixeirarios.mad.lib.domain.entities.skills.soundattack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import java.util.UUID;

public class SoundAttackManager implements AbstractSkillManager {

    private String category;
    private final int lifeTime, frame_amount, speed;
    private final ArrayList<SoundAttackUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final Player player;
    private final SpriteBatch batch;
    private int level, width, height, damage, range;
    private String spritesheet;
    private Texture texture;
    private float accumulatedTime, interval;

    public SoundAttackManager(SpriteBatch batch, EnemyManager enemyManager) {
        this.category = "Sound Attack";
        this.level = 1;
        this.width = 26;
        this.height = 26;
        this.speed = 3;
        this.damage = 100;
        this.range = 500;

        this.player = Player.getInstance();

        this.spritesheet = "skills/sound_attack_1.png";
        this.texture = new Texture(this.spritesheet);

        this.interval = 1f;
        this.lifeTime = 300;
        this.accumulatedTime = 0;

        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.frame_amount = 4;

        this.enemyManager = enemyManager;
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void intervaledSpawn(float deltaTime) {
        if (this.gameStatus.isPlaying()) {
            accumulatedTime += deltaTime;
            if (accumulatedTime >= interval) {
                spawn(player);
                accumulatedTime = 0; // Reset the accumulated time
            }
        }
    }

    @Override
    public void spawn(Player player) {
        if (player == null || enemyManager == null || enemyManager.getEnemies().isEmpty()) return;

        ArrayList<Enemy> nearbyEnemies = getNearbyEnemies(enemyManager);
        if (nearbyEnemies.isEmpty()) return;

        Enemy targetEnemy = nearbyEnemies.get(0);
        SoundAttackUnit soundAttackUnit = new SoundAttackUnit(
                player.getPosX() + (player.getWidth() / 2) - width,
                player.getPosY() + (player.getHeight() / 2) - height,
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

    private ArrayList<Enemy> getNearbyEnemies(EnemyManager enemyManager) {
        SoundAttackManager.RangeArea rangeArea = new SoundAttackManager.RangeArea(
            player.getPosX() - range,
            player.getPosY() - range,
            player.getPosX() + range,
            player.getPosY() + range
        );

        ArrayList<Enemy> nearbyEnemies = new ArrayList<>();
        ArrayList<Enemy> enemies = enemyManager.getEnemies();

        if (enemies == null) {
            System.err.println("Error: enemies array is null");
            return nearbyEnemies;
        }

        if (enemies.size() > 1) {
            sortEnemiesByDistance(enemies);
        }

        for (int index = 0; index < enemies.size(); index++) {
            Enemy enemy = enemies.get(index);

            if (enemy.getPosX() + enemy.getWidth() >= rangeArea.left &&
                    enemy.getPosX() <= rangeArea.right &&
                    enemy.getPosY() >= rangeArea.top &&
                    enemy.getPosY() + enemy.getHeight() <= rangeArea.bottom) {
                nearbyEnemies.add(enemy);
            }
        }
        return nearbyEnemies;
    }

    private void sortEnemiesByDistance(ArrayList<Enemy> enemies) {
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
    public void update() {
        intervaledSpawn(Gdx.graphics.getDeltaTime());

        if (this.activeSkills.isEmpty()) return;

        for (int i = 0; i < this.activeSkills.size(); i++) {
            SoundAttackUnit unit = this.activeSkills.get(i);
            unit.move();
            unit.updateLifeTime();
            unit.checkCollision(
                enemyManager.getEnemies(),
                this::collision
            );
        }

        checkLifeTime();
    }

    private void collision(UUID id, Enemy enemy) {
        this.remove(id);
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    private void checkLifeTime() {
        Iterator<SoundAttackUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            SoundAttackUnit skill = iterator.next();
            skill.updateLifeTime();
            if (skill.getLifeTime() <= 0) {
                iterator.remove();
            }
        }
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
        activeSkills.clear();

        damage += 50;
        interval -= 0.05f;
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

    @Override
    public void addEventListeners() {
        eventManager.on("player:levelup:sound", args -> {
            upgrade();
        });
    }
}
