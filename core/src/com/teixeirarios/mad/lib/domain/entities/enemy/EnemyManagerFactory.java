package com.teixeirarios.mad.lib.domain.entities.enemy;


import com.badlogic.gdx.math.Vector2;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.infra.camera.Camera;

public class EnemyManagerFactory {
    public static EnemyManager create (Player player, Camera camera) {
        MovimentationStrategy movimentationStrategy = new MovimentationStrategy(10, 48);
        return new EnemyManager(player, camera, 60, 10, movimentationStrategy);
    }
}
