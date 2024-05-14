package com.teixeirarios.mad.lib.domain.entities.enemy;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.infra.camera.Camera;

public class EnemyManagerFactory {
    public static EnemyManager create (SpriteBatch batch, Player player, Camera camera) {
        MovimentationStrategy movimentationStrategy = new MovimentationStrategy(36);
        return new EnemyManager(batch, player, camera, 30, 1000, movimentationStrategy);
    }
}
