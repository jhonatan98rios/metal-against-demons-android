package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeCanvas {

    static final ShapeRenderer shapeRenderer = new ShapeRenderer();;

    public static void drawShape(Color color, float dx, float dy, float dw, float dh) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (shapeRenderer.isDrawing()) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(dx, dy, dw, dh);
            shapeRenderer.end();
        }
    }
}
