package com.teixeirarios.mad.lib.domain.entities.player;

import com.teixeirarios.mad.lib.infra.events.EventManager;

public class PlayerStatus {
    public int level, maxHealth, currentHealth, currentXP, nextLevelXp;
    private final EventManager eventManager;

    public PlayerStatus() {
        level = 1;
        maxHealth = 300;
        currentHealth = 300;
        currentXP = 0;
        nextLevelXp = 1;

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    private void addEventListeners() {
        eventManager.on("enemy:collision", args -> {
            // Processa os dados recebidos
            if (currentHealth > 0) {
                currentHealth -= 1;
            } else {
                eventManager.emit("player:die", 1);
            }
        });
    }
}
