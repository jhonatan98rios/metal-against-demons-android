package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

public class Enemy {
    private final int velocity, width, height;
    private int posX, posY;

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
    }

    public void update(int posX, int posY, float playerPosX) {
        enemyCanvas.animate();
        enemyCanvas.drawImage(0, getSprite(playerPosX), this.width, this.height, posX, posY, this.width, this.height);
    }

    public float getSprite(float playerPosX) {
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
