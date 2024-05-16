package com.teixeirarios.mad.lib.domain.entities.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.infra.camera.Camera;

public class GameStatusFactory {

    public static GameStatus create(SpriteBatch batch, Camera camera) {
        GameStatusRender render = new GameStatusRender(batch, camera);
        GameStatus gameStatus = GameStatus.getInstance(render);
        return gameStatus;
    }
}
