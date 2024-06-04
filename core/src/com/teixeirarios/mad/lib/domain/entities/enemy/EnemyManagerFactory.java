package com.teixeirarios.mad.lib.domain.entities.enemy;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class EnemyManagerFactory {
    public static EnemyManager create (SpriteBatch batch, Player player, Camera camera) {

        MovimentationStrategy movimentationStrategy = new MovimentationStrategy(40);
        SpawnStrategy spawnStrategy = new SpawnStrategy();
        EventManager eventManager = EventManager.getInstance();

        StageModel currentStage = StageManager.getInstance().getCurrentStage();

        if (currentStage == null) {
            throw new RuntimeException("Current stage is null");
        }

        return new EnemyManager(
            batch, player, camera, currentStage.getSpawnInterval(), currentStage.getMaxEnemies(),
            movimentationStrategy, spawnStrategy, eventManager
        );
    }
}
