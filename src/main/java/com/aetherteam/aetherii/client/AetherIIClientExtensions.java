package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.AetherIIFluidTypes;
import com.aetherteam.aetherii.client.extensions.SaddlebagClientItemExtensions;
import com.aetherteam.aetherii.client.renderer.AetherIIModelLayers;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaLargeSaddlebagModel;
import com.aetherteam.aetherii.client.renderer.entity.model.MoaSaddlebagModel;
import com.aetherteam.aetherii.client.renderer.entity.state.MoaRenderState;
import com.aetherteam.aetherii.data.resources.registries.AetherIIEquipmentAssets;
import com.aetherteam.aetherii.effect.AetherIIMobEffects;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.extensions.common.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class AetherIIClientExtensions {
    public static final IClientItemExtensions BEAST_PELT = new IClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return DyedItemColor.getOrDefault(stack, 0xFFCFEEF9);
        }
    };
    public static final IClientItemExtensions BURRUKAI_PLATE = new IClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return DyedItemColor.getOrDefault(stack, 0xFF619CC0);
        }
    };
    public static final IClientItemExtensions MOA_SADDLE = new IClientItemExtensions() {
        @Override
        public int getDefaultDyeColor(ItemStack stack) {
            return DyedItemColor.getOrDefault(stack, 0xFF7D8BA3);
        }
    };
    public static final IClientItemExtensions MOA_SADDLEBAG = new SaddlebagClientItemExtensions() {
        @Override
        public ResourceKey<EquipmentAsset> getSaddlebagAsset(ItemStack itemStack) {
            return AetherIIEquipmentAssets.MOA_SADDLEBAG;
        }

        @Override
        public EntityModel<? super MoaRenderState> getSaddlebagModel(ItemStack itemStack) {
            return new MoaSaddlebagModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.MOA_SADDLEBAG));
        }
    };
    public static final IClientItemExtensions LARGE_MOA_SADDLEBAG = new SaddlebagClientItemExtensions() {
        @Override
        public ResourceKey<EquipmentAsset> getSaddlebagAsset(ItemStack itemStack) {
            return AetherIIEquipmentAssets.LARGE_MOA_SADDLEBAG;
        }

        @Override
        public EntityModel<? super MoaRenderState> getSaddlebagModel(ItemStack itemStack) {
            return new MoaLargeSaddlebagModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherIIModelLayers.MOA_LARGE_SADDLEBAG));
        }
    };

    public static final IClientItemExtensions THROWABLE = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
                return HumanoidModel.ArmPose.THROW_TRIDENT;
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

    public static final IClientItemExtensions DART_SHOOTER = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
                return AetherIIArmPoses.DART_SHOOTER;
            }
            return IClientItemExtensions.super.getArmPose(entityLiving, hand, itemStack);
        }
    };

    public static final IClientItemExtensions GLIDER = new IClientItemExtensions() {
        @Nullable
        @Override
        public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getItemInHand(hand).is(itemStack.getItem()) && entityLiving.isUsingItem()) {
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
        public Identifier getRenderOverlayTexture(Minecraft mc) {
            return Identifier.fromNamespaceAndPath(AetherII.MODID, "textures/misc/alkahest.png");
        }

        @Override
        public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            new Vector4f(170 / 255.0F, 226 / 255.0F, 149 / 255.0F, 1F);
        }

        @Override
        public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
            fogData.environmentalStart = 0F;
            fogData.environmentalEnd = 12.0F;
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

    public static void registerClientItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(BEAST_PELT, AetherIIItems.BEAST_PELT_HELMET.get(), AetherIIItems.BEAST_PELT_CHESTPLATE.get(), AetherIIItems.BEAST_PELT_LEGGINGS.get(), AetherIIItems.BEAST_PELT_BOOTS.get(), AetherIIItems.BEAST_PELT_GLOVES.get());
        event.registerItem(BURRUKAI_PLATE, AetherIIItems.BURRUKAI_PLATE_HELMET.get(), AetherIIItems.BURRUKAI_PLATE_CHESTPLATE.get(), AetherIIItems.BURRUKAI_PLATE_LEGGINGS.get(), AetherIIItems.BURRUKAI_PLATE_BOOTS.get(), AetherIIItems.BURRUKAI_PLATE_GLOVES.get());
        event.registerItem(MOA_SADDLE, AetherIIItems.MOA_SADDLE);
        event.registerItem(MOA_SADDLEBAG, AetherIIItems.MOA_SADDLEBAG);
        event.registerItem(LARGE_MOA_SADDLEBAG, AetherIIItems.LARGE_MOA_SADDLEBAG);
        event.registerItem(THROWABLE, AetherIIBlocks.HOLYSTONE_ROCK.asItem(), AetherIIItems.PRISMALLARD_EGG.get(), AetherIIItems.SKYROOT_PINECONE.get(), AetherIIItems.ARCTIC_SNOWBALL.get(), AetherIIItems.BRETTL_LASSO.get());
        event.registerItem(DART_SHOOTER, AetherIIItems.DART_SHOOTER);
        event.registerItem(GLIDER, AetherIIItems.COLD_AERCLOUD_GLIDER, AetherIIItems.GOLDEN_AERCLOUD_GLIDER, AetherIIItems.BLUE_AERCLOUD_GLIDER, AetherIIItems.PURPLE_AERCLOUD_GLIDER);

        event.registerBlock(UNSTABLE_BLOCK, AetherIIBlocks.UNSTABLE_HOLYSTONE.get(), AetherIIBlocks.UNSTABLE_UNDERSHALE.get(), AetherIIBlocks.FRAGILE_ARCTIC_ICE.get(), AetherIIBlocks.UNSTABLE_GUARDIAN_ROOTS.get());

        event.registerFluidType(ALKAHEST_FLUID, AetherIIFluidTypes.ALKAHEST_TYPE.get());

        event.registerMobEffect(HIDE_EFFECT, AetherIIMobEffects.NATURAL_CAMOUFLAGE.get(), AetherIIMobEffects.ELECTRIC_SHOCK.get(), AetherIIMobEffects.CARRION_TRAP.get(), AetherIIMobEffects.GRAVITATIONAL_PULL.get(), AetherIIMobEffects.HEALING_OVERFLOW.get());
    }
}
