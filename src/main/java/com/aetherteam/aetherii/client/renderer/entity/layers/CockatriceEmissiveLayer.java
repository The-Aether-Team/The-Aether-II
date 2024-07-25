package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aetherii.entity.monster.Cockatrice;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class CockatriceEmissiveLayer extends EyesLayer<Cockatrice, CockatriceModel> {
    private static final RenderType COCKATRICE_MARKINGS = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive.png"));

    public CockatriceEmissiveLayer(RenderLayerParent<Cockatrice, CockatriceModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return COCKATRICE_MARKINGS;
    }
}
