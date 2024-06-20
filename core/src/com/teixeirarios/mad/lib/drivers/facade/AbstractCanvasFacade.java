package com.teixeirarios.mad.lib.drivers.facade;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface AbstractCanvasFacade {
    public SpriteBatch batch = null;
    public void drawImage(float sx, float sy, float sw, float sh, float dx, float dy, float dw, float dh);
    public void animate();
    public void dispose();
}
