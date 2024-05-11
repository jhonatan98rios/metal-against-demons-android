package com.teixeirarios.mad.lib.domain.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;


public class PlayerFactory {

    public static Player create (SpriteBatch batch, VirtualJoystick joystick) {
        AbstractCanvasFacade playerCanvas = new CanvasFacade(batch, "spritesheet.png", 4, 0.25F, 50);
        PlayerController playerController = new PlayerController(joystick);

        Player player = new Player(
                playerCanvas,
                playerController,
                ((float) Gdx.graphics.getWidth() / 4) - 25,
                ((float) Gdx.graphics.getHeight() / 4) - 50
        );
        return player;
    }
}
