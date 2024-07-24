package com.aetherteam.aetherii.client.renderer.entity.layers;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

/**
 * [CODE COPY] - {@link net.minecraft.client.renderer.entity.layers.SheepFurLayer}.
 */
public class SheepuffWoolLayer extends RenderLayer<Sheepuff, SheepuffModel<Sheepuff>> {
    private static final ResourceLocation SHEEPUFF_WOOL_TEXTURE = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/entity/mobs/sheepuff/sheepuff_wool.png");

    public SheepuffWoolLayer(RenderLayerParent<Sheepuff, SheepuffModel<Sheepuff>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (sheepuff.isInvisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(sheepuff);
            if (flag) {
                this.getParentModel().prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
                this.getParentModel().setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer consumer = buffer.getBuffer(RenderType.outline(SHEEPUFF_WOOL_TEXTURE));
                this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), -16777216);
            }
        } else {
            int i = -1;
            if (sheepuff.hasCustomName() && sheepuff.getName().getString().equals("jeb_")) {
                int j = 25;
                int k = sheepuff.tickCount / j + sheepuff.getId();
                int l = Sheepuff.SheepuffColor.values().length;
                int i1 = k % l;
                int j1 = (k + 1) % l;
                float f = ((float) (sheepuff.tickCount % j) + partialTicks) / j;
                int k1 = Sheepuff.getDecimalColor(Sheepuff.SheepuffColor.BY_ID.apply(i1));
                int l1 = Sheepuff.getDecimalColor(Sheepuff.SheepuffColor.BY_ID.apply(j1));
                i = FastColor.ARGB32.lerp(f, k1, l1);
            } else {
                i = Sheepuff.getDecimalColor(sheepuff.getColor());
            }
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.getParentModel(), SHEEPUFF_WOOL_TEXTURE, poseStack, buffer, packedLight, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, i);
        }
    }
}
