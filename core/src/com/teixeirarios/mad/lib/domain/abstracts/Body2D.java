package com.teixeirarios.mad.lib.domain.abstracts;


import com.teixeirarios.mad.lib.infra.camera.Camera;

public interface
Body2D {
    public float posX = 0;
    public float posY = 0;
    public float width = 0;
    public float height = 0;
    public void render();
    public void renderHealthBar(Camera camera);
    public int getPosY();
    public int getPosX();
    public int getWidth();
    public int getHeight();

}