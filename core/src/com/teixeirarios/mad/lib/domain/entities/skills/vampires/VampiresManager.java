package com.teixeirarios.mad.lib.domain.entities.skills.vampires;

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

public class VampiresManager implements AbstractSkillManager {

    private final String category;
    private final ArrayList<VampiresUnit> activeSkills;
    private final GameStatus gameStatus;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final Player player;
    private final SpriteBatch batch;
    private final int frame_amount;
    private final float speed;
    private int level, width, height, lifeTime;
    private float damage, accumulatedTime, interval;
    private String spritesheet;
    private Texture texture;

    public VampiresManager(SpriteBatch batch, EnemyManager enemyManager) {
        UserRepository userRepository = UserRepository.getInstance();
        UserState userState = userRepository.getUserState();

        this.category = "Vampires Horde";
        this.level = 0;
        this.width = 48;
        this.height = 48;
        this.speed = 0.05f;
        this.lifeTime = 700;
        this.accumulatedTime = 0;

        this.damage = 3 * userState.strength;
        this.interval = 5f / userState.dexterity;

        this.player = Player.getInstance();

        this.spritesheet = "skills/bat_attack_1.png";
        this.texture = new Texture(this.spritesheet);
        this.activeSkills = new ArrayList<>();
        this.gameStatus = GameStatus.getInstance();
        this.batch = batch;
        this.frame_amount = 2;

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

        VampiresUnit unit = new VampiresUnit(
            player.getPosX() + (player.getWidth() / 2) - width,
            player.getPosY() + (player.getHeight() / 2) - height,
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
            VampiresUnit unit = this.activeSkills.get(i);
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
        Iterator<VampiresUnit> iterator = activeSkills.iterator();
        while (iterator.hasNext()) {
            VampiresUnit skill = iterator.next();
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

        if (this.level > 1) {

            damage += 0.5f;
            lifeTime += 100;
            if (interval > 0.5f) {
                interval -= 0.05f;
            }
        }

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
