package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.Set;

public class ZanitePendantItem extends AccessoryItem {
    private static final Identifier MINING_EFFICIENCY = Identifier.fromNamespaceAndPath(AetherII.MODID, "accessory.ability.zanite_pendant.mining_efficiency");

    public ZanitePendantItem(Properties properties) {
        super(properties.durability(250), AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(Attributes.MINING_EFFICIENCY, new ConditionalModifier(MINING_EFFICIENCY, (stack) -> 0.25 + (1.75 * stack.getDamageValue() / (stack.getMaxDamage() + 0.5)), AttributeModifier.Operation.ADD_VALUE), (stack, wearer) -> true));
        return attributes;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return false;
    }

    public static void onBlockBreak(BreakBlockEvent event) {
        Player player = event.getPlayer();
        LevelAccessor level = event.getLevel();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();

        if (!event.isCanceled()) {
            if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                AccessoryUtil.getFirst(player, AccessoryContainer.SlotType.ACCESSORY).ifPresent((stack) -> {
                    if (stack.is(AetherIIItems.ZANITE_PENDANT)) {
                        if (state.getDestroySpeed(level, pos) > 0 && player.getRandom().nextInt(6) == 0) {
                            ItemStack copyStack = stack.copy();
                            stack.hurtAndBreak(1, serverLevel, player, item -> AccessoryUtil.breakAccessory(item, copyStack, serverPlayer));
                        }
                    }
                });
            }
        }
    }
}
