package com.aetherteam.aetherii.mixin.mixins.client;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.renderer.AetherIIRenderTypes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BlockModelWrapper.class)
public class BlockModelWrapperMixin {
    @Shadow
    @Final
    private ModelRenderProperties properties;
    @Shadow
    @Final
    private List<BakedQuad> quads;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setExtents(Ljava/util/function/Supplier;)V"))
    public void update(ItemStackRenderState itemStackRenderState, ItemStack stack, ItemModelResolver p_388726_, ItemDisplayContext context, ClientLevel p_387522_, ItemOwner p_434975_, int p_388300_, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layerRenderState) {

        //Is it should be not *direct* put?
        if (!stack.hasFoil() && stack.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            ItemStackRenderState.LayerRenderState itemstackrenderstate$layerrenderstate = itemStackRenderState.newLayer();
            itemStackRenderState.setAnimated();
            itemstackrenderstate$layerrenderstate.setRenderType(AetherIIRenderTypes.irradiatedGlint());
            properties.applyToLayer(itemstackrenderstate$layerrenderstate, context);
            itemstackrenderstate$layerrenderstate.prepareQuadList().addAll(this.quads);
        }
    }

    /*
      @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;setExtents(Ljava/util/function/Supplier;)V"))
      private static void update(ItemStackRenderState itemStackRenderState, ItemStack stack, ItemModelResolver modelResolver, ItemDisplayContext context, ClientLevel clientLevel, LivingEntity living, int i, CallbackInfo ci, @Local ItemStackRenderState.LayerRenderState layerRenderState) {
        if (!stack.hasFoil() && stack.is(AetherIITags.Items.IRRADIATED_ITEM)) {
            ((LayerRenderStateWrapper) layerRenderState).aether_ii$setIrradiated(true);
            itemStackRenderState.setAnimated();
        } else {
            ((LayerRenderStateWrapper) layerRenderState).aether_ii$setIrradiated(false);
        }
      }
     */
}
