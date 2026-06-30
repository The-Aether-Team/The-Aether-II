package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {
    @WrapOperation(method = "dropLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private ItemEntity dropLeash(Mob instance, ItemLike item, Operation<ItemEntity> original) {
        if (AetherIIDataAttachments.get(instance, AetherIIDataAttachments.LASSO_CONNECTION)) {
            AetherIIDataAttachments.set(instance, AetherIIDataAttachments.LASSO_CONNECTION, false);
            instance.level().playSound(null, instance.getX(), instance.getY(), instance.getZ(), SoundEvents.LEASH_KNOT_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            return null;
        }
        return original.call(instance, item);
    }

    @WrapOperation(method = "maybeDisableShield(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean maybeDisableShield(ItemStack stack, Item item, Operation<Boolean> original) {
        return original.call(stack, item) || isShield(stack);
    }

    @WrapOperation(method = "maybeDisableShield(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private void maybeDisableShield(ItemCooldowns itemCooldowns, Item item, int duration, Operation<Void> original, Player player, ItemStack weapon, ItemStack useItem) {
        original.call(itemCooldowns, isShield(useItem) ? useItem.getItem() : item, duration);
    }

    private static boolean isShield(ItemStack stack) {
        return stack.getItem() instanceof ShieldItem || stack.is(AetherIITags.Items.TOOLS_SHIELD);
    }
}
