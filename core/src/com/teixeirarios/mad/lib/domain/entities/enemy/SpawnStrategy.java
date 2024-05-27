package com.teixeirarios.mad.lib.domain.entities.enemy;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;

import java.util.ArrayList;

public class SpawnStrategy {
    private ArrayList<AbstractEcosystemFactory> ecosystemStrategies = new ArrayList<>();

    public SpawnStrategy() {
        ecosystemStrategies.add(SpiritFactory::create);
        ecosystemStrategies.add(CrawlerFactory::create);
    }

    public AbstractEcosystemFactory getRandomEcosystemStrategy() {
        if (ecosystemStrategies.isEmpty()) {
            throw new IllegalStateException("No strategies available");
        }
        int randomIndex = random.nextInt(ecosystemStrategies.size());
        return ecosystemStrategies.get(randomIndex);
    }

    @FunctionalInterface
    public interface AbstractEcosystemFactory {
        Enemy create(SpriteBatch batch, int posX, int posY);
    }
}
