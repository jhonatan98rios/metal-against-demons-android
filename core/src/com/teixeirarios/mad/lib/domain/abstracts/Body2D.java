package com.teixeirarios.mad.lib.domain.abstracts;


public interface
Body2D {
    public float posX = 0;
    public float posY = 0;
    public float width = 0;
    public float height = 0;
    public void render();
    public int getPosY();
    public int getPosX();
    public int getWidth();
    public int getHeight();

}