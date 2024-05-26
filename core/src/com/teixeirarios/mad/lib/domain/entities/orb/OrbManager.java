package com.teixeirarios.mad.lib.domain.entities.orb;


import com.teixeirarios.mad.lib.domain.entities.player.Player;
import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.events.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.UUID;

public class OrbManager {

    private static OrbManager instance;
    EventManager eventManager;
    ArrayList<Orb> orbs;
    Player player;
    Camera camera;
    AbstractCanvasFacade canvas;

    public OrbManager(AbstractCanvasFacade canvas) {
        this.player = Player.getInstance();
        this.camera = Camera.getInstance();
        this.orbs = new ArrayList<>();
        this.canvas = canvas;

        eventManager = EventManager.getInstance();
        addEventListeners();
    }

    public static OrbManager getInstance(AbstractCanvasFacade canvas) {
        if (instance == null) {
            instance = new OrbManager(canvas);
        }
        return instance;
    }

   private void spawnXpOrb(Orb orb)  {
        this.orbs.add(orb);
        this.sortOrbs();
    }

    public void sortOrbs() {
        Collections.sort(orbs, new Comparator<Orb>() {
            @Override
            public int compare(Orb a, Orb b) {
                double distanceToA = Math.sqrt(Math.pow(player.getPosX() - a.getPosX(), 2) + Math.pow(player.getPosY() - a.getPosY(), 2));
                double distanceToB = Math.sqrt(Math.pow(player.getPosX() - b.getPosX(), 2) + Math.pow(player.getPosY() - b.getPosY(), 2));
                return Double.compare(distanceToA, distanceToB);
            }
        });
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

    public void renderOrbs() {
        if (orbs.isEmpty()) {
            return;
        }

        for (int i = 0; i <= orbs.size()-1; i++) {
            Orb orb = orbs.get(i);

            canvas.drawShape(
                orb.color,
                orb.getPosX() - camera.getPosX(),
                orb.getPosY() - camera.getPosY(),
                orb.width,
                orb.height
            );
        }
    }

    public void addEventListeners() {
        eventManager.on("orb:spawn", this::handle);
    }

    private void handle(Object... args) {
        int posX = (int) args[0];
        int posY = (int) args[1];
        int value = (int) args[2];

        spawnXpOrb(new Orb(posX, posY, value));
    }
}
