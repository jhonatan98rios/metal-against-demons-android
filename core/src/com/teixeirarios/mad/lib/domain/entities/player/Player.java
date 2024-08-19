package com.teixeirarios.mad.lib.domain.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.ShapeCanvas;
import com.teixeirarios.mad.lib.utils.Constants;

public class Player implements Body2D {
    public AbstractCanvasFacade playerCanvas;
    public PlayerController playerController;
    public PlayerStatus playerStatus;
    public float posX, posY;
    public final int width, height, velocity;
    char posDirection;
    public static Player instance;

    private Player(AbstractCanvasFacade playerCanvas, PlayerController playerController, PlayerStatus playerStatus, float posX, float posY) {
        this.playerCanvas = playerCanvas;
        this.playerController = playerController;
        this.playerStatus = playerStatus;

        this.posX = posX;
        this.posY = posY;
        this.height = 200;
        this.width = 100;
        this.posDirection = 'L';
        this.velocity = 3;
    }

    public static Player getInstance(AbstractCanvasFacade playerCanvas, PlayerController playerController, PlayerStatus playerStatus, float posX, float posY) {
        if (Player.instance == null) {
            Player.instance = new Player(
                playerCanvas,
                playerController,
                playerStatus,
                posX,
                posY
            );
        }
        return Player.instance;
    }

    public static Player getInstance() {
        if (Player.instance == null) {
            throw new IllegalStateException("Player not initialized");
        } else {
            return Player.instance;
        }
    }

    public void update() {
        move();
        playerCanvas.animate();
    }

    public void render() {
        playerCanvas.drawImage(0, this.getSprite(), 50, 100, this.posX, this.posY, this.width, this.height);
    }

    public void renderHealthBar(Camera camera) {
        float healthPercentage = playerStatus.currentHealth / playerStatus.maxHealth;

        ShapeCanvas.drawShape(
            healthPercentage < 0.5f ? Color.RED : Color.GREEN,
            24,
            Gdx.graphics.getHeight() - 80,
            healthPercentage * 128,
            16
        );

        renderXpBar();
    }

    public void renderXpBar() {
        float xpPercentage = playerStatus.currentXP / playerStatus.nextLevelXp;

        ShapeCanvas.drawShape(
            xpPercentage < 0.5f ? Color.SKY : Color.CYAN,
            24,
            Gdx.graphics.getHeight() - 100,
            xpPercentage * 128,
            16
        );
    }

    public float getSprite() {
        float sprite = playerController.isMoving()
            ? posDirection == 'L' ? 100 : 300
            : posDirection == 'L' ? 0 : 200;

        return sprite;
    }

    public void move() {
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

    public void dispose() {
        playerCanvas.dispose();
    }

    @Override
    public int getPosX() {
        return (int) posX;
    }

    @Override
    public int getPosY() {
        return (int) posY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}