package com.teixeirarios.mad.lib.domain.entities.enemy;

import com.badlogic.gdx.graphics.Color;
import com.teixeirarios.mad.lib.domain.abstracts.Body2D;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.ShapeCanvas;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

import java.util.UUID;

public class Enemy implements Body2D {
    private final String category;
    private final UUID id;
    private final int velocity, width, height, safetyMargin;
    private int posX, posY, selectedDirection;
    public CanvasFacade enemyCanvas;
    public EnemyStatus status;

    public Enemy(
            String category,
            int width, int height, int posX, int posY, int safetyMargin, int velocity,
            int maxHealth, float damage, CanvasFacade enemyCanvas
        ) {
        this.category = category;
        this.safetyMargin = safetyMargin;
        this.id = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        this.velocity = velocity;
        this.enemyCanvas = enemyCanvas;
        this.selectedDirection = 0;
        this.status = new EnemyStatus(maxHealth, damage);
    }

    public void update(float playerPosX) {
        //enemyCanvas.animate();
        this.selectedDirection = getSprite(playerPosX);
    }

    public void render() {
        enemyCanvas.drawImage(
                0,
                selectedDirection,
                (float) this.width /2,
                (float) this.height /2,
                this.posX,
                this.posY,
                this.width,
                this.height
        );
    }

    public void renderHealthBar(Camera camera) {
        if (status.currentHealth <= 0 || status.currentHealth == status.maxHealth) {return;}

        float healthPercentage = status.currentHealth / status.maxHealth;
        float width = healthPercentage * 64;
        float height = 5f;
        float dx = this.posX + ((float) this.width / 2) - (width / 2) - camera.getPosX();
        float dy = this.posY + this.height + 10 - camera.getPosY();

        ShapeCanvas.drawShape(
            healthPercentage < 0.5f ? Color.RED : Color.GREEN,
                dx,
                dy,
                width,
                height
        );
    }

    public int getSprite(float playerPosX) {
        return (playerPosX + 50) > posX + ((float) width / 2) ? 0 : this.height /2;
    }

    // Getters e setters

    public UUID getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSafetyMargin() {
        return safetyMargin;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setPosX(int nextPosX) {
        posX = nextPosX;
    }

    public void setPosY(int nextPosY) {
        posY = nextPosY;
    }

    public String getCategory() {
        return category;
    }
}
