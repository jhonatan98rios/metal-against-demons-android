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
    private float scenarioWidth;
    private float scenarioHeight;
    private float scenarioX;
    private float scenarioY;

    public Scenario(Batch batch) {
        this.scenarioWidth = Gdx.graphics.getWidth() * 4;
        this.scenarioHeight = Gdx.graphics.getHeight() * 4;
        this.scenarioX = Gdx.graphics.getWidth() * -2;
        this.scenarioY = Gdx.graphics.getHeight() * -2;

        this.batch = batch;
        this.backgroundTexture = new Texture("pattern-small.png");
        this.tiledBackground = new TiledDrawable(new TextureRegion(backgroundTexture));
    }

    public void drawBackground() {
        batch.begin();
        tiledBackground.draw(batch, scenarioX, scenarioY, scenarioWidth, scenarioHeight);
        batch.end();
    }
}

