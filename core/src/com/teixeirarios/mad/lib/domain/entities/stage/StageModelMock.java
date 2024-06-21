package com.teixeirarios.mad.lib.domain.entities.stage;

import com.teixeirarios.mad.lib.domain.entities.enemy.AbstractEcosystemFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CyclopeFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.DragonFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;

import java.util.HashMap;

public class StageModelMock {

    public static StageModel GetStageModelLevel1() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 2);
        return new StageModel(0, 5, 25, 50, 1f, 1f, enemies);
    }

    public static StageModel GetStageModelLevel2() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 2);
        return new StageModel(1, 10, 50, 30, 1.5f, 1.5f, enemies);
    }

    public static StageModel GetStageModelLevel3() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);
        return new StageModel(2, 25, 100, 30, 2f, 2f, enemies);
    }

    public static StageModel GetStageModelLevel4() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);
        return new StageModel(3, 32, 200, 30, 3f, 3f, enemies);
    }

    public static StageModel GetStageModelLevel5() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);
        return new StageModel(4, 50, 350, 20, 4f, 4f, enemies);
    }

    public static StageModel GetStageModelLevel6() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(5, 75, 500, 20, 5f, 5f, enemies);
    }

    public static StageModel GetStageModelLevel7() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(6, 100, 666, 20, 6f, 6f, enemies);
    }

    public static StageModel GetStageModelLevel8() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(7, 150, 750, 10, 7f, 7f, enemies);
    }

    public static StageModel GetStageModelLevel9() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(8, 200, 1000, 10, 8f, 8f, enemies);
    }

    public static StageModel GetStageModelLevel10() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 2);
        return new StageModel(9, 250, 1200, 10, 9f, 9f, enemies);
    }

    public static StageModel GetStageModelLevel11() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 2);
        return new StageModel(10, 300, 1500, 5, 10f, 10f, enemies);
    }
}
