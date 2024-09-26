package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SkephidModel;
import com.aetherteam.aetherii.entity.monster.Skephid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class SkephidEmissiveLayer<T extends Skephid> extends EyesLayer<T, SkephidModel<T>> {
    private static final RenderType EYE = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/skephid/skephid_eyes.png"));

    public SkephidEmissiveLayer(RenderLayerParent<T, SkephidModel<T>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return EYE;
    }
}
