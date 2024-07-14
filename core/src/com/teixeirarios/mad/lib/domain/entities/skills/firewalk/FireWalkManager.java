package com.teixeirarios.mad.lib.domain.entities.skills.firewalk;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class FireWalkManager implements AbstractSkillManager {

    private final String category;
    private final ArrayList<FireWalkUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final Player player;
    private final SpriteBatch batch;
    private final float speed;
    private int level, width, height, lifeTime, frame_amount;
    private float damage, accumulatedTime, interval;
    private String spritesheet;
    private Texture texture;

    public FireWalkManager(SpriteBatch batch, EnemyManager enemyManager) {
        UserRepository userRepository = UserRepository.getInstance();
        UserState userState = userRepository.getUserState();

        this.category = "Fire Walk";
        this.level = 0;
        this.width = 80;
        this.height = 80;
        this.speed = 0.05f;
        this.lifeTime = 750;
        this.accumulatedTime = 0;

        this.damage = userState.strength;
        this.interval = 2f / userState.dexterity;

        this.player = Player.getInstance();

        this.spritesheet = "skills/fire_walk_1.png";
        this.texture = new Texture(this.spritesheet);
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
        if (this.level == 0) return;

        ArrayList<Enemy> enemies = enemyManager.getEnemies();
        if (player == null || enemies.isEmpty()) return;

        FireWalkUnit unit = new FireWalkUnit(
                player.getPosX() + (player.getWidth() / 2) - (width/2),
                player.getPosY(),
                width,
                height,
                speed,
                texture,
                frame_amount,
                lifeTime,
                batch
        );

        this.addActiveSkills(unit);
    }

    @Override
    public void update() {
        intervaledSpawn(Gdx.graphics.getDeltaTime());

        if (this.activeSkills.isEmpty()) return;

        for (int i = 0; i < this.activeSkills.size(); i++) {
            FireWalkUnit unit = this.activeSkills.get(i);
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
        Iterator<FireWalkUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            FireWalkUnit skill = iterator.next();
            skill.updateLifeTime();
            if (skill.getLifeTime() <= 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public void upgrade() {
        activeSkills.clear();
        level += 1;

        if (this.level == 1) return;

        damage += 0.2f;
        lifeTime += 250;

        if (interval > 0.5f) {
            interval -= 0.05f;
        }

        if (level <= 5) {
            this.spritesheet = "skills/fire_walk_" + level + ".png";
            this.texture = new Texture(this.spritesheet);
        }

        if (level == 3) {
            frame_amount = 6;
        }

        HashMap<Integer, int[]> dimensions = new HashMap<>();
        dimensions.put(2, new int[]{100, 100});
        dimensions.put(3, new int[]{120, 120});
        dimensions.put(4, new int[]{160, 160});
        dimensions.put(5, new int[]{200, 200});

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

    public void addActiveSkills(FireWalkUnit activeSkill) {
        this.activeSkills.add(activeSkill);
    }

    @Override
    public void addEventListeners() {
        eventManager.on("player:levelup:firewalk", args -> {
            upgrade();
        });
    }
}
