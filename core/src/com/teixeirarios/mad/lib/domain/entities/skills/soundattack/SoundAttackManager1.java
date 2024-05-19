package com.teixeirarios.mad.lib.domain.entities.skills.soundattack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.teixeirarios.mad.lib.domain.entities.enemy.Enemy;
import com.teixeirarios.mad.lib.domain.entities.enemy.EnemyManager;
import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.domain.entities.skills.abstracts.AbstractSkillManager;
import com.teixeirarios.mad.lib.utils.ListUtils;

public class SoundAttackManager1 extends SoundAttackManagerBase {
    public Boolean isActive;
    public String name;
    public int width;
    public int height;
    public int speed;
    public int damage;
    public String spritesheet;
    public int interval;
    public int lifeTime;
    SpriteBatch batch;

    public SoundAttackManager1(SpriteBatch batch) {
        super();
        this.isActive = true;
        this.name = "Sound Attack";
        this.width = 26;
        this.height = 26;
        this.speed = 3;
        this.damage = 2;
        this.spritesheet = "skills/sound_attack_1.png";
        this.interval = 1000;
        this.lifeTime = 60 * 5; //frames * sec
        this.batch = batch;
    }

    @Override
    public AbstractSkillManager upgrade() {
        SoundAttackManager1 skill = new SoundAttackManager1(batch);
        skill.damage += 1;
        skill.interval -= 50;
        return skill;
    }

    public void spawn(Player player, EnemyManager enemyManager) {

        if (player == null || enemyManager == null || enemyManager.getEnemies().size == 0) return;

        Array<Enemy> nearbyEnemies = getNearbyEnemies(player, enemyManager);

        if (!nearbyEnemies.isEmpty()) {
            Enemy targetEnemy = nearbyEnemies.get(0);

            SoundAttackUnit soundAttackUnit = new SoundAttackUnit(
                    player.getPosX() + (player.getWidth() / 2),
                    player.getPosY() + (player.getHeight() / 2),
                    targetEnemy.getPosX() + (targetEnemy.getWidth() / 2),
                    targetEnemy.getPosY() + (targetEnemy.getHeight() / 2),
                    this.width,
                    this.height,
                    this.damage,
                    this.speed,
                    false,
                    this.spritesheet,
                    1,
                    this.lifeTime,
                    batch
            );

            this.activeSkills.add(soundAttackUnit);
        }
    }

    private Array<Enemy> getNearbyEnemies(Player player, EnemyManager enemyManager) {
        RangeArea rangeArea = new RangeArea(
                player.getPosX() - 500,
                player.getPosY() - 500,
                player.getPosX() + 500,
                player.getPosY() + 500
        );

        Array<Enemy> nearbyEnemies = new Array<>();
        Array<Enemy> enemies = enemyManager.getEnemies();

        if (enemies == null) {
            System.err.println("Error: enemies array is null");
            return nearbyEnemies;
        }

        if (enemies.size > 1) {
            sortEnemiesByDistance(enemies, player);

//            enemies.sort(new Comparator<Enemy>() {
//                @Override
//                public int compare(Enemy e1, Enemy e2) {
//                    double distanceToE1 = calculateDistance(player, e1);
//                    double distanceToE2 = calculateDistance(player, e2);
//                    return Double.compare(distanceToE1, distanceToE2);
//                }
//            });
        }

        for (int index = 0; index < enemies.size; index++) {
            Enemy enemy = enemies.get(index);

            if (enemy.getPosX() >= rangeArea.left &&
                    enemy.getPosX() <= rangeArea.right &&
                    enemy.getPosY() >= rangeArea.top &&
                    enemy.getPosY() <= rangeArea.bottom) {
                nearbyEnemies.add(enemy);
            }
        }
        return nearbyEnemies;
    }

    private void sortEnemiesByDistance(Array<Enemy> enemies, Player player) {
        ListUtils.bubbleSort(enemies, player);
    }

    private static class RangeArea {
        public float left, top, right, bottom;

        public RangeArea(float left, float top, float right, float bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }
}
