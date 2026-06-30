package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluidTypes;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class AetherIIClientExtensions {
    public static final AetherIIDyeableClientItemExtensions BEAST_PELT = new AetherIIDyeableClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return AetherIIDyeableClientItemExtensions.getColorOrDefault(stack, 0xFFCFEEF9);
        }
    };
    public static final AetherIIDyeableClientItemExtensions BURRUKAI_PLATE = new AetherIIDyeableClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return AetherIIDyeableClientItemExtensions.getColorOrDefault(stack, 0xFF619CC0);
        }
    };
    public static final AetherIIDyeableClientItemExtensions MOA_SADDLE = new AetherIIDyeableClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return AetherIIDyeableClientItemExtensions.getColorOrDefault(stack, 0xFF7D8BA3);
        }
    };

    public static final IClientItemExtensions THROWABLE = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
                return HumanoidModel.ArmPose.THROW_SPEAR;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            if (player.isUsingItem()) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate((float)i * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);

                boolean flag3 = arm == HumanoidArm.RIGHT;
                int k = flag3 ? 1 : -1;

                poseStack.translate((float)k * -0.2385682F, 0.28344387F, 0.15731531F);
//                poseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 35.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                float f8 = (float)itemInHand.getUseDuration() - ((float)player.getUseItemRemainingTicks() - partialTick + 1.0F);
                float f12 = f8 / 20.0F;
                f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                if (f12 > 1.0F) {
                    f12 = 1.0F;
                }

                if (f12 > 0.1F) {
                    float f15 = Mth.sin((f8 - 0.1F) * 1.25F);
                    float f18 = f12 - 0.1F;
                    float f20 = f15 * f18;
                    poseStack.translate(f20 * 0.0F, f20 * 0.004F, f20 * 0.0F);
                }

                poseStack.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
//                poseStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                poseStack.mulPose(Axis.YN.rotationDegrees((float)k * 45.0F));
                return true;
            }
            return false;
        }
    };

    public static final IClientItemExtensions DART_SHOOTER = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }
    };

    public static final IClientItemExtensions GLIDER = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
                return HumanoidModel.ArmPose.EMPTY;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }
    };

    public static final IClientBlockExtensions UNSTABLE_BLOCK = new IClientBlockExtensions() {
        public boolean playBreakSound(BlockState state, Level level, BlockPos pos) {
            return !level.getBlockState(pos.above()).isAir();
        }

        @Override
        public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
            VoxelShape voxelshape = state.getShape(level, pos);
            voxelshape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                double d1 = Math.min(1.0F, maxX - minX);
                double d2 = Math.min(1.0F, maxY - minY);
                double d3 = Math.min(1.0F, maxZ - minZ);
                int i = Math.max(2, Mth.ceil(d1 / (double) 0.25F));
                int j = Math.max(2, Mth.ceil(d2 / (double) 0.25F));
                int k = Math.max(2, Mth.ceil(d3 / (double) 0.25F));

                for (int l = 0; l < i; ++l) {
                    for (int i1 = 0; i1 < j; ++i1) {
                        for (int j1 = 0; j1 < k; ++j1) {
                            double d4 = ((double)l + (double)0.5F) / (double)i;
                            double d5 = ((double)i1 + (double)0.5F) / (double)j;
                            double d6 = ((double)j1 + (double)0.5F) / (double)k;
                            double d7 = d4 * d1 + minX;
                            double d8 = d5 * d2 + minY;
                            double d9 = d6 * d3 + minZ;
                            if (level.getRandom().nextInt(5) == 0) {
                                manager.add((new TerrainParticle((ClientLevel) level, (double) pos.getX() + d7, (double) pos.getY() + d8, (double) pos.getZ() + d9, d4 - (double) 0.5F, d5 - (double) 0.5F, d6 - (double) 0.5F, state, pos)).updateSprite(state, pos));
                            }
                        }
                    }
                }
            });
            return true;
        }
    };

    public static final IClientFluidTypeExtensions ALKAHEST_FLUID = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
            return new ResourceLocation(AetherII.MODID, "textures/misc/alkahest.png");
        }

        @Override
        public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
            return new Vector3f(170 / 255.0F, 226 / 255.0F, 149 / 255.0F);
        }

        @Override
        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
            RenderSystem.setShaderFogStart(0.0F);
            RenderSystem.setShaderFogEnd(12.0F);
        }
    };

    public static final IClientMobEffectExtensions HIDE_EFFECT = new IClientMobEffectExtensions() {
        @Override
        public boolean isVisibleInInventory(MobEffectInstance instance) {
            return false;
        }

        @Override
        public boolean isVisibleInGui(MobEffectInstance instance) {
            return false;
        }
    };

}
