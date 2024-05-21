package com.teixeirarios.mad.lib.domain.entities.game;

import com.teixeirarios.mad.lib.infra.events.EventManager;

public class GameStatus {
    private GameStatusOptions status;
    public static GameStatus instance;
    public GameStatusRender render;

    private final EventManager eventManager;

    public GameStatus(GameStatusOptions status, GameStatusRender render) {
        this.status = status;
        this.render = render;
        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    public static GameStatus getInstance(GameStatusRender render) {
        if (instance == null) {
            instance = new GameStatus(GameStatusOptions.PLAYING, render);
        }
        return instance;
    }

    public static GameStatus getInstance() {
        if (instance == null) {
            throw new RuntimeException("GameStatus not initialized");
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

    public void renderPauseButton() {
        if (isPlaying()) {
            render.renderPauseButton();
        }
    }

    private void addEventListeners() {
        eventManager.on("player:die", args -> {
            setStatus(GameStatusOptions.STOPPED);
        });

        eventManager.on("status:pause", args -> {
            setStatus(GameStatusOptions.PAUSED);
        });

        eventManager.on("status:play", args -> {
            setStatus(GameStatusOptions.PLAYING);
        });
    }
}