package com.teixeirarios.mad.lib.domain.entities.scenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class Scenario {
    private Texture backgroundTexture;
    private TiledDrawable tiledBackground;
    private Batch batch;

    public Scenario(Batch batch) {
        this.backgroundTexture = new Texture("pattern.png");
        this.tiledBackground = new TiledDrawable(new TextureRegion(backgroundTexture));
        this.batch = batch;
    }

    public void drawBackground() {
        tiledBackground.draw(
                batch,
                -(Gdx.graphics.getWidth() * 50),
                -(Gdx.graphics.getHeight() * 50),
                Gdx.graphics.getWidth() * 1000,
                Gdx.graphics.getHeight() * 1000
        );
    }
}

