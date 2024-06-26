package com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;


public class SpiritFactory {

    public static Enemy create(SpriteBatch batch, int posX, int posY) {
        CanvasFacade enemyCanvas = new CanvasFacade(batch, "enemies/spirit.png", 4, 0.25f, 36);
        Enemy enemy = new Enemy(
                72,
                150,
                posX,
                posY,
                2,
                enemyCanvas
        );
        return enemy;
    }
}
