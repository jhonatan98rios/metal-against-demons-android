package com.teixeirarios.mad.lib.domain.entities.orb;

import com.badlogic.gdx.graphics.Color;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class Orb {
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
        this.width = 5;
        this.height = 10;
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
}
