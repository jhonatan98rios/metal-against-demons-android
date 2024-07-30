package com.teixeirarios.mad.lib.domain.entities.orb;

import com.badlogic.gdx.graphics.Color;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.infra.camera.Camera;

import java.util.UUID;

public class Orb implements Body2D {
    private final UUID id;
    private final int posX;
    private int posY;
    private final int width;
    private final int height;
    private final int value;
    private Color color, primaryColor, secondaryColor;
    private boolean goingUp;
    private float accumulatedTime;
    private static final float ANIMATION_INTERVAL = 0.3f; // 300ms

    public Orb(int posX, int posY, int value){
        this.id = UUID.randomUUID();
        this.posX = posX;
        this.posY = posY;
        this.width = 10;
        this.height = 20;
        this.value = value;
        this.accumulatedTime = 0;

        this.primaryColor = value > 50 ? Color.RED
                : value > 25 ? Color.PURPLE
                : Color.SKY;

        this.secondaryColor = value > 50 ? Color.FIREBRICK
                : value > 25 ? Color.VIOLET
                : Color.CYAN;

        this.color = this.primaryColor;
    }

    public void update(float deltaTime) {
        accumulatedTime += deltaTime;

        if (accumulatedTime >= ANIMATION_INTERVAL) {
            animate();
            accumulatedTime = 0;
        }
    }

    private void animate() {
        if (goingUp) {
            this.color = this.primaryColor;
            this.posY += 5;
        } else {
            this.color = this.secondaryColor;
            this.posY -= 5;
        }
        goingUp = !goingUp;
    }
    public UUID getId(){
        return this.id;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void renderHealthBar(Camera camera) {
        throw new UnsupportedOperationException();
    }

    public Color getColor() {
        return color;
    }
}
