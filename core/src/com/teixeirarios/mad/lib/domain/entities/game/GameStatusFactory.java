package com.teixeirarios.mad.lib.domain.entities.game;

public class GameStatusFactory {

    public static GameStatus create() {
        GameStatus gameStatus = GameStatus.getInstance();
        return gameStatus;
    }
}
