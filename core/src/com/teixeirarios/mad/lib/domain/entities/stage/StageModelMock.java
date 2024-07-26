package com.teixeirarios.mad.lib.domain.entities.stage;

import com.teixeirarios.mad.lib.domain.entities.enemy.AbstractEcosystemFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.AzazelFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CyclopeFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.DragonFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.GargoyleFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.NightmareFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.TormentorFactory;

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
        return new StageModel(7, 120, 750, 30, 5f, 5f, enemies, null);
    }

    public static StageModel GetStageModelLevel9() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 12);
        enemies.put(CrawlerFactory::create, 5);
        enemies.put(CyclopeFactory::create, 2);
        enemies.put(DragonFactory::create, 1);
        return new StageModel(8, 150, 1000, 30, 6f, 6f, enemies, null);
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

        return new StageModel(9, 150, 1000, 30, 6f, 6f, enemies, bosses);
    }

    public static StageModel GetStageModelLevel11() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 6);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(10, 200, 1200, 25, 7f, 7f, enemies, null);
    }

    public static StageModel GetStageModelLevel12() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 6);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(11, 250, 1300, 25, 8f, 8f, enemies, null);
    }

    public static StageModel GetStageModelLevel13() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 6);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(12, 300, 1400, 25, 8f, 8f, enemies, null);
    }

    public static StageModel GetStageModelLevel14() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 6);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(13, 350, 1500, 25, 9f, 9f, enemies, null);
    }

    public static StageModel GetStageModelLevel15() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 10);
        enemies.put(CrawlerFactory::create, 6);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        ArrayList<AbstractEcosystemFactory> bosses = new ArrayList<>();
        bosses.add(NightmareFactory::create);

        return new StageModel(14, 350, 1500, 25, 9f, 9f, enemies, bosses);
    }

    public static StageModel GetStageModelLevel16() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 9);
        enemies.put(CrawlerFactory::create, 7);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(15, 400, 1600, 20, 10f, 10f, enemies, null);
    }

    public static StageModel GetStageModelLevel17() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 9);
        enemies.put(CrawlerFactory::create, 7);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(16, 450, 1700, 20, 10f, 10f, enemies, null);
    }

    public static StageModel GetStageModelLevel18() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 9);
        enemies.put(CrawlerFactory::create, 7);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 1);

        return new StageModel(17, 500, 1800, 20, 11f, 11f, enemies, null);
    }

    public static StageModel GetStageModelLevel19() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 7);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 2);

        return new StageModel(18, 500, 2000, 20, 12f, 12f, enemies, null);
    }

    public static StageModel GetStageModelLevel20() {
        HashMap<AbstractEcosystemFactory, Integer> enemies = new HashMap<>();
        enemies.put(SpiritFactory::create, 8);
        enemies.put(CrawlerFactory::create, 7);
        enemies.put(CyclopeFactory::create, 3);
        enemies.put(DragonFactory::create, 2);

        ArrayList<AbstractEcosystemFactory> bosses = new ArrayList<>();
        bosses.add(TormentorFactory::create);

        return new StageModel(19, 700, 2000, 10, 12f, 12f, enemies, bosses);
    }
}