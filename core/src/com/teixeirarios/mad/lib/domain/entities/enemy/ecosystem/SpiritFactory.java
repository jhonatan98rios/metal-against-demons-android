package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class SpiritFactory {

    public static CanvasFacade enemyCanvas;

    public static Enemy create(SpriteBatch batch, int posX, int posY) {

        if (enemyCanvas == null) {
            enemyCanvas = new CanvasFacade(batch, "enemies/spirit.png", 4, 0.25f, 36);
        }

        StageManager stageManager =  StageManager.getInstance();

        Enemy enemy = new Enemy(
                72,
                150,
                posX,
                posY,
                2,
                (int) stageManager.getCurrentStage().getBaseHealth() * 150,
                stageManager.getCurrentStage().getBaseDamage(),
                enemyCanvas
        );
        return enemy;
    }
}
