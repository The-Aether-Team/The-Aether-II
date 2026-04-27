package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SentryTrapBlock;
import com.aetherteam.aetherii.block.natural.AetherLeavesBlock;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    @Inject(method = "forceOpaque(ZLnet/minecraft/world/level/block/state/BlockState;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void forceOpaque(boolean cutoutLeaves, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (!cutoutLeaves && blockState.getBlock() instanceof AetherLeavesBlock) {
            cir.setReturnValue(false);
        }
        if (!cutoutLeaves && AetherIIRenderers.isFastBlock(blockState)) {
            cir.setReturnValue(true);
        }
    }
}
