package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.state.KirridRenderState;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class KirridWoolLayer extends RenderLayer<KirridRenderState, EntityModel<KirridRenderState>> {
    private static final ResourceLocation ARCTIC_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_wool.png");
    private static final ResourceLocation ARCTIC_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_baby_wool.png");
    private static final ResourceLocation HIGHFIELDS_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_wool.png");
    private static final ResourceLocation HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby_wool.png");
    private static final ResourceLocation MAGNETIC_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_wool.png");
    private static final ResourceLocation MAGNETIC_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_baby_wool.png");

    public KirridWoolLayer(RenderLayerParent<KirridRenderState, EntityModel<KirridRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, KirridRenderState kirrid, float netHeadYaw, float headPitch) {
        if (kirrid.isInvisible) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = kirrid.appearsGlowing;
            if (flag) {
                this.getParentModel().setupAnim(kirrid);
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.outline(this.getTexture(kirrid)));
                this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(kirrid, 0.0F), -16777216);
            }
        } else {
            int i = -1;
            if (kirrid.customName != null && "jeb_".equals(kirrid.customName.getString())) {
                int j = 25;
                int k = Mth.floor(kirrid.ageInTicks);
                int l = k / 25 + kirrid.id;
                int i1 = DyeColor.values().length;
                int j1 = l % i1;
                int k1 = (l + 1) % i1;
                float f = ((float) (k % 25) + Mth.frac(kirrid.ageInTicks)) / 25.0F;
                int l1 = Sheep.getColor(DyeColor.byId(j1));
                int i2 = Sheep.getColor(DyeColor.byId(k1));
                i = ARGB.lerp(f, l1, i2);
            } else {
                i = Sheep.getColor(kirrid.woolColor);
            }
        }
    }

    private ResourceLocation getTexture(KirridRenderState kirrid) {
        if (kirrid.entityType == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
            return kirrid.isBaby ? ARCTIC_KIRRID_BABY_WOOL_TEXTURE : ARCTIC_KIRRID_WOOL_TEXTURE;
        } else if (kirrid.entityType == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
            return kirrid.isBaby ? MAGNETIC_KIRRID_BABY_WOOL_TEXTURE : MAGNETIC_KIRRID_WOOL_TEXTURE;
        } else {
            return kirrid.isBaby ? HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE : HIGHFIELDS_KIRRID_WOOL_TEXTURE;
        }
    }
}
