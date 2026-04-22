package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.SentryTrapBlock;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
//    @ModifyArgs(method = "putQuadData(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;Lnet/minecraft/client/renderer/block/ModelBlockRenderer$CommonRenderStorage;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFFF[IIZ)V"))
//    private void putQuadData(Args args, @Local(argsOnly = true) BlockState state) {
//        if (state.is(AetherIIBlocks.LOCKED_BLOCK) || state.is(AetherIIBlocks.BOSS_DOORWAY_BLOCK) || state.is(AetherIIBlocks.TREASURE_DOORWAY_BLOCK) || (state.is(AetherIIBlocks.SENTRY_TRAP) && state.getValue(SentryTrapBlock.LOCKED))) {
//            float red = args.get(3);
//            float green = args.get(4);
//            float blue = args.get(5);
//            args.set(3, red * 0.75F);
//            args.set(4, green * 0.75F);
//            args.set(5, blue * 0.75F);
//        }
//    } //todo

//    @WrapOperation(method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/block/model/BlockStateModel;FFFIILnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;renderQuadList(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFLjava/util/List;II)V"))
//    private static void renderQuadList(PoseStack.Pose pose, VertexConsumer consumer, float red, float green, float blue, List<BakedQuad> quads, int packedLight, int packedOverlay, Operation<Void> original, @Local(argsOnly = true) BlockState state) {
//        if (state.is(AetherIIBlocks.LOCKED_BLOCK) || state.is(AetherIIBlocks.BOSS_DOORWAY_BLOCK) || state.is(AetherIIBlocks.TREASURE_DOORWAY_BLOCK) || (state.is(AetherIIBlocks.SENTRY_TRAP) && state.getValue(SentryTrapBlock.LOCKED))) {
//            for (BakedQuad bakedquad : quads) {
//                consumer.putBulkData(pose, bakedquad, red * 0.75F, green * 0.75F, blue * 0.75F, 1.0F, packedLight, packedOverlay);
//            }
//        } else {
//            original.call(pose, consumer, red, green, blue, quads, packedLight, packedOverlay);
//        }
//    }


    @Inject(method = "forceOpaque(ZLnet/minecraft/world/level/block/state/BlockState;)Z", at = @At(value = "HEAD"), cancellable = true)
    private static void forceOpaque(boolean cutoutLeaves, BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (!cutoutLeaves && AetherIIRenderers.isFastBlock(blockState)) {
            cir.setReturnValue(true);
        }
    }
}
