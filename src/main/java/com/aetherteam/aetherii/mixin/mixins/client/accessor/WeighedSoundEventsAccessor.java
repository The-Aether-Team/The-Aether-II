package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.Weighted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(WeighedSoundEvents.class)
public interface WeighedSoundEventsAccessor {
    @Accessor("list")
    List<Weighted<Sound>> aether_ii$getList();
}
