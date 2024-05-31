package com.teixeirarios.mad.lib.domain.entities.enemy;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Constants;

public class EnemyManagerFactory {
    public static EnemyManager create (SpriteBatch batch, Player player, Camera camera) {

        MovimentationStrategy movimentationStrategy = new MovimentationStrategy(40);
        SpawnStrategy spawnStrategy = new SpawnStrategy();
        EventManager eventManager = EventManager.getInstance();

        return EnemyManager.getInstance(
            batch, player, camera, Constants.ENEMIES_SPAWN_INTERVAL, Constants.MAX_ENEMIES,
            movimentationStrategy, spawnStrategy, eventManager
        );
    }
}
