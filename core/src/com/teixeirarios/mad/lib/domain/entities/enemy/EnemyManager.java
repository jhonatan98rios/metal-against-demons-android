package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.strategies.CollisionStrategy;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Constants;

public class EnemyManager {

    private final Player player;
    private final Camera camera;
    private final Array<Enemy> enemies;
    private final int spawnInterval;
    private final int maxEnemies;
    private float spawnTimer;
    private final MovimentationStrategy movimentationStrategy;
    private final SpawnStrategy spawnStrategy;
    private final SpriteBatch batch;
    private final EventManager eventManager;
    public static EnemyManager instance;

    public EnemyManager(
            SpriteBatch batch,
            Player player,
            Camera camera,
            int spawnInterval,
            int maxEnemies,
            MovimentationStrategy movimentationStrategy,
            SpawnStrategy spawnStrategy,
            EventManager eventManager
    ) {
        this.enemies = new Array<>();
        this.spawnInterval = spawnInterval;
        this.maxEnemies = maxEnemies;
        this.spawnTimer = 0;
        this.movimentationStrategy = movimentationStrategy;
        this.spawnStrategy = spawnStrategy;
        this.player = player;
        this.camera = camera;
        this.batch = batch;
        this.eventManager = eventManager;

        this.addEventListeners();
    }

    public static EnemyManager getInstance(
            SpriteBatch batch,
            Player player,
            Camera camera,
            int spawnInterval,
            int maxEnemies,
            MovimentationStrategy movimentationStrategy,
            SpawnStrategy spawnStrategy,
            EventManager eventManager
    ) {
        if (EnemyManager.instance == null) {
            EnemyManager.instance = new EnemyManager(
                batch,
                player,
                camera,
                spawnInterval,
                maxEnemies,
                movimentationStrategy,
                spawnStrategy,
                eventManager
            );
        }
        return EnemyManager.instance;
    }

    public static EnemyManager getInstance() {
        if (EnemyManager.instance == null) {
            throw new IllegalStateException("EnemyManager não inicializado");
        }
        return EnemyManager.instance;
    }

    public void update() {
        spawnTimer += 1;
        if (spawnTimer >= spawnInterval && enemies.size < maxEnemies) {
            spawnEnemy();
            spawnTimer = 0;
        }

        Vector2 playerPosition = new Vector2(player.getPosX(), player.getPosY());
        movimentationStrategy.updateEnemiesMovement(enemies, playerPosition);

        for (Enemy enemy : enemies) {
            enemy.update(player.posX);

            if (CollisionStrategy.isColliding(enemy, player, 10, 0)) {
                System.out.println("Colisão com o inimigo");
                eventManager.emit("enemy:collision", 1);
            }
        }
    }

    private void spawnEnemy() {
        // Definindo uma margem de segurança para evitar que inimigos sejam gerados muito próximos uns dos outros
        float safetyMargin = 36f;

        // Loop até encontrar uma posição adequada para o novo inimigo
        while (true) {
            // Gerar uma posição aleatória para o novo inimigo
            int posX = MathUtils.random(Constants.SCENARIO_WIDTH) - Constants.SCENARIO_WIDTH / 2;
            int posY = MathUtils.random(Constants.SCENARIO_HEIGHT) - Constants.SCENARIO_HEIGHT / 2;

            // Verificar se a nova posição está dentro do cenário
            if (posX < 0 || posX > Constants.SCENARIO_WIDTH || posY < 0 || posY > Constants.SCENARIO_HEIGHT) {
                continue;
            }

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
                var strategy = this.spawnStrategy.getRandomEcosystemStrategy();
                Enemy newEnemy = strategy.create(batch, posX, posY);
                enemies.add(newEnemy);
                break;
            }
        }
    }

    private void addEventListeners() {
        eventManager.on("skill:damage", args -> {
            Enemy enemy = (Enemy) args[0];
            int damage = (int) args[1];

            System.out.println("enemy: " + enemy);
            System.out.println("damage: " + damage);

            removeEnemy(enemy);

//            enemy.health -= damage;
//            if (enemy.health <= 0) {
//                removeEnemy(enemy);
//            }
        });
    }

    public void removeEnemy(Enemy enemy) {
        enemies.removeValue(enemy, true);
    }

    // Getters e setters
    public Array<Enemy> getEnemies() {
        return enemies;
    }

}