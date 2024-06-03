package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.Color;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.camera.Camera;

import java.util.UUID;

public class Enemy implements Body2D {
    UUID id;
    private final int velocity, width, height;
    private int posX, posY, selectedFrame;
    public AbstractCanvasFacade enemyCanvas;
    public EnemyStatus status;

    public Enemy(
            int width, int height, int posX, int posY, int velocity,
            int maxHealth, int damage, AbstractCanvasFacade enemyCanvas
        ) {
        this.id = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.enemyCanvas = enemyCanvas;
        this.selectedFrame = 0;
        this.status = new EnemyStatus(maxHealth, damage);
    }

    public void update(float playerPosX) {
        //enemyCanvas.animate();
        this.selectedFrame = getSprite(playerPosX);
    }

    public void render() {
        enemyCanvas.drawImage(
                0,
                selectedFrame,
                (float) this.width /2,
                (float) this.height /2,
                this.posX,
                this.posY,
                this.width,
                this.height
        );
    }

    public void renderHealthBar(Camera camera) {
        if (status.currentHealth <= 0) {return;}

        float healthPercentage = (float) status.currentHealth / status.maxHealth;

        enemyCanvas.drawShape(
            healthPercentage < 0.5f ? Color.RED : Color.GREEN,
            posX + 10 - camera.getPosX(),
            this.posY + this.height + 10 - camera.getPosY(),
            healthPercentage * 64,
            5
        );
    }

    public int getSprite(float playerPosX) {
        return playerPosX > posX ? 0 : this.height /2;
    }

    // Getters e setters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setPosX(int nextPosX) {
        posX = nextPosX;
    }

    public void setPosY(int nextPosY) {
        posY = nextPosY;
    }

}
