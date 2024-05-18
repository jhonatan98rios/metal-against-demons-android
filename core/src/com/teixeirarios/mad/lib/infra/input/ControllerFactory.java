package com.teixeirarios.mad.lib.infra.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ControllerFactory {

    public static VirtualJoystick create(Stage stage) {
        VirtualJoystick joystick = new VirtualJoystick(stage);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        return joystick;
    }
}
