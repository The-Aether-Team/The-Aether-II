package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluidTypes;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class AetherIIClientExtensions {
    public static final IClientItemExtensions TAEGORE_HIDE = new IClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, 0xCFEEF9) : -1;
        }
    };
    public static final IClientItemExtensions BURRUKAI_PELT = new IClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, 0x619CC0) : -1;
        }
    };

    public static final IClientItemExtensions THROWABLE = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return HumanoidModel.ArmPose.THROW_SPEAR;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }

        @Override
        public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                poseStack.translate((float)i * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);

                boolean flag3 = arm == HumanoidArm.RIGHT;
                int k = flag3 ? 1 : -1;

                poseStack.translate((float)k * -0.2385682F, 0.28344387F, 0.15731531F);
//                poseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float)k * 35.3F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float)k * -9.785F));
                float f8 = (float)itemInHand.getUseDuration(player) - ((float)player.getUseItemRemainingTicks() - partialTick + 1.0F);
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

    public static final IClientItemExtensions GLIDER = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return AetherIIArmPoses.GLIDING;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }
    };

    public static final IClientBlockExtensions UNSTABLE_BLOCK = new IClientBlockExtensions() {
        @Override
        public boolean playBreakSound(BlockState state, Level level, BlockPos pos) {
            return !level.getBlockState(pos.above()).isAir();
        }
    };

    public static final IClientFluidTypeExtensions ACID_FLUID = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "fluid/acid_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "fluid/acid_flow");
        }

        @Override
        public ResourceLocation getOverlayTexture() {
            return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "fluid/acid_overlay");
        }

        @Override
        public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
            return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "textures/misc/acid.png");
        }

        @Override
        public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            return new Vector4f(170 / 255.0F, 226 / 255.0F, 149 / 255.0F, 1F);
        }

        @Override
        public FogParameters modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, FogParameters fogParameters) {
            return new FogParameters(0.0F, 12.0F, fogParameters.shape(), fogParameters.red(), fogParameters.green(), fogParameters.blue(), fogParameters.alpha());
        }

        @Override
        public int getTintColor() {
            return 0xFFFFFFFF;
        }
    };

    public static void registerClientItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(TAEGORE_HIDE, AetherIIItems.TAEGORE_HIDE_HELMET.get(), AetherIIItems.TAEGORE_HIDE_CHESTPLATE.get(), AetherIIItems.TAEGORE_HIDE_LEGGINGS.get(), AetherIIItems.TAEGORE_HIDE_BOOTS.get(), AetherIIItems.TAEGORE_HIDE_GLOVES.get());
        event.registerItem(BURRUKAI_PELT, AetherIIItems.BURRUKAI_PELT_HELMET.get(), AetherIIItems.BURRUKAI_PELT_CHESTPLATE.get(), AetherIIItems.BURRUKAI_PELT_LEGGINGS.get(), AetherIIItems.BURRUKAI_PELT_BOOTS.get(), AetherIIItems.BURRUKAI_PELT_GLOVES.get());
        event.registerItem(THROWABLE, AetherIIBlocks.HOLYSTONE_ROCK.asItem(), AetherIIItems.SKYROOT_PINECONE.get(), AetherIIItems.ARCTIC_SNOWBALL.get());
        event.registerItem(GLIDER, AetherIIItems.COLD_AERCLOUD_GLIDER, AetherIIItems.GOLDEN_AERCLOUD_GLIDER, AetherIIItems.BLUE_AERCLOUD_GLIDER, AetherIIItems.PURPLE_AERCLOUD_GLIDER);

        event.registerBlock(UNSTABLE_BLOCK, AetherIIBlocks.UNSTABLE_HOLYSTONE.get(),AetherIIBlocks.UNSTABLE_UNDERSHALE.get());

        event.registerFluidType(ACID_FLUID, AetherIIFluidTypes.ACID_TYPE.get());
    }
}
