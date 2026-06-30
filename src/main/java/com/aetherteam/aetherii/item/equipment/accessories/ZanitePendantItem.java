package com.aetherteam.aetherii.item.equipment.accessories;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

import java.util.Set;

public class ZanitePendantItem extends AccessoryItem {
    private static final ResourceLocation MINING_EFFICIENCY = new ResourceLocation(AetherII.MODID, "accessory.ability.zanite_pendant.mining_efficiency");

    public ZanitePendantItem(Properties properties) {
        super(properties.durability(250), AccessoryContainer.SlotType.ACCESSORY);
    }

    @Override
    public Set<ConditionalAttribute> gatherAttributes(Set<ConditionalAttribute> attributes) {
        attributes = super.gatherAttributes(attributes);
        attributes.add(new ConditionalAttribute(AetherIIAttributes.MINING_EFFICIENCY, new ConditionalModifier(MINING_EFFICIENCY, (stack) -> 0.25 + (1.75 * stack.getDamageValue() / (stack.getMaxDamage() + 0.5)), AttributeModifier.Operation.ADDITION), (stack, wearer) -> true));
        return attributes;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment) {
        return false;
    }

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        LevelAccessor level = event.getLevel();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();

        if (!event.isCanceled()) {
            if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
                AccessoryUtil.getFirst(player, AccessoryContainer.SlotType.ACCESSORY).ifPresent((stack) -> {
                    if (stack.is(AetherIIItems.ZANITE_PENDANT.get())) {
                        if (state.getDestroySpeed(level, pos) > 0 && player.getRandom().nextInt(6) == 0) {
                            ItemStack copyStack = stack.copy();
                            stack.hurtAndBreak(1, player, entity -> AccessoryUtil.breakAccessory(copyStack.getItem(), copyStack, serverPlayer));
                        }
                    }
                });
            }
        }
    }
}
