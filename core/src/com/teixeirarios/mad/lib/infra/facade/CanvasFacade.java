package com.teixeirarios.mad.lib.infra.facade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;

public class CanvasFacade implements AbstractCanvasFacade {

    public SpriteBatch batch;
    private float posX;
    private Texture sprite;
    private int numFramesX;
    private float frameDuration;
    private float timeSinceLastFrame;
    private int currentFrame;
    private float frameWidth;

    public CanvasFacade(SpriteBatch batch, String spritesheet, int numFramesX, float frameDuration, float frameWidth) {
        this.batch = batch;
        this.sprite = new Texture(spritesheet);
        this.numFramesX = numFramesX;
        this.frameDuration = frameDuration;
        this.frameWidth = frameWidth;
    }

    @Override
    public void drawImage(float sx, float sy, float sw, float sh, float dx, float dy, float dw, float dh) {
        if (batch.isDrawing()) {
            TextureRegion region = new TextureRegion(sprite, (int) posX, (int) sy, (int) sw, (int) sh);
            batch.draw(region, dx, dy, dw, dh);
        }
    }

    public void animate() {
        timeSinceLastFrame += Gdx.graphics.getDeltaTime();
        if (timeSinceLastFrame >= frameDuration) {
            currentFrame = (currentFrame + 1) % numFramesX;
            timeSinceLastFrame = 0;
        }
        posX = currentFrame * frameWidth;
    }

    public void dispose() {
        sprite.dispose();
    }
}

