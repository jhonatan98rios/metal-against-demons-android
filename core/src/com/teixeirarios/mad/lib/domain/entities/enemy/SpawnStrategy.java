package com.teixeirarios.mad.lib.domain.entities.enemy;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.CrawlerFactory;
import com.teixeirarios.mad.lib.domain.entities.enemy.ecosystem.SpiritFactory;

import java.util.ArrayList;

public class SpawnStrategy {
    private final ArrayList<AbstractEcosystemFactory> ecosystemStrategies = new ArrayList<>();

    public SpawnStrategy() {
        addFactoryWithWeight(SpiritFactory::create, 3); // 75%
        addFactoryWithWeight(CrawlerFactory::create, 1); // 25%
    }

    // Método para adicionar fábricas com pesos
    private void addFactoryWithWeight(AbstractEcosystemFactory factory, int weight) {
        for (int i = 0; i < weight; i++) {
            ecosystemStrategies.add(factory);
        }
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
