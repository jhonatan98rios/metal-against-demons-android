package com.teixeirarios.mad.lib.domain.entities.skills.forcefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManager;

import java.util.UUID;

public class ForceFieldUnit implements AbstractSkill {

    public UUID id;
    public int width, height, initialX, initialY, targetX, targetY, posX, posY, srcX, srcY, countAnim, frame_amount, damage, speed, lifeTime;
    public Boolean isAnimated;
    public String spritesheet;
    private SpriteBatch batch;

    public ForceFieldUnit(
            int initialX, int initialY, int targetX, int targetY, int width, int height, int damage, int speed, Boolean isAnimated,
            String spritesheet, int frame_amount, int lifeTime, SpriteBatch batch
    ) {
        this.id = UUID.randomUUID();
        this.posX = initialX;
        this.posY = initialY;
        this.initialX = initialX;
        this.initialY = initialY;
        this.targetX = targetX;
        this.targetY = targetY;

        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;

        this.srcX = 0;
        this.srcY = 0;
        this.countAnim = 0;
        this.frame_amount = frame_amount;

        this.isAnimated = isAnimated;
        this.lifeTime = lifeTime;
        this.spritesheet = spritesheet;
        this.batch = batch;
    }

    @Override
    public void move() {
        Player player = Player.getInstance();
        this.posX = (int) (player.posX + (player.width / 2) - (this.width / 2));
        this.posY = (int) (player.posY + (player.height / 2) - (this.height / 2));

        Texture texture = new Texture(this.spritesheet);
        TextureRegion region = new TextureRegion(texture, srcX, srcY, width/2, height/2);
        if (batch.isDrawing()) {
            batch.draw(region, posX, posY, width, height);
        }

        this.spriteAnimation();
    }

    @Override
    public void checkCollision(Array<Enemy> enemies, SoundAttackManager.CollisionCallback callback) {
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);
            if ((this.posX <= enemy.getPosX() + enemy.getWidth()) &&
                    (this.posX + this.width >= enemy.getPosX()) &&
                    (this.posY <= enemy.getPosY() + enemy.getHeight()) &&
                    (this.posY + this.height >= enemy.getPosY())) {
                callback.collision(this, enemy);
            }
        }
    }

    @Override
    public void startSpawn(Player player, EnemyManager enemyManager) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void spawn(Player player, EnemyManager enemyManager) {
        throw new RuntimeException("Not implemented");
    }

    public UUID getId() {
        return id;
    }

    public void spriteAnimation() {
        int ANIMATION_SPEED = 3;
        int TIME_TO_RESTART = 60 / ANIMATION_SPEED;
        int SELECTED_FRAME = (int) Math.floor(this.countAnim / (TIME_TO_RESTART / this.frame_amount));

        this.countAnim++;

        if (this.countAnim >= TIME_TO_RESTART) {
            this.countAnim = 0;
        }

        this.srcX = SELECTED_FRAME * this.width/2;
    }
}
