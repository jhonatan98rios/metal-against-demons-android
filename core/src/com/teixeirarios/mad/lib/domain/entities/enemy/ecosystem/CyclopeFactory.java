package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class CyclopeFactory {

    public static CanvasFacade enemyCanvas;

    public static Enemy create(SpriteBatch batch, int posX, int posY) {

        if (enemyCanvas == null) {
            enemyCanvas = new CanvasFacade(batch, "enemies/cyclope.png", 4, 0.25f, 88);
        }

        StageManager stageManager =  StageManager.getInstance();

        Enemy enemy = new Enemy(
                "cyclope",
                177,
                170,
                posX,
                posY,
                2,
                (int) stageManager.getCurrentStage().getBaseHealth() * 200,
                stageManager.getCurrentStage().getBaseDamage() * 2,
                enemyCanvas
        );
        return enemy;
    }
}
