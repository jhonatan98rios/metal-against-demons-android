package com.teixeirarios.mad.lib.domain.strategies;

import com.teixeirarios.mad.lib.domain.abstracts.Body2D;

public class CollisionStrategy {
    public static boolean isColliding(Body2D body1, Body2D body2, int marginTolerance) {
        float body1Right = body1.getPosX() + body1.getWidth() - marginTolerance;
        float body1Bottom = body1.getPosY() + body1.getHeight() - marginTolerance;
        float body2Right = body2.getPosX() + body2.getWidth() - marginTolerance;
        float body2Bottom = body2.getPosY() + body2.getHeight() - marginTolerance;

        return body1.getPosX() < body2Right &&
                body1Right > body2.getPosX() &&
                body1.getPosY() < body2Bottom &&
                body1Bottom > body2.getPosY();
    }
}

