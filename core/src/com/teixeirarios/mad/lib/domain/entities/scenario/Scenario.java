package com.teixeirarios.mad.lib.domain.entities.scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class Scenario {
    private Texture backgroundTexture;
    private TiledDrawable tiledBackground;
    private Batch batch;
    private float viewportWidth;
    private float viewportHeight;

    public Scenario(Batch batch) {
        this.batch = batch;
        this.viewportWidth = Gdx.graphics.getWidth();
        this.viewportHeight = Gdx.graphics.getHeight();
        this.backgroundTexture = new Texture("pattern.png");
        this.tiledBackground = new TiledDrawable(new TextureRegion(backgroundTexture));
    }

    public void drawBackground() {
        batch.begin();
        tiledBackground.draw(batch, 0, 0, viewportWidth, viewportHeight);
        batch.end();
    }
}

