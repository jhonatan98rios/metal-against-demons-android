package com.teixeirarios.mad.lib.drivers.facade;

import com.badlogic.gdx.graphics.Color;

public interface AbstractCanvasFacade {
    public void drawImage(float sx, float sy, float sw, float sh, float dx, float dy, float dw, float dh);
    public void drawShape(Color color, float dx, float dy, float dw, float dh);
    public void animate();
    public void dispose();
}
