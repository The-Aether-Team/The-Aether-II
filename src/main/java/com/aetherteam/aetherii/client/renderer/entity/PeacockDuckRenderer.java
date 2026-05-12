package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.PeacockDuckModel;
import com.aetherteam.aetherii.client.renderer.entity.state.PeacockDuckRenderState;
import com.aetherteam.aetherii.entity.passive.PeacockDuck;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PeacockDuckRenderer<T extends PeacockDuck> extends MobRenderer<T, PeacockDuckRenderState, PeacockDuckModel<PeacockDuckRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/peacock_duck/peacock_duck.png");

    public PeacockDuckRenderer(EntityRendererProvider.Context context) {
        super(context, new PeacockDuckModel<>(context.bakeLayer(AetherIIModelLayers.PEACOCK_DUCK)), 0.35F);
    }

    @Override
    public Identifier getTextureLocation(PeacockDuckRenderState state) {
        return LOCATION;
    }

    @Override
    public PeacockDuckRenderState createRenderState() {
        return new PeacockDuckRenderState();
    }

    @Override
    public void extractRenderState(T entity, PeacockDuckRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);

    }
}
