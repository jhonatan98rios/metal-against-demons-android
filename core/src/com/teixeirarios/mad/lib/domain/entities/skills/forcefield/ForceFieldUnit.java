package com.teixeirarios.mad.lib.domain.entities.skills.forcefield;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;

import java.util.ArrayList;
import java.util.UUID;

public class ForceFieldUnit implements AbstractSkill {

    private final UUID id;
    private final int width, height, srcY, frame_amount;
    private int posX, posY, srcX, countAnim;
    private final Texture texture;
    private final SpriteBatch batch;

    public ForceFieldUnit(
            int initialX, int initialY, int width, int height,
            Texture texture, int frame_amount, SpriteBatch batch
    ) {
        this.id = UUID.randomUUID();
        this.posX = initialX;
        this.posY = initialY;
        this.width = width;
        this.height = height;

        this.srcX = 0;
        this.srcY = 0;
        this.countAnim = 0;
        this.frame_amount = frame_amount;

        this.texture = texture;
        this.batch = batch;
    }

    @Override
    public void move() {
        Player player = Player.getInstance();
        this.posX = (int) (player.posX + (player.width / 2) - (this.width / 2));
        this.posY = (int) (player.posY + (player.height / 2) - (this.height / 2));

        TextureRegion region = new TextureRegion(texture, srcX, srcY, width, height);
        if (batch.isDrawing()) {
            batch.draw(region, posX, posY, width, height);
        }

        this.spriteAnimation();
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
    public void spawn(Player player, EnemyManager enemyManager) {
        throw new RuntimeException("Not implemented");
    }

    public UUID getId() {
        return id;
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
}
