package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class NightmareFactory {

    public static CanvasFacade enemyCanvas;

    public static Enemy create(SpriteBatch batch, int posX, int posY) {

        if (enemyCanvas == null) {
            enemyCanvas = new CanvasFacade(batch, "enemies/nightmare.png", 6, 0.25f, 200);
        }

        StageManager stageManager =  StageManager.getInstance();

        Enemy enemy = new Enemy(
                "boss",
                400,
                400,
                posX,
                posY,
                120,
                2,
                (int) stageManager.getCurrentStage().getBaseHealth() * 30000,
                stageManager.getCurrentStage().getBaseDamage() * 3,
                enemyCanvas
        );
        return enemy;
    }
}
