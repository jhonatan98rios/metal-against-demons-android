package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

public class Player implements Body2D {
    public AbstractCanvasFacade playerCanvas;
    public float posX;
    public float posY;
    public float width;
    public float height;
    public final float velocity;
    char posDirection;

    public Player(AbstractCanvasFacade playerCanvas) {
        this.playerCanvas = playerCanvas;
        this.posX = 0;
        this.posY = 0;
        this.height = 100;
        this.width = 50;
        this.posDirection = 'L';
        this.velocity = 3f;
    }

    public void update() {
        playerCanvas.animate();
        playerCanvas.drawImage(0, getSprite(), this.width, this.height, posX, posY, this.width*4, this.height*4);
    }

    public void dispose() {
        playerCanvas.dispose();
    }

    public float getSprite() {
        return 0f;
    }
}