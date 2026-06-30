package com.aetherteam.aetherii.item.consumeeffect;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public record ReduceStatusEffectConsumeEffect(Map<Holder<MobEffect>, Integer> effects) implements ConsumeEffect {
    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {
        boolean flag = false;
        for (Map.Entry<Holder<MobEffect>, Integer> entry : this.effects().entrySet()) {
            AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM).reduceBuildup(entry.getKey(), entry.getValue());
            flag = true;
        }
        return flag;
    }
}
