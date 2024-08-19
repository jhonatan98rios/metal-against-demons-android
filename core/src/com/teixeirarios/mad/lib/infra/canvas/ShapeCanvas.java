package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeCanvas {
    public static ShapeRenderer shapeRenderer;

    public static void drawShape(Color color, float dx, float dy, float dw, float dh) {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (shapeRenderer.isDrawing()) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(dx, dy, dw, dh);
            shapeRenderer.end();
        }
    }

    public static ShapeRenderer getShapeRenderer() {
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }

        return shapeRenderer;
    }

    public static void dispose() {
        try {
            shapeRenderer.dispose();
            shapeRenderer = null;
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
