package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.mixin.wrappers.common.ItemCooldownsWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin implements ItemCooldownsWrapper {
    @Unique
    public Player player;

    @Inject(at = @At(value = "HEAD"), method = "onCooldownEnded(Lnet/minecraft/world/item/Item;)V")
    private void onCooldownEnded(Item item, CallbackInfo ci) {
        if (item.builtInRegistryHolder().is(AetherIITags.Items.TOOLS_SHIELD)) {
            if (!this.player.level().isClientSide()) {
                DamageSystemAttachment attachment = AetherIIDataAttachments.get(player, AetherIIDataAttachments.DAMAGE_SYSTEM);
                attachment.setShieldEndurance(AetherIIAttributes.getMaxEndurance(this.player));
            }
        }
    }

    @Unique
    @Override
    public ItemCooldowns aether_ii$setPlayer(Player player) {
        this.player = player;
        return (ItemCooldowns) (Object) this;
    }
}
