package com.teixeirarios.mad.lib.domain.entities.orb;

import com.badlogic.gdx.graphics.Color;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.infra.camera.Camera;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Orb implements Body2D {
    UUID id;
    int posX;
    int posY;
    int width;
    int height;
    int value;
    Color color;

    public Orb(int posX, int posY, int value){
        this.id = UUID.randomUUID();
        this.posX = posX;
        this.posY = posY;
        this.width = 10;
        this.height = 20;
        this.value = value;
        this.color = Color.SKY;

        this.animate(true);
    }

    public void animate(boolean goingUp) {
        if (goingUp) {
            this.color = Color.CYAN;
            this.posY += 5;
        } else {
            this.color = Color.SKY;
            this.posY -= 5;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                animate(!goingUp);
            }
        }, 300);
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
}
