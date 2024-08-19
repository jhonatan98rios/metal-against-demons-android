package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel;
import com.teixeirarios.mad.lib.domain.strategies.CollisionStrategy;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class EnemyManager {

    private final Player player;
    private final Camera camera;
    private final StageModel currentStage;
    private final ArrayList<Enemy> enemies;
    private final int spawnInterval;
    private final int maxEnemies;
    private float spawnTimer;
    private final MovimentationStrategy movimentationStrategy;
    private final SpawnStrategy spawnStrategy;
    private final SpriteBatch batch;
    private final EventManager eventManager;

    public EnemyManager(
            SpriteBatch batch,
            Player player,
            Camera camera,
            StageModel currentStage,
            MovimentationStrategy movimentationStrategy,
            SpawnStrategy spawnStrategy,
            EventManager eventManager
    ) {
        this.enemies = new ArrayList<>();
        this.currentStage = currentStage;
        this.spawnInterval = currentStage.getSpawnInterval();
        this.maxEnemies = currentStage.getMaxEnemies();
        this.spawnTimer = 0;
        this.movimentationStrategy = movimentationStrategy;
        this.spawnStrategy = spawnStrategy;
        this.player = player;
        this.camera = camera;
        this.batch = batch;
        this.eventManager = eventManager;

        this.addEventListeners();
        this.spawnBoss();
    }

    public void update() {
        spawnTimer += 1;
        if (spawnTimer >= spawnInterval && enemies.size() < maxEnemies) {
            spawnEnemy();
            spawnTimer = 0;
        }

        Vector2 playerPosition = new Vector2(
                player.getPosX() - ((float) player.getWidth() / 2),
                player.getPosY() - ((float) player.getHeight() / 2)
        );
        movimentationStrategy.updateEnemiesMovement(enemies, playerPosition);

        if (!enemies.isEmpty()) {
            ArrayList<Enemy> enemyCategories = getOneEnemyPerCategory();
            for (Enemy enemy : enemyCategories) {
                enemy.enemyCanvas.animate();
            }
        }

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.update(player.posX);

            if (CollisionStrategy.isColliding(enemy, player, enemy.getSafetyMargin())) {
                eventManager.emit("enemy:collision", enemy.status.damage);
            }
        }
    }

    private void spawnEnemy() {
        // Definindo uma margem de segurança para evitar que inimigos sejam gerados muito próximos uns dos outros
        float safetyMargin = 36f;

        // Loop até encontrar uma posição adequada para o novo inimigo
        while (true) {
            Vector2 position = calculateRandomPosition();

            // Verificar se a nova posição está ocupada por outro inimigo
            boolean positionOccupied = false;

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                if (Math.abs(position.x - enemy.getPosX()) < safetyMargin && Math.abs(position.y - enemy.getPosY()) < safetyMargin) {
                    positionOccupied = true;
                    break;
                }
            }

            // Se a posição não estiver ocupada, criar o novo inimigo e sai do loop
            if (!positionOccupied) {
                var strategy = this.spawnStrategy.getRandomEcosystemStrategy();
                Enemy newEnemy = strategy.create(batch, (int) position.x, (int) position.y);
                enemies.add(newEnemy);
                break;
            }
        }
    }

    private void spawnBoss() {
        if (this.currentStage.getBosses() == null) return;
        var strategies = this.spawnStrategy.getBossEcosystemStrategy();
        for (AbstractEcosystemFactory strategy : strategies) {
            Vector2 position = calculateRandomPosition();
            Enemy newEnemy = strategy.create(batch, (int) position.x, (int) position.y);
            enemies.add(newEnemy);
        }
    }
    
    private Vector2 calculateRandomPosition() {
        Random random = new Random();
        int region = random.nextInt(4);
        int scenarioWidth = Constants.SCENARIO_WIDTH;
        int scenarioHeight = Constants.SCENARIO_HEIGHT;
        int cameraWidth = camera.getWidth();
        int cameraHeight = camera.getHeight();
        int cameraX = camera.getPosX();
        int cameraY = camera.getPosY();
        int posX = 0, posY = 0, range = 0, seed = 0;

        switch (region) {
            case 0: // Above the camera
                posX = random.nextInt(scenarioWidth);
                posY = cameraY > 0 ? random.nextInt(cameraY) : 0;
                break;
            case 1: // Below the camera
                range = scenarioHeight - (cameraY + cameraHeight);
                seed = (range > 0) ? random.nextInt(range) : 0;
                posY = seed + (cameraY + cameraHeight);
                posX = random.nextInt(scenarioWidth);
                break;
            case 2: // Left of the camera
                posX = cameraX > 0 ? random.nextInt(cameraX) : 0;
                posY = random.nextInt(scenarioHeight);
                break;
            case 3: // Right of the camera
                range = scenarioWidth - (cameraX + cameraWidth);
                seed = (range > 0) ? random.nextInt(range) : 0;
                posX = seed + (cameraX + cameraWidth);
                posY = random.nextInt(scenarioHeight);
                break;
        }

        return new Vector2(posX, posY);
    }

    private void addEventListeners() {
        eventManager.on("skill:damage", args -> {
            Enemy enemy = (Enemy) args[0];
            float damage = (float) args[1];

            takeDamage(enemy, damage);
        });
    }

    public void takeDamage(Enemy enemy, float damage) {
        enemy.status.currentHealth -= damage;
        if (enemy.status.currentHealth <= 0) {
            removeEnemy(enemy);
        }
    }

    public void removeEnemy(Enemy enemy) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if (e.getId() == enemy.getId()) {
                iterator.remove();
            }
        }

        this.eventManager.emit("enemy:die", enemy.getCategory());

        this.eventManager.emit(
            "orb:spawn",
            enemy.getPosX() + (enemy.getWidth() / 2),
            enemy.getPosY() + (enemy.getHeight() / 2),
            enemy.status.maxHealth / 50
        );
    }

    public ArrayList<Enemy> getOneEnemyPerCategory() {
        HashMap<String, Enemy> categoryMap = new HashMap<>();
        for (Enemy enemy : this.enemies) {
            // Se a categoria ainda não estiver no mapa, adiciona
            if (!categoryMap.containsKey(enemy.getCategory())) {
                categoryMap.put(enemy.getCategory(), enemy);
            }
        }

        // Retorna os valores do mapa como uma lista
        return new ArrayList<>(categoryMap.values());
    }

    // Getters e setters
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void dispose() {
        for (int i=0; i < enemies.size(); i++) {
               Enemy enemy = enemies.get(i);
               if (enemy == null) return;
               enemy.enemyCanvas.dispose();
        }
    }
}