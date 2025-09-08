package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.mixin.wrappers.common.ItemCooldownsWrapper;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin implements ItemCooldownsWrapper {
    @Unique
    public Player player;

    @Inject(at = @At(value = "HEAD"), method = "onCooldownEnded(Lnet/minecraft/resources/ResourceLocation;)V")
    private void onCooldownEnded(ResourceLocation resourceLocation, CallbackInfo ci) {
        List<ResourceLocation> tagContents = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.TOOLS_SHIELD).spliterator(), false)
                .map(Holder::unwrapKey).filter(Optional::isPresent).map(optional -> optional.get().location()).toList();
        if (tagContents.contains(resourceLocation)) {
            if (!this.player.level().isClientSide()) {
                DamageSystemAttachment attachment = this.player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
                attachment.setSynched(this.player.getId(), INBTSynchable.Direction.CLIENT, "setShieldStamina", DamageSystemAttachment.MAX_SHIELD_STAMINA);
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
