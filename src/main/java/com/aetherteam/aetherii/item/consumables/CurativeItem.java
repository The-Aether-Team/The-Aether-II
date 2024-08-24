package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Map;

public class CurativeItem extends Item {
    private final UseAnim useAnimation;
    private final int useDuration;
    private final EffectCure effectCure;

    public CurativeItem(UseAnim useAnimation, int useDuration, EffectCure effectCure, Properties properties) {
        super(properties);
        this.useAnimation = useAnimation;
        this.useDuration = useDuration;
        this.effectCure = effectCure;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        super.finishUsingItem(stack, level, entityLiving);
        if (entityLiving instanceof ServerPlayer serverplayer) {
            if (!serverplayer.getAbilities().instabuild) {
                stack.shrink(1);
            }
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide()) {
            entityLiving.removeEffectsCuredBy(this.effectCure);
        }

        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return this.useAnimation;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return this.useDuration;
    }
}