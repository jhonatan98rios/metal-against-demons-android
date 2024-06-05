package com.teixeirarios.mad.lib.domain.entities.stage;

import java.util.HashMap;

public class StageManager {
    public static StageManager instance;
    private final HashMap<Integer, StageModel> stagesDataMap = new HashMap<>();
    private StageModel currentStage;

    private StageManager() {
        addStageData(new StageModel(0, 10, 10, 1f, 1f));
        addStageData(new StageModel(1, 25, 10, 1f, 1f));
        addStageData(new StageModel(2, 50, 10, 1f, 1f));
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
