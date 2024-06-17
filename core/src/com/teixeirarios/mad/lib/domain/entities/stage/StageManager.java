package com.teixeirarios.mad.lib.domain.entities.stage;

import java.util.HashMap;

public class StageManager {
    public static StageManager instance;
    private final HashMap<Integer, StageModel> stagesDataMap = new HashMap<>();
    private StageModel currentStage;

    private StageManager() {
        addStageData(new StageModel(0, 5, 25, 50, 1f, 1f));
        addStageData(new StageModel(1, 10, 50, 30, 1.5f, 1.5f));
        addStageData(new StageModel(2, 20, 100, 30, 2f, 2f));
        addStageData(new StageModel(3, 50, 200, 30, 3f, 3f));
        addStageData(new StageModel(4, 100, 300, 20, 4f, 4f));
        addStageData(new StageModel(5, 150, 500, 20, 5f, 5f));
        addStageData(new StageModel(6, 200, 666, 20, 6f, 6f));
        addStageData(new StageModel(7, 250, 750, 10, 7f, 7f));
        addStageData(new StageModel(8, 300, 1000, 10, 8f, 8f));
        addStageData(new StageModel(9, 400, 1200, 10, 8f, 8f));
        addStageData(new StageModel(10, 500, 1500, 5, 10f, 10f));
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public void addStageData(StageModel stageModel) {
        stagesDataMap.put(stageModel.getId(), stageModel);
    }

    public StageModel getStageData(int id) {
        return stagesDataMap.get(id);
    }

    public void setCurrentStage(StageModel currentStage) {
        this.currentStage = currentStage;
    }

    public StageModel getCurrentStage() {
        return currentStage;
    }
}
