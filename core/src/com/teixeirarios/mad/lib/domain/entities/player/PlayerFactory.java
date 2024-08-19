package com.teixeirarios.mad.lib.domain.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;
import com.teixeirarios.mad.lib.utils.Constants;


public class PlayerFactory {

    public static Player create (SpriteBatch batch, VirtualJoystick joystick) {
        AbstractCanvasFacade playerCanvas = new CanvasFacade(batch, "spritesheet.png", 4, 0.25F, 50);
        PlayerController playerController = new PlayerController(joystick);
        PlayerStatus playerStatus = new PlayerStatus();

        Player player = Player.getInstance(
                playerCanvas,
                playerController,
                playerStatus,
                (Constants.SCENARIO_WIDTH / 2) - 25,
                (Constants.SCENARIO_HEIGHT / 2) - 50
        );
        return player;
    }
}
