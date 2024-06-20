package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class DragonFactory {

    public static CanvasFacade enemyCanvas;

    public static Enemy create(SpriteBatch batch, int posX, int posY) {

        if (enemyCanvas == null) {
            enemyCanvas = new CanvasFacade(batch, "enemies/dragon.png", 4, 0.25f, 114);
        }

        StageManager stageManager =  StageManager.getInstance();

        Enemy enemy = new Enemy(
                "dragon",
                114 * 2,
                240,
                posX,
                posY,
                1,
                (int) stageManager.getCurrentStage().getBaseHealth() * 500,
                stageManager.getCurrentStage().getBaseDamage() * 3,
                enemyCanvas
        );
        return enemy;
    }
}
