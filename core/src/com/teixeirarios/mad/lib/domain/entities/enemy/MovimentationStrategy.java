package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class MovimentationStrategy {
    private final int safetyMargin;

    public MovimentationStrategy(int safetyMargin){
        this.safetyMargin = safetyMargin;
    }

    public void updateEnemiesMovement(Array<Enemy> enemies, Vector2 playerPosition) {
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);

            Vector2 directionToPlayer = getDirection(playerPosition, enemy);
            Vector2 nextPosition = getNextPosition(directionToPlayer, enemy);
            nextPosition = adjustNextPosition(enemies, nextPosition, enemy);

            enemy.setPosX(Math.round(nextPosition.x));
            enemy.setPosY(Math.round(nextPosition.y));

        }
    }

    Vector2 getDirection(Vector2 playerPosition, Enemy enemyPosition) {
        // Calcula a direção para a qual o inimigo deve se mover em direção ao jogador
        return new Vector2(playerPosition.x - enemyPosition.getPosX(), playerPosition.y - enemyPosition.getPosY()).nor();
    }

    Vector2 getNextPosition(Vector2 directionToPlayer, Enemy enemyPosition) {
        // Calcula a próxima posição do inimigo com base na direção e na velocidade
        return new Vector2(
                enemyPosition.getPosX() + directionToPlayer.x * enemyPosition.getVelocity(),
                enemyPosition.getPosY() + directionToPlayer.y * enemyPosition.getVelocity()
        );
    }

    public Vector2 adjustNextPosition(Array<Enemy> enemies, Vector2 nextPosition, Enemy enemy) {
        boolean collisionX = false;
        boolean collisionY = false;

        for (int i = 0; i < enemies.size; i++) {
            Enemy otherEnemy = enemies.get(i);
            if (otherEnemy != enemy) {
                if (Math.abs(nextPosition.x - otherEnemy.getPosX()) < safetyMargin && Math.abs(enemy.getPosY() - otherEnemy.getPosY()) < safetyMargin) {
                    collisionX = true;
                }
                if (Math.abs(nextPosition.y - otherEnemy.getPosY()) < safetyMargin && Math.abs(enemy.getPosX() - otherEnemy.getPosX()) < safetyMargin) {
                    collisionY = true;
                }
                if (collisionX && collisionY) {
                    break;
                }
            }
        }

        float adjustedX = collisionX ? enemy.getPosX() : nextPosition.x;
        float adjustedY = collisionY ? enemy.getPosY() : nextPosition.y;

        return new Vector2(adjustedX, adjustedY);
    }
}
