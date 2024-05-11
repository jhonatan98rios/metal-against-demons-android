package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.drivers.input.AbstractInputObserver;
import com.teixeirarios.mad.lib.infra.input.VirtualJoystick;

public class PlayerController implements AbstractInputObserver {
    private float analogX = 0;
    private float analogY = 0;

    public PlayerController (VirtualJoystick joystick) {
        joystick.addObserver(this);
    }

    public Boolean isMoving() {
        return analogX != 0 || analogY != 0;
    }

    public float getAnalogX() {
        return analogX;
    }

    public float getAnalogY() {
        return analogY;
    }

    @Override
    public void nofityMoveTouch(float x, float y) {
        analogX = x;
        analogY = y;
    }

    @Override
    public void nofityStopMoveTouch() {
        analogX = 0;
        analogY = 0;
    }
}
