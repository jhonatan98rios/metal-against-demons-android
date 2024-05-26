package com.teixeirarios.mad.lib.domain.entities.orb;

import com.teixeirarios.mad.lib.drivers.facade.AbstractCanvasFacade;
import com.teixeirarios.mad.lib.infra.facade.CanvasFacade;

public class OrbManagerFactory {
    public static OrbManager create () {
        AbstractCanvasFacade canvas = new CanvasFacade();
        return OrbManager.getInstance(canvas);
    }
}
