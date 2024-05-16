package com.teixeirarios.mad.lib.domain.entities.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.infra.camera.Camera;


public class GameStatusRender {
    SpriteBatch batch;
    Texture pauseButton;

    Camera camera;

    public GameStatusRender(SpriteBatch batch, Camera camera) {
        this.batch = batch;
        this.pauseButton = new Texture("pause.png");
        this.camera = camera;
    }

    public void renderPauseButton() {
        batch.draw(
                pauseButton,
                32 + camera.getPosX(),
                camera.getPosY() + Gdx.graphics.getHeight() - 80,
                48,
                48
        );
    }
}
