package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.domain.entities.game.GameStatus;
import com.teixeirarios.mad.lib.infra.database.models.UserState;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class PlayerStatus {
    private final EventManager eventManager;
    private final UserState userState;

    public int level;
    public float maxHealth, currentHealth, nextLevelXp, currentXP;
    public long totalXP;

    public PlayerStatus() {
        UserRepository userRepository = UserRepository.getInstance();
        userState = userRepository.getUserState();

        level = 1;
        maxHealth = userState.health;
        currentHealth = userState.health;
        currentXP = 0;
        nextLevelXp = 20;
        totalXP = 0L;

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void addEventListeners() {
        eventManager.on("enemy:collision", args -> {
            takeDamage((float) args[0]);
        });
    }

    public void takeDamage(float damage) {
        //this.currentHealth -= damage;

        if (this.currentHealth <= 0) {
            die();
        }
    }

    public void takeXp(float xp) {
        float updateCurrentXP = this.currentXP + (xp * userState.luck);

        if (updateCurrentXP >= this.nextLevelXp) {
            updateCurrentXP -= this.nextLevelXp;
            this.levelup();
        }

        this.currentXP = updateCurrentXP;
        this.totalXP = (long) (this.totalXP + xp);
    }

    public void levelup() {
        this.level += 1;
        this.nextLevelXp = this.nextLevelXp * 1.5f;
        this.maxHealth += 1;
        this.currentHealth += 1;
        this.eventManager.emit("player:levelup");
    }

    public void die() {
        this.eventManager.emit("status:gameover", false);
        GameStatus gameStatus = GameStatus.getInstance();
        gameStatus.saveBattleAchievements(totalXP, false);
    }
}
