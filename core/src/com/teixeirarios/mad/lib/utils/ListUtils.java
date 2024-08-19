package com.teixeirarios.mad.lib.utils;

import static com.teixeirarios.mad.lib.utils.Calculate.calculateDistance;

import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.player.Player;

import java.util.ArrayList;

public class ListUtils {
    public static void bubbleSort(ArrayList<Enemy> enemies, Player player) {
        int n = enemies.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (calculateDistance(player, enemies.get(j)) > calculateDistance(player, enemies.get(j + 1))) {
                    // Swap enemies[j] and enemies[j+1]
                    Enemy temp = enemies.get(j);
                    enemies.set(j, enemies.get(j + 1));
                    enemies.set(j + 1, temp);
                }
            }
        }
    }
}
