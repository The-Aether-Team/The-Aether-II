package com.aetherteam.aetherii.block;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class AetherIIFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AetherII.MODID);

    public static final RegistryObject<FluidType> ALKAHEST_TYPE = FLUID_TYPES.register("alkahest", () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("block.aether_ii.alkahest")
                    .canExtinguish(false)
                    .supportsBoating(false)
                    .pathType(BlockPathTypes.DAMAGE_CAUTIOUS)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, AetherIISoundEvents.ITEM_ARKENIUM_CANISTER_FILL_ALKAHEST.get())
                    .sound(SoundActions.BUCKET_EMPTY, AetherIISoundEvents.ITEM_ARKENIUM_CANISTER_EMPTY_ALKAHEST.get())
                    .lightLevel(8)
            ) {
        @Override
        public boolean canDrownIn(LivingEntity entity) {
            return false;
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation(AetherII.MODID, "fluid/alkahest_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation(AetherII.MODID, "fluid/alkahest_flow");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return new ResourceLocation(AetherII.MODID, "fluid/alkahest_overlay");
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return new ResourceLocation(AetherII.MODID, "textures/misc/alkahest.png");
                }

                @Override
                public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    return new Vector3f(170 / 255.0F, 226 / 255.0F, 149 / 255.0F);
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTicks, float nearDistance, float farDistance, FogShape shape) {
                    RenderSystem.setShaderFogStart(0.0F);
                    RenderSystem.setShaderFogEnd(12.0F);
                }

                @Override
                public int getTintColor() {
                    return 0xFFFFFFFF;
                }
            });
        }
    });
}
