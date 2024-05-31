package com.teixeirarios.mad.lib.domain.entities.skills.vampires;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;

import java.util.ArrayList;
import java.util.UUID;

public class VampiresUnit implements AbstractSkill {

    private final UUID id;
    private final int width, height, frame_amount, srcY;
    private int posX, posY, srcX, countAnim, lifeTime;
    private float speed, angle, radius;
    private final Texture texture;
    private final SpriteBatch batch;

    public VampiresUnit(
            int initialX, int initialY, int width, int height, float speed,
            Texture texture, int frame_amount, int lifeTime, SpriteBatch batch
    ) {
        this.id = UUID.randomUUID();
        this.posX = initialX;
        this.posY = initialY;
        this.angle = 0;
        this.radius = 20;

        this.width = width;
        this.height = height;
        this.speed = speed;

        this.srcX = 0;
        this.srcY = 0;
        this.countAnim = 0;
        this.frame_amount = frame_amount;

        this.lifeTime = lifeTime;
        this.texture = texture;
        this.batch = batch;
    }

    @Override
    public void move() {
        Player player = Player.getInstance();

        double offsetX = Math.cos(this.angle) * this.radius;
        double offsetY = Math.sin(this.angle) * this.radius;

        this.posX = (int) (player.getPosX() + (player.getWidth() / 4) + offsetX);
        this.posY = (int) (player.getPosY() + (player.getHeight() / 2) + offsetY);

        this.angle += this.speed;
        this.speed -= 0.00005f;
        this.radius += 0.7f;

        // Refatorar essa caralha
        TextureRegion region = new TextureRegion(texture, srcX, srcY, width, height);
        if (batch.isDrawing()) {
            batch.draw(region, posX, posY, width, height);
        }

        this.spriteAnimation();
    }

    public void spriteAnimation() {
        int ANIMATION_SPEED = 3;
        int TIME_TO_RESTART = 60 / ANIMATION_SPEED;
        int SELECTED_FRAME = (int) Math.floor((float) this.countAnim / ((float) TIME_TO_RESTART / this.frame_amount));
        this.countAnim++;

        if (this.countAnim >= TIME_TO_RESTART) {
            this.countAnim = 0;
        }

        this.srcX = SELECTED_FRAME * this.width;
    }

    public void updateLifeTime() {
        this.lifeTime -= 1;
    }

    @Override
    public void checkCollision(ArrayList<Enemy> enemies, CollisionCallback callback) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if ((this.posX <= enemy.getPosX() + enemy.getWidth()) &&
                (this.posX + this.width >= enemy.getPosX()) &&
                (this.posY <= enemy.getPosY() + enemy.getHeight()) &&
                (this.posY + this.height >= enemy.getPosY())) {
                callback.collision(this.getId(), enemy);
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

    @Override
    public UUID getId() {
        return id;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
