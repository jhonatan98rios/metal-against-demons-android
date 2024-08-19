package com.teixeirarios.mad.lib.domain.entities.skills.lightning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.database.models.UserState;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class LightningManager implements AbstractSkillManager {

    private final String category;
    private final ArrayList<LightningUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final Player player;
    private final SpriteBatch batch;
    private final int frame_amount;
    private final int speed;
    private int level, width, height, range, lifeTime;
    private float accumulatedTime, interval, damage;
    private String spritesheet;
    private Texture texture;

    public LightningManager(SpriteBatch batch, EnemyManager enemyManager) {
        UserRepository userRepository = UserRepository.getInstance();
        UserState userState = userRepository.getUserState();

        this.category = "Lightning";
        this.level = 0;
        this.width = 160;
        this.height = 160;
        this.speed = 3;
        this.range = 700;

        this.damage = userState.strength * 1.2f;
        this.interval = 1f / userState.dexterity;

        this.player = Player.getInstance();

        this.spritesheet = "skills/lightning_1.png";
        this.texture = new Texture(this.spritesheet);

        this.lifeTime = 50;
        this.accumulatedTime = 0;

        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.frame_amount = 8;

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
        if (level == 0 || player == null || enemyManager == null || enemyManager.getEnemies().isEmpty()) return;

        ArrayList<Enemy> nearbyEnemies = getNearbyEnemies(enemyManager);
        if (nearbyEnemies.isEmpty() || nearbyEnemies.size() < 3) return;

        Enemy targetEnemy = nearbyEnemies.get(1);
        LightningUnit lightningUnit = new LightningUnit(
                targetEnemy.getPosX() + (targetEnemy.getWidth() / 2) - (width / 2),
                targetEnemy.getPosY() + (targetEnemy.getHeight() / 2) - (height / 2) ,
                targetEnemy.getPosX() + (targetEnemy.getWidth() / 2) - (width / 2),
                targetEnemy.getPosY() + (targetEnemy.getHeight() / 2) - (height / 2),
                width,
                height,
                speed,
                texture,
                frame_amount,
                lifeTime,
                batch
        );

        this.addActiveSkills(lightningUnit);
    }

    private ArrayList<Enemy> getNearbyEnemies(EnemyManager enemyManager) {
        RangeArea rangeArea = new RangeArea(
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
            LightningUnit unit = this.activeSkills.get(i);
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
        this.eventManager.emit("skill:damage", enemy, this.damage);
    }

    private void checkLifeTime() {
        Iterator<LightningUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            LightningUnit skill = iterator.next();
            skill.updateLifeTime();
            if (skill.getLifeTime() <= 0) {
                iterator.remove();
            }
        }
    }

    private void remove(UUID id) {
        Iterator<LightningUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            LightningUnit skill = iterator.next();
            if (skill.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void upgrade() {
        activeSkills.clear();
        level += 1;

        if (this.level == 1) return;

        damage += 0.5f;
        range += 200;
        lifeTime += 10;

        if (interval > 0.2f) {
            interval -= 0.01f;
        }

        if (level <= 5) {
            this.spritesheet = "skills/lightning_" + level + ".png";
            this.texture = new Texture(this.spritesheet);
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{180, 180});
        dimensions.put(3, new int[]{200, 200});
        dimensions.put(4, new int[]{240, 240});
        dimensions.put(5, new int[]{280, 280});

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

    public void addActiveSkills(LightningUnit activeSkill) {
        this.activeSkills.add(activeSkill);
    }

    @Override
    public void addEventListeners() {
        eventManager.on("player:levelup:lightning", args -> {
            upgrade();
        });
    }
}
