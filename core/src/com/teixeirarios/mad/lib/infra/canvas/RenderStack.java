package com.teixeirarios.mad.lib.infra.canvas;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderStack {
    public static void update(List<Body2D> bodies2D) {

        Collections.sort(bodies2D, new Comparator<Body2D>() {
            @Override
            public int compare(Body2D body1, Body2D body2) {
                return Float.compare(body2.posX, body1.posY);
            }
        });

        for (Body2D body2D: bodies2D) {
            body2D.update();
        }
    }
}
