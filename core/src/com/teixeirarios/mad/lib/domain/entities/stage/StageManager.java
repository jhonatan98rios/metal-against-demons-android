package com.teixeirarios.mad.lib.domain.entities.stage;

import java.util.HashMap;

public class StageManager {
    public static StageManager instance;
    private final HashMap<Integer, StageModel> stagesDataMap = new HashMap<>();
    private StageModel currentStage;

    private StageManager() {
        addStageData(StageModelMock.GetStageModelLevel1());
        addStageData(StageModelMock.GetStageModelLevel2());
        addStageData(StageModelMock.GetStageModelLevel3());
        addStageData(StageModelMock.GetStageModelLevel4());
        addStageData(StageModelMock.GetStageModelLevel5());
        addStageData(StageModelMock.GetStageModelLevel6());
        addStageData(StageModelMock.GetStageModelLevel7());
        addStageData(StageModelMock.GetStageModelLevel8());
        addStageData(StageModelMock.GetStageModelLevel9());
        addStageData(StageModelMock.GetStageModelLevel10());
        addStageData(StageModelMock.GetStageModelLevel11());
        addStageData(StageModelMock.GetStageModelLevel12());
        addStageData(StageModelMock.GetStageModelLevel13());
        addStageData(StageModelMock.GetStageModelLevel14());
        addStageData(StageModelMock.GetStageModelLevel15());
        addStageData(StageModelMock.GetStageModelLevel16());
        addStageData(StageModelMock.GetStageModelLevel17());
        addStageData(StageModelMock.GetStageModelLevel18());
        addStageData(StageModelMock.GetStageModelLevel19());
        addStageData(StageModelMock.GetStageModelLevel20());
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

    public boolean isThereAnyBosses() {
        return currentStage.getBosses() != null;
    }

    public int getTotalBosses() {
        return currentStage.getBosses().size();
    }
}
