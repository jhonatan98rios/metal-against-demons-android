package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

public class Enemy implements Body2D {
    private final int velocity, width, height;
    private int posX, posY, selectedFrame;
    public AbstractCanvasFacade enemyCanvas;


    public Enemy(
            int width, int height, int posX, int posY,
            AbstractCanvasFacade enemyCanvas
        ) {

        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.velocity = 1;
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
        return playerPosX > posX ? 0 : 75;
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
