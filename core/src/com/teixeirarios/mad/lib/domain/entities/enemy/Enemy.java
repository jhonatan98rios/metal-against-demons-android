package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    private final float width;
    private final float height;
    private float posX;
    private float posY;
    private  final Rectangle bounds;
    public final float velocity;

    public Enemy(float width, float height, float posX, float posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.bounds = new Rectangle(posX, posY, width, height);
        this.velocity = 0.5f;
    }

    // Getters e setters

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setPosX(float nextPosX) {
        posX = nextPosX;
    }

    public void setPosY(float nextPosY) {
        posY = nextPosY;
    }
}
