package com.teixeirarios.mad.lib.infra.canvas;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.infra.camera.Camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RenderStack {
    public static void render(ArrayList<Body2D> bodies2D) {

        if (bodies2D == null || bodies2D.size() == 0) {
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

        for (int i = 0; i < bodies2D.size(); i++) {
            Body2D body2D = bodies2D.get(i);
            body2D.render();
        }
    }

    public static void renderHealthBar(ArrayList<Body2D> bodies2D, Camera camera) {

        if (bodies2D == null || bodies2D.isEmpty()) {
            return;
        }

        try {
            Collections.sort(bodies2D, new Comparator<Body2D>() {
                @Override
                public int compare(Body2D b1, Body2D b2) {
                    return Float.compare(b2.getPosY(), b1.getPosY());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao ordenar os objetos");
        }

        for (int i = 0; i < bodies2D.size(); i++) {
            Body2D body2D = bodies2D.get(i);
            body2D.renderHealthBar(camera);
        }
    }
}
