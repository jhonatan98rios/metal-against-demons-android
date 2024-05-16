package com.teixeirarios.mad.lib.domain.entities.enemy;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.infra.camera.Camera;

public class EnemyManagerFactory {
    public static EnemyManager create (SpriteBatch batch, Player player, Camera camera) {
        MovimentationStrategy movimentationStrategy = new MovimentationStrategy(72);
        SpawnStrategy spawnStrategy = new SpawnStrategy();
        return new EnemyManager(
                batch, player, camera, 30, 500,
                movimentationStrategy, spawnStrategy
        );
    }
}
