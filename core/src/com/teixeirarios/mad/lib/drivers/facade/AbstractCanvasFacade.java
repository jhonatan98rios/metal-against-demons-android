package com.teixeirarios.mad.lib.drivers.facade;

public interface AbstractCanvasFacade {
    public void drawImage(float sx, float sy, float sw, float sh, float dx, float dy, float dw, float dh);
    public void animate();
    public void dispose();
}
