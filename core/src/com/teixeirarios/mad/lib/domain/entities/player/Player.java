package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.utils.Constants;

public class Player implements Body2D {
    public AbstractCanvasFacade playerCanvas;
    public PlayerController playerController;
    public float posX, posY;
    public final int width, height, velocity;
    char posDirection;

    public Player(AbstractCanvasFacade playerCanvas, PlayerController playerController, float posX, float posY) {
        this.playerCanvas = playerCanvas;
        this.playerController = playerController;

        this.posX = posX;
        this.posY = posY;
        this.height = 100;
        this.width = 50;
        this.posDirection = 'L';
        this.velocity = 2;
    }

    public void update() {
        moveTouch();
        playerCanvas.animate();
        playerCanvas.drawImage(0, getSprite(), this.width, this.height, posX, posY, this.width, this.height);
    }

    public float getSprite() {
        float sprite = playerController.isMoving()
                ? posDirection == 'L' ? 100 : 300
                : posDirection == 'L' ? 0 : 200;

        return sprite;
    }

    public void moveTouch() {
        if(playerController.getAnalogX() > 0) {
            posDirection = 'R';
        } else if(playerController.getAnalogX() < 0) {
            posDirection = 'L';
        }

        if (playerController.getAnalogX() == 0 && playerController.getAnalogY() == 0) return;

        float nextPosX = posX + (velocity * playerController.getAnalogX());
        float nextPosY = posY + (velocity * playerController.getAnalogY());

        if (nextPosX < 0 || nextPosX + width > Constants.SCENARIO_WIDTH) {
            nextPosX = posX;
        }

        if (nextPosY < 0 || nextPosY + height > Constants.SCENARIO_HEIGHT) {
            nextPosY = posY;
        }

        posY = nextPosY;
        posX = nextPosX;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}