package com.teixeirarios.mad.lib.domain.entities.scenario;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.teixeirarios.mad.lib.utils.Constants;

public class Scenario {
    private Texture backgroundTexture;
    private TiledDrawable tiledBackground;
    private Batch batch;
    private float scenarioWidth;
    private float scenarioHeight;
    private float scenarioX;
    private float scenarioY;

    public Scenario(Batch batch) {
        this.scenarioWidth = Constants.SCENARIO_WIDTH;
        this.scenarioHeight = Constants.SCENARIO_HEIGHT;
        this.scenarioX = 0;
        this.scenarioY = 0;

        this.batch = batch;
        this.backgroundTexture = new Texture("pattern-small.png");
        this.tiledBackground = new TiledDrawable(new TextureRegion(backgroundTexture));
    }

    public void drawBackground() {
        tiledBackground.draw(batch, scenarioX, scenarioY, scenarioWidth, scenarioHeight);
    }

    public void dispose() {
        backgroundTexture.dispose();
    }
}

