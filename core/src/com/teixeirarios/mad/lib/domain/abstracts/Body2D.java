package com.teixeirarios.mad.lib.domain.abstracts;


import com.badlogic.gdx.math.Vector2;

public interface Body2D {
    public float posX = 0;
    public float posY = 0;
    public float width = 0;
    public float height = 0;
    public void update();
}