package com.teixeirarios.mad.lib.domain.entities.stage;

import com.teixeirarios.mad.lib.domain.entities.enemy.AbstractEcosystemFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
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
        return new StageModel(1, 10, 50, 30, 2f, 2f, enemies);
    }

    public static StageModel GetStageModelLevel3() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 7);
        enemies.put(CrawlerFactory::create, 3);
        return new StageModel(2, 30, 100, 30, 2f, 2f, enemies);
    }

    public static StageModel GetStageModelLevel4() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 7);
        enemies.put(CrawlerFactory::create, 3);
        return new StageModel(3, 50, 200, 30, 3f, 3f, enemies);
    }

    public static StageModel GetStageModelLevel5() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 4);
        return new StageModel(4, 100, 350, 20, 4f, 4f, enemies);
    }

    public static StageModel GetStageModelLevel6() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 14);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(5, 120, 500, 20, 4f, 4f, enemies);
    }

    public static StageModel GetStageModelLevel7() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 14);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(6, 200, 666, 20, 5f, 5f, enemies);
    }

    public static StageModel GetStageModelLevel8() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 5);
        enemies.put(CrawlerFactory::create, 4);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(7, 250, 750, 10, 6f, 6f, enemies);
    }

    public static StageModel GetStageModelLevel9() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 5);
        enemies.put(CrawlerFactory::create, 4);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(8, 300, 1000, 10, 7f, 7f, enemies);
    }

    public static StageModel GetStageModelLevel10() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 4);
        enemies.put(CrawlerFactory::create, 4);
        enemies.put(DragonFactory::create, 2);
        return new StageModel(9, 400, 1200, 10, 8f, 8f, enemies);
    }

    public static StageModel GetStageModelLevel11() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 4);
        enemies.put(CrawlerFactory::create, 4);
        enemies.put(DragonFactory::create, 2);
        return new StageModel(10, 500, 1500, 5, 10f, 10f, enemies);
    }
}
