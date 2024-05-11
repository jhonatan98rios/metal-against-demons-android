package com.teixeirarios.mad.lib.domain.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;


public class PlayerFactory {

    public static Player create (SpriteBatch batch) {
        AbstractCanvasFacade playerCanvas = new CanvasFacade(batch, "spritesheet.png", 4, 0.25F, 50);
        Player player = new Player(playerCanvas);
        return player;
    }
}
