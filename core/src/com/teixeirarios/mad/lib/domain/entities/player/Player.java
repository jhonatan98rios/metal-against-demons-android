package com.teixeirarios.mad.lib.domain.entities.player;

import com.badlogic.gdx.Gdx;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

public class Player implements Body2D {
    public AbstractCanvasFacade playerCanvas;
    public PlayerController playerController;

    public float posX;
    public float posY;
    public float width;
    public float height;
    public final float velocity;
    char posDirection;

    public Player(AbstractCanvasFacade playerCanvas, PlayerController playerController, float posX, float posY) {
        this.playerCanvas = playerCanvas;
        this.playerController = playerController;

        this.posX = posX;
        this.posY = posY;
        this.height = 100;
        this.width = 50;
        this.posDirection = 'L';
        this.velocity = 2f;
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

        if (playerController.getAnalogX() != 0 || playerController.getAnalogY() != 0) {
            posY += velocity * playerController.getAnalogY();
            posX += velocity * playerController.getAnalogX();
        }
    }
}