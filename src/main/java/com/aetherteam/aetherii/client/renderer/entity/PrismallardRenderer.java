package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PrismallardModel;
import com.aetherteam.aetherii.client.renderer.entity.state.PrismallardRenderState;
import com.aetherteam.aetherii.entity.passive.Prismallard;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PrismallardRenderer<T extends Prismallard> extends MobRenderer<T, PrismallardRenderState, PrismallardModel<PrismallardRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/prismallard/prismallard.png");

    public PrismallardRenderer(EntityRendererProvider.Context context) {
        super(context, new PrismallardModel<>(context.bakeLayer(AetherIIModelLayers.PRISMALLARD)), 0.35F);
    }

    @Override
    public Identifier getTextureLocation(PrismallardRenderState state) {
        return LOCATION;
    }

    @Override
    public PrismallardRenderState createRenderState() {
        return new PrismallardRenderState();
    }

    @Override
    public void extractRenderState(T entity, PrismallardRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        state.featherScale = entity.getDisplayAnimationScale(partialTicks);
    }
}
