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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Supplier;

public class PreventativeItem extends Item {
    private final UseAnim useAnimation;
    private final int useDuration;
    private final Supplier<ItemStack> remainderItem;
    private final Map<Holder<MobEffect>, Integer> preventativeMap;

    public PreventativeItem(UseAnim useAnimation, int useDuration, Map<Holder<MobEffect>, Integer> preventativeMap, Properties properties) {
        this(useAnimation, useDuration, () -> ItemStack.EMPTY, preventativeMap, properties);
    }

    public PreventativeItem(UseAnim useAnimation, int useDuration, Supplier<ItemStack> remainderItem, Map<Holder<MobEffect>, Integer> preventativeMap, Properties properties) {
        super(properties);
        this.useAnimation = useAnimation;
        this.useDuration = useDuration;
        this.remainderItem = remainderItem;
        this.preventativeMap = preventativeMap;
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

        for (Map.Entry<Holder<MobEffect>, Integer> entry : this.preventativeMap.entrySet()) {
            entityLiving.getData(AetherIIDataAttachments.EFFECTS_SYSTEM).reduceBuildup(entry.getKey(), entry.getValue());
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
