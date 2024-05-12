package com.teixeirarios.mad.lib.infra.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.drivers.camera.AbstractCamera;

public class Camera implements AbstractCamera {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Player player;

    public Camera(SpriteBatch batch, Player player) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = batch;
        this.player = player;
    }

    @Override
    public void update() {
        if (player.posX < camera.position.x - camera.viewportWidth / 4) {
            camera.position.x = player.posX + camera.viewportWidth / 4;
        } else if (player.posX > camera.position.x + camera.viewportWidth / 4) {
            camera.position.x = player.posX - camera.viewportWidth / 4;
        }

        if (player.posY < camera.position.y - camera.viewportHeight / 4) {
            camera.position.y = player.posY + camera.viewportHeight / 4;
        } else if (player.posY > camera.position.y + camera.viewportHeight / 8) {
            camera.position.y = player.posY - camera.viewportHeight / 8;
        }

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
