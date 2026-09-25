package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SawcapSlingerModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SawcapSlingerRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.SawcapSlinger;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class SawcapSlingerRenderer<T extends SawcapSlinger> extends MobRenderer<T, SawcapSlingerRenderState, SawcapSlingerModel<SawcapSlingerRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sawcap_slinger/sawcap_slinger.png");

    public SawcapSlingerRenderer(EntityRendererProvider.Context context) {
        super(context, new SawcapSlingerModel<>(context.bakeLayer(AetherIIModelLayers.SAWCAP_SLINGER)), 0.35F);
    }

    @Override
    public SawcapSlingerRenderState createRenderState() {
        return new SawcapSlingerRenderState();
    }

    @Override
    public void extractRenderState(T entity, SawcapSlingerRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        renderState.rotate = entity.getRotateAnimationScale(partialTicks);
    }

    @Override
    public Identifier getTextureLocation(SawcapSlingerRenderState renderState) {
        return LOCATION;
    }
}
