package com.teixeirarios.mad.lib.domain.entities.game;

import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager;
import com.teixeirarios.mad.lib.infra.database.models.UserState;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class GameStatus {

    private final EventManager eventManager;
    private final UserRepository userRepository;
    private final UserState userState;
    public static GameStatus instance;
    private GameStatusOptions status;
    private StageManager stageManager;
    private int deadEnemies;

    public GameStatus(GameStatusOptions status) {
        this.userRepository = UserRepository.getInstance();
        this.userState = userRepository.getUserState();

        this.status = status;
        this.stageManager = StageManager.getInstance();
        this.eventManager = EventManager.getInstance();
        addEventListeners();
    }

    public static GameStatus getInstance() {
        if (instance == null) {
            instance = new GameStatus(GameStatusOptions.PLAYING);
        }
        return instance;
    }

    public GameStatusOptions getStatus() {
        return status;
    }

    public void setStatus(GameStatusOptions status) {
        this.status = status;
    }

    public boolean isPlaying() {
        return status == GameStatusOptions.PLAYING;
    }

    public boolean isPaused() {
        return status == GameStatusOptions.PAUSED;
    }

    public boolean isStopped() {
        return status == GameStatusOptions.STOPPED;
    }

    public boolean isLevelUp() {
        return status == GameStatusOptions.LEVELUP;
    }

    public void saveBattleAchievements(long totalXp, boolean isWin) {
        //userState.money += totalXP / 10;
        userState.experience += Math.round((totalXp * userState.luck) / 4) ;
        userStateLevelUp();

        if (isWin && userState.currentStage == stageManager.getCurrentStage().getId()) {
            userState.currentStage += 1;
        }

        userRepository.setUserState(userState);
    }

    public void userStateLevelUp() {
        if (userState.experience >= userState.nextLevelUp) {
            userState.level += 1;
            userState.experience -= userState.nextLevelUp;
            userState.nextLevelUp = Math.round(userState.nextLevelUp * 1.2f);
            userState.points += 1;

            userStateLevelUp();
        }
    }

    private void addEventListeners() {
        eventManager.on("status:gameover", args -> {
            setStatus(GameStatusOptions.STOPPED);
        });

        eventManager.on("enemy:die", args -> {
            deadEnemies += 1;

            if (deadEnemies >= stageManager.getCurrentStage().getTotalEnemies()) {
                this.eventManager.emit("status:gameover", true);

                long totalXp = Player.getInstance().playerStatus.totalXP;
                saveBattleAchievements(totalXp, true);

                setStatus(GameStatusOptions.STOPPED);
            }
        });

        eventManager.on("player:levelup", args -> {
            setStatus(GameStatusOptions.LEVELUP);
        });

        eventManager.on("status:pause", args -> {
            setStatus(GameStatusOptions.PAUSED);
        });

        eventManager.on("status:play", args -> {
            setStatus(GameStatusOptions.PLAYING);
        });
    }
}