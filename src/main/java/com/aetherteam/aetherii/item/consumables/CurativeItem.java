package com.aetherteam.aetherii.item.consumables;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCure;

import java.util.function.Supplier;

public class CurativeItem extends Item {
    private final UseAnim useAnimation;
    private final int useDuration;
    private final Supplier<ItemStack> remainderItem;
    private final EffectCure effectCure;

    public CurativeItem(UseAnim useAnimation, int useDuration, EffectCure effectCure, Properties properties) {
        this(useAnimation, useDuration, () -> ItemStack.EMPTY, effectCure, properties);
    }

    public CurativeItem(UseAnim useAnimation, int useDuration, Supplier<ItemStack> remainderItem, EffectCure effectCure, Properties properties) {
        super(properties);
        this.useAnimation = useAnimation;
        this.useDuration = useDuration;
        this.remainderItem = remainderItem;
        this.effectCure = effectCure;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.consume(1, player);
        }

        if (!this.remainderItem.get().isEmpty()) {
            if (player == null || !player.hasInfiniteMaterials()) {
                if (stack.isEmpty()) {
                    return this.remainderItem.get();
                }
                if (player != null) {
                    player.getInventory().add(this.remainderItem.get());
                }
            }
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