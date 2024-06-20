package com.teixeirarios.mad.lib.domain.entities.orb;

import com.teixeirarios.mad.lib.infra.camera.Camera;

public class OrbManagerFactory {
    public static OrbManager create (Camera camera) {
        return new OrbManager(camera);
    }
}
