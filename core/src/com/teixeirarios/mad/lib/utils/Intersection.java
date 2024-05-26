package com.teixeirarios.mad.lib.utils;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;

public class Intersection {
    public static boolean check(Body2D elementA, Body2D elementB) {

        return elementA.getPosX() <= elementB.getPosX() + elementB.getWidth() &&
                elementA.getPosX() + elementA.getWidth() >= elementB.getPosX() &&
                elementA.getPosY() <= elementB.getPosY() + elementB.getHeight() &&
                elementA.getPosY() + elementA.getHeight() >= elementB.getPosY();
    }
}
