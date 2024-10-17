package com.aetherteam.aetherii.item.consumables;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.effect.buildup.EffectBuildupPresets;
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

import java.util.function.Supplier;

public class TeaItem extends Item {
    private final UseAnim useAnimation;
    private final int useDuration;
    private final Supplier<ItemStack> remainderItem;
    private final EffectBuildupPresets.Preset preset;

    public TeaItem(UseAnim useAnimation, int useDuration, EffectBuildupPresets.Preset preset, Properties properties) {
        this(useAnimation, useDuration, () -> ItemStack.EMPTY, preset, properties);
    }

    public TeaItem(UseAnim useAnimation, int useDuration, Supplier<ItemStack> remainderItem, EffectBuildupPresets.Preset preset, Properties properties) {
        super(properties);
        this.useAnimation = useAnimation;
        this.useDuration = useDuration;
        this.remainderItem = remainderItem;
        this.preset = preset;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
        assert player != null;
        player.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).addBuildup(preset, 1000);

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }
        if (!this.remainderItem.get().isEmpty()) {
            if (!player.hasInfiniteMaterials()) {
                if (stack.isEmpty()) {
                    return this.remainderItem.get();
                }
                player.getInventory().add(this.remainderItem.get());
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
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