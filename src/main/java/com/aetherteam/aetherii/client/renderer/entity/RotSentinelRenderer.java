package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.RotSentinelModel;
import com.aetherteam.aetherii.entity.monster.dungeon.RotSentinel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class RotSentinelRenderer<T extends RotSentinel> extends MobRenderer<T, LivingEntityRenderState, RotSentinelModel<LivingEntityRenderState>> {
    private static final Identifier LOCATION = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/rot_sentinel/rot_sentinel.png");

    public RotSentinelRenderer(EntityRendererProvider.Context context) {
        super(context, new RotSentinelModel<>(context.bakeLayer(AetherIIModelLayers.ROT_SENTINEL)), 0.5F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(T bladeshroomHunter, LivingEntityRenderState renderState, float p_361157_) {
        super.extractRenderState(bladeshroomHunter, renderState, p_361157_);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState renderState) {
        return LOCATION;
    }
}
