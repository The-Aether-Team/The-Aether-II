package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class KirridWoolLayer extends RenderLayer<KirridRenderState, EntityModel<KirridRenderState>> {
    private static final Identifier ARCTIC_KIRRID_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_wool.png");
    private static final Identifier ARCTIC_KIRRID_BABY_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_baby_wool.png");
    private static final Identifier HIGHFIELDS_KIRRID_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_wool.png");
    private static final Identifier HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby_wool.png");
    private static final Identifier MAGNETIC_KIRRID_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_wool.png");
    private static final Identifier MAGNETIC_KIRRID_BABY_WOOL_TEXTURE = Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_baby_wool.png");

    public KirridWoolLayer(RenderLayerParent<KirridRenderState, EntityModel<KirridRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, KirridRenderState kirrid, float v, float v1) {
        if (kirrid.isInvisible) {
            if (kirrid.appearsGlowing()) {
                submitNodeCollector.submitModel(this.getParentModel(), kirrid, poseStack, RenderTypes.outline(this.getTexture(kirrid)), packedLight, LivingEntityRenderer.getOverlayCoords(kirrid, 0.0F), -16777216, null);
            }
        } else {
            kirrid.woolColor.ifPresent((woolColor) -> {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getTexture(kirrid), poseStack, submitNodeCollector, packedLight, kirrid, woolColor, 1);
            });
        }
    }

    private Identifier getTexture(KirridRenderState kirrid) {
        if (kirrid.entityType == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
            return kirrid.isBaby ? ARCTIC_KIRRID_BABY_WOOL_TEXTURE : ARCTIC_KIRRID_WOOL_TEXTURE;
        } else if (kirrid.entityType == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
            return kirrid.isBaby ? MAGNETIC_KIRRID_BABY_WOOL_TEXTURE : MAGNETIC_KIRRID_WOOL_TEXTURE;
        } else {
            return kirrid.isBaby ? HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE : HIGHFIELDS_KIRRID_WOOL_TEXTURE;
        }
    }
}
