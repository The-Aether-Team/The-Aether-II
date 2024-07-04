package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Kirrid;
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
import net.minecraft.util.FastColor;

public class KirridWoolLayer extends RenderLayer<Kirrid, EntityModel<Kirrid>> {
    private static final ResourceLocation ARCTIC_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_wool.png");
    private static final ResourceLocation ARCTIC_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/arctic/kirrid_arctic_baby_wool.png");
    private static final ResourceLocation HIGHFIELDS_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_wool.png");
    private static final ResourceLocation HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/highfields/kirrid_highfields_baby_wool.png");
    private static final ResourceLocation MAGNETIC_KIRRID_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_wool.png");
    private static final ResourceLocation MAGNETIC_KIRRID_BABY_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/kirrid/magnetic/kirrid_magnetic_baby_wool.png");

    public KirridWoolLayer(RenderLayerParent<Kirrid, EntityModel<Kirrid>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Kirrid kirrid, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (kirrid.isInvisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(kirrid);
            if (flag) {
                this.getParentModel().prepareMobModel(kirrid, limbSwing, limbSwingAmount, partialTick);
                this.getParentModel().setupAnim(kirrid, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.outline(this.getTexture(kirrid)));
                this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(kirrid, 0.0F), -16777216);
            }
        } else {
            int i = -1;
            if (kirrid.hasCustomName() && kirrid.getName().getString().equals("jeb_")) {
                int j = 25;
                int k = kirrid.tickCount / j + kirrid.getId();
                int l = Kirrid.KirridColor.values().length;
                int i1 = k % l;
                int j1 = (k + 1) % l;
                float f = ((float) (kirrid.tickCount % j) + partialTick) / j;
                int k1 = Kirrid.getDecimalColor(Kirrid.KirridColor.BY_ID.apply(i1));
                int l1 = Kirrid.getDecimalColor(Kirrid.KirridColor.BY_ID.apply(j1));
                i = FastColor.ARGB32.lerp(f, k1, l1);
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), this.getTexture(kirrid), poseStack, bufferSource, packedLight, kirrid, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, i);
            } else if (kirrid.getColor().isPresent()) {
                i = Kirrid.getDecimalColor(kirrid.getColor().get());
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), this.getTexture(kirrid), poseStack, bufferSource, packedLight, kirrid, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTick, i);
            }
        }
    }

    private ResourceLocation getTexture(Kirrid kirrid) {
        if (kirrid.getType() == AetherIIEntityTypes.ARCTIC_KIRRID.get()) {
            return kirrid.isBaby() ? ARCTIC_KIRRID_BABY_WOOL_TEXTURE : ARCTIC_KIRRID_WOOL_TEXTURE;
        } else if (kirrid.getType() == AetherIIEntityTypes.MAGNETIC_KIRRID.get()) {
            return kirrid.isBaby() ? MAGNETIC_KIRRID_BABY_WOOL_TEXTURE : MAGNETIC_KIRRID_WOOL_TEXTURE;
        } else {
            return kirrid.isBaby() ? HIGHFIELDS_KIRRID_BABY_WOOL_TEXTURE : HIGHFIELDS_KIRRID_WOOL_TEXTURE;
        }
    }
}
