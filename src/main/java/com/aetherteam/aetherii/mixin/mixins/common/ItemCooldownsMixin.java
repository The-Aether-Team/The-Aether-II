package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.mixin.wrappers.common.ItemCooldownsWrapper;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin implements ItemCooldownsWrapper {
    @Unique
    public Player player;

    @Inject(at = @At(value = "HEAD"), method = "onCooldownEnded(Lnet/minecraft/resources/ResourceLocation;)V") //todo itemstack?
    private void onCooldownEnded(ResourceLocation p_366721_, CallbackInfo ci) {
//        if (item.getDefaultInstance().is(Tags.Items.TOOLS_SHIELD)) {
//            if (!this.player.level().isClientSide()) {
//                DamageSystemAttachment attachment = this.player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
//                attachment.setSynched(this.player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", DamageSystemAttachment.MAX_SHIELD_STAMINA);
//            }
//        }
    }

    @Unique
    @Override
    public ItemCooldowns aether_ii$setPlayer(Player player) {
        this.player = player;
        return (ItemCooldowns) (Object) this;
    }
}
