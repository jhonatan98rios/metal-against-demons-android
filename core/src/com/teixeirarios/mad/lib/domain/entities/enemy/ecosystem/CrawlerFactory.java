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
                65 * 2,
                37 * 2,
                posX,
                posY,
                1,
                (int) stageManager.getCurrentStage().getBaseHealth() * 300,
                stageManager.getCurrentStage().getBaseDamage() * 2,
                enemyCanvas
        );
        return enemy;
    }
}
