package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.Collections;


public class MovimentationStrategy {

    float deltaTime;
    float safetyMargin;

    public MovimentationStrategy(float deltaTime, float safetyMargin){
        this.deltaTime = deltaTime;
        this.safetyMargin = safetyMargin;
    }

    public void updateEnemiesMovement(Array<Enemy> enemies, Vector2 playerPosition, Vector2 cameraPosition) {
        for (int i = 0; i < enemies.size; i++) {
            Enemy enemy = enemies.get(i);

            Vector2 directionToPlayer = getDirection(playerPosition, enemy);
            Vector2 nextPosition = getNextPosition(directionToPlayer, enemy);

            boolean isPositionAvailable = validateNextPositionAvailability(enemies, nextPosition, enemy);

            if (isPositionAvailable) {
                // Atualiza a posição do inimigo com base na posição ajustada
                enemy.setPosX(nextPosition.x);
                enemy.setPosY(nextPosition.y);
            }
//            else {
//                // Se a próxima posição estiver ocupada, tenta mover-se em uma direção alternativa
//                Vector2 alternateDirection = getAlternateDirection(enemies, nextPosition, enemy);
//                if (alternateDirection != null) {
//                    Vector2 alternatePosition = getNextPosition(alternateDirection, enemy);
//                    enemy.setPosX(alternatePosition.x + cameraPosition.x);
//                    enemy.setPosY(alternatePosition.y + cameraPosition.y);
//                }
//            }
        }
    }

    Vector2 getDirection(Vector2 playerPosition, Enemy enemyPosition) {
        // Calcula a direção para a qual o inimigo deve se mover em direção ao jogador
        return new Vector2(playerPosition.x - enemyPosition.getPosX(), playerPosition.y - enemyPosition.getPosY()).nor();
    }

    Vector2 getNextPosition(Vector2 directionToPlayer, Enemy enemyPosition) {
        // Calcula a próxima posição do inimigo com base na direção e na velocidade
        return new Vector2(
                enemyPosition.getPosX() + directionToPlayer.x * enemyPosition.velocity * deltaTime,
                enemyPosition.getPosY() + directionToPlayer.y * enemyPosition.velocity * deltaTime
        );
    }

    boolean validateNextPositionAvailability(Array<Enemy> enemies, Vector2 nextPosition, Enemy enemy) {
        // Verifica se a próxima posição do inimigo está livre de colisões com outros inimigos
        boolean positionOccupied = false;
        for (Enemy otherEnemy : enemies) {
            if (otherEnemy != enemy && Math.abs(nextPosition.x - otherEnemy.getPosX()) < safetyMargin && Math.abs(nextPosition.y - otherEnemy.getPosY()) < safetyMargin) {
                positionOccupied = true;
                break;
            }
        }

        return !positionOccupied;
    }

    private Vector2 getAlternateDirection(Array<Enemy> enemies, Vector2 currentPosition, Enemy enemy) {
        // Se a próxima posição estiver ocupada, tenta mover-se em uma direção alternativa
        // Lista de direções possíveis (cima, baixo, esquerda, direita)
        Vector2[] directions = { new Vector2(0, 1), new Vector2(0, -1), new Vector2(-1, 0), new Vector2(1, 0) };

        // Embaralha a lista de direções para evitar padrões de movimento previsíveis
        Collections.shuffle(Arrays.asList(directions));

        // Tenta encontrar uma direção alternativa que não esteja ocupada por outro inimigo
        for (Vector2 direction : directions) {
            Vector2 alternatePosition = getNextPosition(direction, enemy);
            boolean isPositionAvailable = validateNextPositionAvailability(enemies, alternatePosition, enemy);
            if (isPositionAvailable) {
                return direction;
            }
        }

        // Se nenhuma direção estiver disponível, retorna null
        return null;
    }
}
