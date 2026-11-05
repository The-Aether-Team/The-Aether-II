package com.aetherteam.aetherii.client.renderer.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.SkyrootChestMimicModel;
import com.aetherteam.aetherii.client.renderer.entity.state.SkyrootChestMimicRenderState;
import com.aetherteam.aetherii.entity.monster.dungeon.Mimic;
import com.aetherteam.aetherii.entity.monster.dungeon.SkyrootChestMimic;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class SkyrootChestMimicRenderer extends MobRenderer<SkyrootChestMimic, SkyrootChestMimicRenderState, SkyrootChestMimicModel<SkyrootChestMimicRenderState>> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/mimic/skyroot_chest_mimic.png");

    public SkyrootChestMimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new SkyrootChestMimicModel<>(renderer.bakeLayer(AetherIIModelLayers.SKYROOT_CHEST_MIMIC)), 1.0F);

    }

    @Override
    public SkyrootChestMimicRenderState createRenderState() {
        return new SkyrootChestMimicRenderState();
    }

    @Override
    public void extractRenderState(SkyrootChestMimic mimic, SkyrootChestMimicRenderState skyrootChestMimicRenderState, float particalTick) {
        super.extractRenderState(mimic, skyrootChestMimicRenderState, particalTick);
    }

    /**
     * If the Lootr mod is installed or if it is Christmas, Mimics will have a custom texture.
     *
     * @param Mimic The {@link Mimic} entity.
     * @return The texture {@link }.
     */
    @Override
    public Identifier getTextureLocation(SkyrootChestMimicRenderState Mimic) {
        return TEXTURE;
    }
}

