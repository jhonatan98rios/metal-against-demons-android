package com.teixeirarios.mad.lib.infra.canvas;

import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;

import java.util.Comparator;

public class RenderStack {
    public static void render(Array<Body2D> bodies2D) {

        if (bodies2D == null || bodies2D.size == 0) {
            return;
        }

        try {
            bodies2D.sort(new Comparator<Body2D>() {
                @Override
                public int compare(Body2D b1, Body2D b2) {
                    return Float.compare(b2.getPosY(), b1.getPosY());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao ordenar os objetos");
        }

        for (int i = 0; i < bodies2D.size; i++) {
            Body2D body2D = bodies2D.get(i);
            body2D.render();
        }
    }
}
