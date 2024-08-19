package com.teixeirarios.mad.lib.domain.entities.skills.abstracts;

import com.teixeirarios.mad.lib.domain.entities.player.Player;

public interface AbstractSkillManager {
    void spawn(Player player);
    void update();
    void upgrade();
    void addEventListeners();
    String getCategory();
}
