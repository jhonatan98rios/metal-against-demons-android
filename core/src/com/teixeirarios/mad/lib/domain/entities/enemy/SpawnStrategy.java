package com.teixeirarios.mad.lib.domain.entities.enemy;

import static com.badlogic.gdx.math.MathUtils.random;

import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel;

import java.util.ArrayList;
import java.util.Map;

public class SpawnStrategy {
    private final ArrayList<AbstractEcosystemFactory> ecosystemStrategies = new ArrayList<>();

    public SpawnStrategy() {

        StageModel stage = StageManager.getInstance().getCurrentStage();
        var enemies = stage.getEnemies();

        if (enemies.isEmpty()) {
            throw new IllegalStateException("No enemies available");
        }

        for (Map.Entry<AbstractEcosystemFactory, Integer> entry : enemies.entrySet()) {
            AbstractEcosystemFactory factory = entry.getKey();
            int weight = entry.getValue();
            addFactoryWithWeight(factory, weight);
        }
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

    public ArrayList<AbstractEcosystemFactory> getBossEcosystemStrategy() {
        return StageManager.getInstance().getCurrentStage().getBosses();
    }
}

