package com.teixeirarios.mad.lib.domain.entities.stage;

import com.teixeirarios.mad.lib.domain.entities.enemy.AbstractEcosystemFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.AzazelFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CyclopeFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.DragonFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.GargoyleFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class StageModelMock {

    public static StageModel GetStageModelLevel1() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 2);
        return new StageModel(0, 10, 25, 50, 1f, 1f, enemies, null);
    }

    public static StageModel GetStageModelLevel2() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 2);
        return new StageModel(1, 15, 50, 30, 1.2f, 1.2f, enemies, null);
    }

    public static StageModel GetStageModelLevel3() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);
        return new StageModel(2, 20, 100, 30, 1.5f, 1.5f, enemies, null);
    }

    public static StageModel GetStageModelLevel4() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);
        return new StageModel(3, 30, 250, 30, 2f, 2f, enemies, null);
    }

    public static StageModel GetStageModelLevel5() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 6);
        enemies.put(CrawlerFactory::create, 3);
        enemies.put(CyclopeFactory::create, 1);

        ArrayList<AbstractEcosystemFactory> bosses = new ArrayList<>();
        bosses.add(AzazelFactory::create);

        return new StageModel(4, 50, 350, 30, 2.5f, 3f, enemies, bosses);
    }

    public static StageModel GetStageModelLevel6() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(5, 75, 500, 30, 3f, 4f, enemies, null);
    }

    public static StageModel GetStageModelLevel7() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(6, 100, 666, 30, 4f, 5f, enemies, null);
    }

    public static StageModel GetStageModelLevel8() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(7, 120, 750, 25, 5f, 5f, enemies, null);
    }

    public static StageModel GetStageModelLevel9() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(8, 150, 1000, 25, 6f, 6f, enemies, null);
    }

    public static StageModel GetStageModelLevel10() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);

        ArrayList<AbstractEcosystemFactory> bosses = new ArrayList<>();
        bosses.add(GargoyleFactory::create);
        bosses.add(GargoyleFactory::create);

        return new StageModel(9, 150, 1000, 25, 6f, 6f, enemies, bosses);
    }
}