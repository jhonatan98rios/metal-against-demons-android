package com.teixeirarios.mad.lib.drivers.input;


public interface AbstractInputObserver {
    void nofityMoveTouch(float x, float y);
    void nofityStopMoveTouch();
}