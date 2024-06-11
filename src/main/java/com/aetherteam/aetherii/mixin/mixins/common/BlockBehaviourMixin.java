package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @ModifyVariable(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/storage/loot/LootParams$Builder;)Ljava/util/List;", at = @At(value = "HEAD"), argsOnly = true)
    private LootParams.Builder getDrops(LootParams.Builder params) {
        if (params.getParameter(LootContextParams.TOOL).is(AetherIIItems.ARKENIUM_SHEARS)) {
            return params.withParameter(LootContextParams.TOOL, new ItemStack(Items.SHEARS));
        }
        return params;
    }
}
