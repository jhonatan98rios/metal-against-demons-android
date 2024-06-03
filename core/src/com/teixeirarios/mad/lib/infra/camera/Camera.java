package com.teixeirarios.mad.lib.infra.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.drivers.camera.AbstractCamera;
import com.teixeirarios.mad.lib.utils.Constants;

public class Camera implements AbstractCamera, Body2D {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;

    public Camera(Player player) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.player = player;
    }

    @Override
    public void update(SpriteBatch batch) {

        float posX = camera.position.x;
        float posY = camera.position.y;

        if (player.posX < camera.position.x - camera.viewportWidth / 4) {
            posX = player.posX + camera.viewportWidth / 4;
        } else if (player.posX > camera.position.x + camera.viewportWidth / 4) {
            posX = player.posX - camera.viewportWidth / 4;
        }

        if (player.posY < camera.position.y - camera.viewportHeight / 4) {
            posY = player.posY + camera.viewportHeight / 4;
        } else if (player.posY > camera.position.y + camera.viewportHeight / 8) {
            posY = player.posY - camera.viewportHeight / 8;
        }

        if (posX - camera.viewportWidth / 2 < 0 || posX + camera.viewportWidth / 2 > Constants.SCENARIO_WIDTH) {
            posX = camera.position.x;
        }

        if (posY - camera.viewportHeight / 2 < 0 || posY + camera.viewportHeight / 2 > Constants.SCENARIO_HEIGHT) {
            posY = camera.position.y;
        }

        camera.position.x = posX;
        camera.position.y = posY;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public int getPosX() {
        return (int) (camera.position.x - camera.viewportWidth / 2);
    }

    @Override
    public void render() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void renderHealthBar(Camera camera) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public int getPosY() {
        return (int) (camera.position.y - camera.viewportHeight / 2);
    }

    public int getWidth() {
        return (int) camera.viewportWidth;
    }

    public int getHeight() {
        return (int) camera.viewportHeight;
    }
}
