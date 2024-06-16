package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.infra.database.models.UserState;
import com.teixeirarios.mad.lib.infra.database.repository.UserRepository;
import com.teixeirarios.mad.lib.infra.events.EventManager;

public class PlayerStatus {
    public int level;
    public float maxHealth, currentHealth, nextLevelXp, currentXP;

    public Long totalXP;
    private final EventManager eventManager;
    private UserRepository userRepository;

    public PlayerStatus() {
        userRepository = UserRepository.getInstance();
        UserState userState = userRepository.getUserState();

        level = 1;
        maxHealth = userState.health;
        currentHealth = userState.health;
        currentXP = 0;
        nextLevelXp = 100;
        totalXP = 0L;

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void addEventListeners() {
        eventManager.on("enemy:collision", args -> {
            takeDamage((int) args[0]);
        });
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;

        if (this.currentHealth <= 0) {
            die();
        }
    }

    public void takeXp(float xp) {
        float updateCurrentXP = this.currentXP + xp;

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
        //eventManager.emit("player:levelup:forcefield");
    }

    public void die() {
        this.eventManager.emit("player:die");
        saveBattleAchievements(totalXP);
    }

    public void saveBattleAchievements(long totalXP) {
        UserState userState = userRepository.getUserState();
        //userState.money += totalXP / 10;
        userState.experience += totalXP / 5;
        userStateLevelUp(userState);

        UserRepository.getInstance().setUserState(userState);
    }

    public void userStateLevelUp(UserState userState) {
        if (userState.experience >= userState.nextLevelUp) {
            userState.level += 1;
            userState.experience -= userState.nextLevelUp;
            userState.nextLevelUp = Math.round(userState.nextLevelUp * 1.5f);
            userState.points += 1;

            userStateLevelUp(userState);
        }
    }
}
