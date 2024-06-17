package com.teixeirarios.mad.lib.domain.entities.skills.forcefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.infra.database.models.UserState;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ForceFieldManager implements AbstractSkillManager {

    private final ArrayList<ForceFieldUnit> activeSkills;
    private final EventManager eventManager;
    private final EnemyManager enemyManager;
    private final SpriteBatch batch;
    private final String category;
    private final int frame_amount;
    private int level, width, height;
    private float damage;
    private String spritesheet;
    private Texture texture;

    public ForceFieldManager(SpriteBatch batch, EnemyManager enemyManager) {
        UserRepository userRepository = UserRepository.getInstance();
        UserState userState = userRepository.getUserState();

        this.category = "Force Field";
        this.level = 1;
        this.width = 240;
        this.height = 240;
        this.damage = userState.strength / 3;

        this.spritesheet = "skills/force_field_1.png";
        this.texture = new Texture(this.spritesheet);

        this.activeSkills = new ArrayList<>();
        this.batch = batch;
        this.frame_amount = 4;

        this.enemyManager = enemyManager;
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    @Override
    public void spawn(Player player) {

        ForceFieldUnit unit = new ForceFieldUnit(
            player.getPosX() - width,
            player.getPosY() + height,
            width,
            height,
            texture,
            frame_amount,
            batch
        );

        activeSkills.add(unit);
    }

    @Override
    public void update() {
        if (this.activeSkills.isEmpty()) return;

        for (int i = 0; i < this.activeSkills.size(); i++) {
            ForceFieldUnit unit = this.activeSkills.get(i);

            unit.move();
            unit.checkCollision(
                enemyManager.getEnemies(),
                this::collision
            );
        }
    }

    @Override
    public void upgrade() {
        activeSkills.clear();

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

        spawn(Player.getInstance());
    }

    @Override
    public String getCategory() {
        return category;
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
