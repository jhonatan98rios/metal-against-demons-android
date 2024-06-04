package com.teixeirarios.mad.lib.domain.entities.stage;

import java.util.HashMap;

public class StageManager {
    public static StageManager instance;
    private final HashMap<String, StageModel> stagesDataMap = new HashMap<>();
    private StageModel currentStage;

    private StageManager() {
        addStageData(new StageModel("1", 10, 10, 1f, 1f));
        addStageData(new StageModel("2", 25, 10, 1f, 1f));
        addStageData(new StageModel("3", 50, 10, 1f, 1f));
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }
    public void addStageData(StageModel gameData) {
        stagesDataMap.put(gameData.getId(), gameData);
    }
    public StageModel getStageData(String id) {
        return stagesDataMap.get(id);
    }
    public HashMap<String, StageModel> getAllStagesData() {
        return stagesDataMap;
    }
    public void setCurrentStage(StageModel currentStage) {
        this.currentStage = currentStage;
    }
    public StageModel getCurrentStage() {
        return currentStage;
    }
}
