package com.teixeirarios.mad.lib.domain.entities.orb;

import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.camera.Camera;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class OrbManagerFactory {
    public static OrbManager create (Camera camera) {
        AbstractCanvasFacade canvas = new CanvasFacade();
        return new OrbManager(canvas, camera);
    }
}
