package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.utils.Constants;

public class EnemyManager {

    Player player;
    Camera camera;
    private Array<Enemy> enemies;
    private float spawnInterval;
    private int maxEnemies;

    private float spawnTimer;

    MovimentationStrategy movimentationStrategy;

    ShapeRenderer shapeRenderer;

    public EnemyManager(Player player, Camera camera, float spawnInterval, int maxEnemies, MovimentationStrategy movimentationStrategy) {
        this.enemies = new Array<>();
        this.spawnInterval = spawnInterval;
        this.maxEnemies = maxEnemies;
        this.spawnTimer = 0;
        this.movimentationStrategy = movimentationStrategy;
        this.player = player;
        this.camera = camera;

        this.shapeRenderer = new ShapeRenderer();

        System.out.println(this.camera.getPosX());
        System.out.println(this.camera.getPosY());
    }

    public void update() {
        spawnTimer += 1;
        if (spawnTimer >= spawnInterval && enemies.size < maxEnemies) {
            spawnEnemy();
            spawnTimer = 0;
        }

        Vector2 playerPosition = new Vector2(player.getPosX(), player.getPosY());
        Vector2 cameraPosition = new Vector2(camera.getPosX(), camera.getPosY());

        movimentationStrategy.updateEnemiesMovement(enemies, playerPosition, cameraPosition);
        render(cameraPosition);
    }

    public void render(Vector2 cameraPosition) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        for (Enemy enemy : enemies) {
            shapeRenderer.rect(
                    enemy.getPosX() - cameraPosition.x,
                    enemy.getPosY() - cameraPosition.y,
                    enemy.getWidth(),
                    enemy.getHeight()
            );
        }

        shapeRenderer.end();
    }

    private void spawnEnemy() {
        // Definindo uma margem de segurança para evitar que inimigos sejam gerados muito próximos uns dos outros
        float safetyMargin = 10f;

        // Loop até encontrar uma posição adequada para o novo inimigo
        while (true) {
            // Gerar uma posição aleatória para o novo inimigo
            float posX = MathUtils.random(Constants.SCENARIO_WIDTH);
            float posY = MathUtils.random(Constants.SCENARIO_HEIGHT);

            // Verificar se a nova posição está ocupada por outro inimigo
            boolean positionOccupied = false;
            for (Enemy enemy : enemies) {
                if (Math.abs(posX - enemy.getPosX()) < safetyMargin && Math.abs(posY - enemy.getPosY()) < safetyMargin) {
                    positionOccupied = true;
                    break;
                }
            }

            // Se a posição não estiver ocupada, criar o novo inimigo e sair do loop
            if (!positionOccupied) {
                Enemy newEnemy = new Enemy(64, 64, posX, posY);
                enemies.add(newEnemy);
                break;
            }
        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.removeValue(enemy, true);
    }

    // Getters e setters
    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public void setSpawnInterval(float spawnInterval) {
        this.spawnInterval = spawnInterval;
    }

    public void setMaxEnemies(int maxEnemies) {
        this.maxEnemies = maxEnemies;
    }
}