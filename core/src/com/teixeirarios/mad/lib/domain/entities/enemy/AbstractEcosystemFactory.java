package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

@FunctionalInterface
public interface AbstractEcosystemFactory {
    Enemy create(SpriteBatch batch, int posX, int posY);
}
