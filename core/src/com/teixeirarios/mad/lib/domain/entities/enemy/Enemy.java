package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

import java.util.UUID;

public class Enemy implements Body2D {
    UUID id;
    private final int velocity, width, height;
    private int posX, posY, selectedFrame;
    public AbstractCanvasFacade enemyCanvas;

    public Enemy(
            int width, int height, int posX, int posY, int velocity,
            AbstractCanvasFacade enemyCanvas
        ) {
        this.id = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.enemyCanvas = enemyCanvas;
        this.selectedFrame = 0;
    }

    public void update(float playerPosX) {
        enemyCanvas.animate();
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
