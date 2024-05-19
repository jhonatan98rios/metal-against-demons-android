package com.teixeirarios.mad.lib.domain.entities.skills.soundattack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkill;
import com.teixeirarios.mad.lib.domain.entities.skills.soundattack.SoundAttackManagerBase.CollisionCallback;

import java.util.UUID;

public class SoundAttackUnit implements AbstractSkill {
    public UUID id;
    public int width;
    public int height;
    public int initialX;
    public int initialY;
    public int targetX;
    public int targetY;
    public int posX;
    public int posY;
    public int srcX;
    public int srcY;
    public int countAnim;
    public int frame_amount;
    public int speed;
    public int damage;
    public Boolean isAnimated;
    public String spritesheet;
    public int lifeTime;

    //public AbstractCanvasFacade skillCanvas;

    SpriteBatch batch;

    public SoundAttackUnit(
            int initialX, int initialY, int targetX, int targetY, int width, int height, int damage, int speed, Boolean isAnimated,
            String spritesheet, int frame_amount, int lifeTime, SpriteBatch batch) {

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

        //this.skillCanvas = new CanvasFacade(batch, spritesheet, 1, 1, 26);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void move() {
        int deltaX = this.targetX - this.initialX;
        int deltaY = this.targetY - this.initialY;

        float distance =(float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        float directionX = deltaX / distance;
        float directionY = deltaY / distance;
        float velocityX = directionX * this.speed;
        float velocityY = directionY * this.speed;
        float newPosX = this.posX + velocityX;
        float newPosY = this.posY + velocityY;
        this.posX = (int) newPosX;
        this.posY = (int) newPosY;

        // Refatorar essa caralha
        Texture texture = new Texture(this.spritesheet);
        TextureRegion region = new TextureRegion(texture, srcX, srcY, width, height);
        batch.draw(region, posX, posY, width*2, height*2);


        //skillCanvas.drawImage(this.posX, this.posY, this.width, this.height, this.srcX, this.srcY, this.width, this.height);

        if (this.isAnimated) {
            this.spriteAnimation();
        }
    }

    public void updateLifeTime() {
        this.lifeTime -= 1;
    }

    @Override
    public void checkCollision(Array<Enemy> enemies, CollisionCallback callback) {
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

    public void spriteAnimation() {
        int ANIMATION_SPEED = 3;
        int TIME_TO_RESTART = 60 / ANIMATION_SPEED;
        int SELECTED_FRAME = (int) Math.floor(this.countAnim / (TIME_TO_RESTART / this.frame_amount));

        this.countAnim++;

        if (this.countAnim >= TIME_TO_RESTART) {
            this.countAnim = 0;
        }

        this.srcX = SELECTED_FRAME * this.width;
    }

    @Override
    public void startSpawn(Player player, EnemyManager enemyManager) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void spawn(Player player, EnemyManager enemyManager) {
        throw new RuntimeException("Not implemented");
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
