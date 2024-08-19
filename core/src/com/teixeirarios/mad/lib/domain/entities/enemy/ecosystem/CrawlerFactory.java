package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class CrawlerFactory {

    public static CanvasFacade enemyCanvas;

    public static Enemy create(SpriteBatch batch, int posX, int posY) {

        if (enemyCanvas == null) {
            enemyCanvas = new CanvasFacade(batch, "enemies/crawler.png", 4, 0.25f, 65);
        }

        StageManager stageManager =  StageManager.getInstance();

        Enemy enemy = new Enemy(
                "crawler",
                130,
                75,
                posX,
                posY,
                25,
                1,
                (int) stageManager.getCurrentStage().getBaseHealth() * 300,
                stageManager.getCurrentStage().getBaseDamage() * 1,
                enemyCanvas
        );
        return enemy;
    }
}
