package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;


public class MovimentationStrategy {
    private final int safetyMargin;

    public MovimentationStrategy(int safetyMargin){
        this.safetyMargin = safetyMargin;
    }

    public void updateEnemiesMovement(ArrayList<Enemy> enemies, Vector2 playerPosition) {
        int enemyCount = enemies.size();

        // Precompute directions and next positions to avoid recalculations
        Vector2[] directionsToPlayer = new Vector2[enemyCount];
        Vector2[] nextPositions = new Vector2[enemyCount];

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);

            Vector2 directionToPlayer = getDirection(playerPosition, enemy);
            directionsToPlayer[i] = directionToPlayer;

            Vector2 nextPosition = getNextPosition(directionToPlayer, enemy);
            nextPositions[i] = nextPosition;
        }

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            Vector2 nextPosition = adjustNextPosition(enemies, nextPositions[i], enemy);

            enemy.setPosX(Math.round(nextPosition.x));
            enemy.setPosY(Math.round(nextPosition.y));
        }
    }

    Vector2 getDirection(Vector2 playerPosition, Enemy enemy) {
        // Calcula a direção para a qual o inimigo deve se mover em direção ao jogador
        return new Vector2(
                playerPosition.x - (enemy.getPosX() + ((float) enemy.getWidth() / 4)) + 50,
                playerPosition.y - (enemy.getPosY() - ((float) enemy.getHeight() / 4)) + 50
        ).nor();
    }

    Vector2 getNextPosition(Vector2 directionToPlayer, Enemy enemyPosition) {
        // Calcula a próxima posição do inimigo com base na direção e na velocidade
        return new Vector2(
                enemyPosition.getPosX() + directionToPlayer.x * enemyPosition.getVelocity(),
                enemyPosition.getPosY() + directionToPlayer.y * enemyPosition.getVelocity()
        );
    }

    public Vector2 adjustNextPosition(ArrayList<Enemy> enemies, Vector2 nextPosition, Enemy enemy) {
        boolean collisionX = false;
        boolean collisionY = false;

        int enemyCount = enemies.size();
        float enemyPosX = enemy.getPosX();
        float enemyPosY = enemy.getPosY();

        for (int i = 0; i < enemyCount; i++) {
            Enemy otherEnemy = enemies.get(i);
            if (otherEnemy != enemy) {
                float otherEnemyPosX = otherEnemy.getPosX();
                float otherEnemyPosY = otherEnemy.getPosY();

                if (Math.abs(nextPosition.x - otherEnemyPosX) < safetyMargin && Math.abs(enemyPosY - otherEnemyPosY) < safetyMargin) {
                    collisionX = true;
                }
                if (Math.abs(nextPosition.y - otherEnemyPosY) < safetyMargin && Math.abs(enemyPosX - otherEnemyPosX) < safetyMargin) {
                    collisionY = true;
                }
                if (collisionX && collisionY) {
                    break;
                }
            }
        }

        float adjustedX = collisionX ? enemyPosX : nextPosition.x;
        float adjustedY = collisionY ? enemyPosY : nextPosition.y;

        return new Vector2(adjustedX, adjustedY);
    }
}
