package com.aetherteam.aetherii.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AetherIIMixinPlugin implements IMixinConfigPlugin {
    private boolean isOptiFineInstalled = false;
    private boolean isSodiumInstalled = false;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            Class.forName("optifine.Installer", false, getClass().getClassLoader());
            isOptiFineInstalled = true;
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("net.caffeinemc.mods.sodium.client.SodiumClientMod", false, getClass().getClassLoader());
            isSodiumInstalled = true;
        } catch (ClassNotFoundException ignored) {
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("com.aetherteam.aetherii.mixin.mixins.client.sodium.ChunkBuilderMeshingTaskMixin")
                || mixinClassName.equals("com.aetherteam.aetherii.mixin.mixins.client.sodium.DefaultFluidRendererMixin")) {
            return isSodiumInstalled;
        }

        if (this.isOptiFineInstalled) {
            if (mixinClassName.equals("com.aetherteam.aether.mixin.mixins.client.BossHealthOverlayMixin")) return false;
            if (mixinClassName.equals("com.aetherteam.aether.mixin.mixins.client.optifine.BossHealthOverlayMixin")) return true;
        }

        return !mixinClassName.equals("com.aetherteam.aether.mixin.mixins.client.optifine.BossHealthOverlayMixin");
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
