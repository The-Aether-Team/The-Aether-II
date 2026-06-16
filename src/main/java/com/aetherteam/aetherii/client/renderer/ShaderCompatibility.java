package com.aetherteam.aetherii.client.renderer;

import net.irisshaders.iris.api.v0.IrisApi;
import net.neoforged.fml.ModList;

public class ShaderCompatibility {
    private static Boolean irisLoaded = null;

    public static boolean areShadersActive() {
        if (irisLoaded == null) {
            irisLoaded = ModList.get().isLoaded("iris");
        }
        if (irisLoaded) {
            return IrisApi.getInstance().isShaderPackInUse();
        }
        return false;
    }
}
