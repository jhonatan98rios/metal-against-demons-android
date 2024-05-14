package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderStack {
    public static void render(Array<Body2D> bodies2D) {

        bodies2D.sort(new Comparator<Body2D>() {
            @Override
            public int compare(Body2D b1, Body2D b2) {
                return Float.compare(b2.getPosY(), b1.getPosY());
            }
        });

        for (Body2D body2D: bodies2D) {
            body2D.render();
        }
    }
}
