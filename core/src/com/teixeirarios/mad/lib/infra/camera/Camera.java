package com.teixeirarios.mad.lib.infra.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.drivers.camera.AbstractCamera;
import com.teixeirarios.mad.lib.utils.Constants;

public class Camera implements AbstractCamera {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;

    private static Camera instance;

    private Camera(SpriteBatch batch, Player player) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = batch;
        this.player = player;
    }

    public static Camera getInstance(SpriteBatch batch, Player player) {
        if (instance == null) {
            instance = new Camera(batch, player);
        }
        return instance;
    }

    public static Camera getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Camera not initialized");
        }
        return instance;
    }

    @Override
    public void update() {

        float nextPositionX = camera.position.x;
        float nextPositionY = camera.position.y;

        if (player.posX < camera.position.x - camera.viewportWidth / 4) {
            nextPositionX = player.posX + camera.viewportWidth / 4;
        } else if (player.posX > camera.position.x + camera.viewportWidth / 4) {
            nextPositionX = player.posX - camera.viewportWidth / 4;
        }

        if (player.posY < camera.position.y - camera.viewportHeight / 4) {
            nextPositionY = player.posY + camera.viewportHeight / 4;
        } else if (player.posY > camera.position.y + camera.viewportHeight / 8) {
            nextPositionY = player.posY - camera.viewportHeight / 8;
        }

        if (nextPositionX - camera.viewportWidth / 2 < 0 || nextPositionX + camera.viewportWidth / 2 > Constants.SCENARIO_WIDTH) {
            nextPositionX = camera.position.x;
        }

        if (nextPositionY - camera.viewportHeight / 2 < 0 || nextPositionY + camera.viewportHeight / 2 > Constants.SCENARIO_HEIGHT) {
            nextPositionY = camera.position.y;
        }

        camera.position.x = nextPositionX;
        camera.position.y = nextPositionY;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public float getPosX() {
        return camera.position.x - camera.viewportWidth / 2;
    }

    public float getPosY() {
        return camera.position.y - camera.viewportHeight / 2;
    }
}
