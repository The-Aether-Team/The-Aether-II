package com.aetherteam.aetherii.client.renderer;

import net.minecraftforge.fml.ModList;

public class ShaderCompatibility {
    private static Boolean irisLoaded = null;

    public static boolean areShadersActive() {
        if (irisLoaded == null) {
            irisLoaded = ModList.get().isLoaded("iris");
        }
        if (irisLoaded) {
            try {
                Class<?> irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
                Object irisApi = irisApiClass.getMethod("getInstance").invoke(null);
                return Boolean.TRUE.equals(irisApiClass.getMethod("isShaderPackInUse").invoke(irisApi));
            } catch (ReflectiveOperationException | LinkageError ignored) {
                return false;
            }
        }
        return false;
    }
}
