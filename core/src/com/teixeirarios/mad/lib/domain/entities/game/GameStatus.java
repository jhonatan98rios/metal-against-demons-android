package com.teixeirarios.mad.lib.domain.entities.game;

import com.teixeirarios.mad.lib.infra.events.EventManager;

public class GameStatus {
    private GameStatusOptions status;
    public static GameStatus instance;
    private final EventManager eventManager;

    public GameStatus(GameStatusOptions status) {
        this.status = status;
        eventManager = EventManager.getInstance();
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

    private void addEventListeners() {
        eventManager.on("player:die", args -> {
            setStatus(GameStatusOptions.STOPPED);

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