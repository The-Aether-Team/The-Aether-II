package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.DamageSystemAttachment;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.mixin.wrappers.common.ItemCooldownsWrapper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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

    @Inject(at = @At(value = "HEAD"), method = "onCooldownEnded(Lnet/minecraft/resources/Identifier;)V")
    private void onCooldownEnded(Identifier resourceLocation, CallbackInfo ci) {
        List<Identifier> tagContents = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(Tags.Items.TOOLS_SHIELD).spliterator(), false)
                .map(Holder::unwrapKey).filter(Optional::isPresent).map(optional -> optional.get().location()).toList();
        if (tagContents.contains(resourceLocation)) {
            if (!this.player.level().isClientSide()) {
                DamageSystemAttachment attachment = this.player.getData(AetherIIDataAttachments.DAMAGE_SYSTEM);
                attachment.setShieldEndurance(AetherIIAttributes.getMaxEndurance(this.player));
                this.player.syncData(AetherIIDataAttachments.DAMAGE_SYSTEM);
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
