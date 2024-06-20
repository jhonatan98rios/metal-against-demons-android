package com.teixeirarios.mad.lib.domain.entities.orb;


import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.canvas.ShapeCanvas;
import com.teixeirarios.mad.lib.infra.events.EventManager;
import com.teixeirarios.mad.lib.utils.Intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;

public class OrbManager {
    EventManager eventManager;
    ArrayList<Orb> orbs;
    Player player;
    Camera camera;
    AbstractCanvasFacade canvas;

    public OrbManager(AbstractCanvasFacade canvas, Camera camera) {
        this.canvas = canvas;
        this.camera = camera;

        this.player = Player.getInstance();
        this.orbs = new ArrayList<>();

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

   private void spawnXpOrb(Orb orb)  {
        this.orbs.add(orb);
        this.sortOrbs();
    }

    public void sortOrbs() {
        Collections.sort(orbs, (a, b) -> {
            double distanceToA = Math.sqrt(Math.pow(player.getPosX() - a.getPosX(), 2) + Math.pow(player.getPosY() - a.getPosY(), 2));
            double distanceToB = Math.sqrt(Math.pow(player.getPosX() - b.getPosX(), 2) + Math.pow(player.getPosY() - b.getPosY(), 2));
            return Double.compare(distanceToA, distanceToB);
        });
    }

    public void checkOrbsCollection(Player player) {

        if (orbs.isEmpty()) {
            return;
        }

        for (int i = 0; i <= orbs.size()-1; i++) {
            Orb orb = orbs.get(i);

            if (Intersection.check(orb, player)) {
                player.playerStatus.takeXp(orb.getValue());
                remove(orb.getId());
            }
        }
    }

    public void remove(UUID id) {
        Iterator<Orb> iterator = orbs.iterator();
        while (iterator.hasNext()) {
            Orb orb = iterator.next();
            if (orb.getId().equals(id)) {
                iterator.remove();
            }
        }
    }

    public void renderOrbs(float deltaTime) {
        if (orbs.isEmpty()) {
            return;
        }

        for (int i = 0; i <= orbs.size()-1; i++) {
            Orb orb = orbs.get(i);
            orb.update(deltaTime);

            ShapeCanvas.drawShape(
                orb.getColor(),
                orb.getPosX() - camera.getPosX(),
                orb.getPosY() - camera.getPosY(),
                orb.getWidth(),
                orb.getHeight()
            );
        }
    }

    public void addEventListeners() {
        eventManager.on("orb:spawn", this::handle);
    }

    private void handle(Object... args) {
        int posX = (int) args[0];
        int posY = (int) args[1];
        float value = (float) args[2];

        spawnXpOrb(new Orb(posX, posY, (int) value));
    }
}
