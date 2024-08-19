package com.teixeirarios.mad.lib.utils;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;

public class Calculate {
    public static double calculateDistance(Body2D b1, Body2D b2) {
        try {
            float dx = b1.getPosX() - b2.getPosX();
            float dy = b1.getPosY() - b2.getPosY();
            return Math.sqrt(dx * dx + dy * dy);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao executar calculateDistance with: \n" + b1 + b2);
        }
    }
}


