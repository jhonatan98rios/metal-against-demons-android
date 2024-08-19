package com.teixeirarios.mad.lib.infra.canvas;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.utils.Intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RenderStack {
    public static void render(ArrayList<Body2D> bodies2D, Camera camera) {

        if (bodies2D == null || bodies2D.isEmpty()) {
            return;
        }

        sortByPosY(bodies2D);

        for (Body2D body2D : bodies2D) {
            if (Intersection.check(body2D, (Body2D) camera)) {
                body2D.render();
            }
        }
    }

    public static void renderHealthBar(ArrayList<Body2D> bodies2D, Camera camera) {

        if (bodies2D == null || bodies2D.isEmpty()) {
            return;
        }

        sortByPosY(bodies2D);

        for (Body2D body2D : bodies2D) {
            body2D.renderHealthBar(camera);
        }
    }

    public static void sortByPosY(ArrayList<Body2D> bodies2D) {
        try {
            Collections.sort(bodies2D, new Comparator<Body2D>() {
                @Override
                public int compare(Body2D b1, Body2D b2) {
                    return Float.compare(b2.getPosY(), b1.getPosY());
                }
            });
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ordenar os objetos");
        }
    }
}
