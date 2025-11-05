package com.aetherteam.aetherii.item.equipment;

import com.aetherteam.aetherii.integration.AccessoryUtil;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AccessoryItem extends Item {
    private final AccessoryContainer.SlotType slotType;

    public AccessoryItem(Properties properties, AccessoryContainer.SlotType slotType) {
        super(properties);
        this.slotType = slotType;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return AccessoryUtil.equip(player, player.getItemInHand(hand), this.getSlotType());
    }

    public void onEquip(ItemStack stack, LivingEntity wearer) {

    }

    public void onUnequip(ItemStack stack, LivingEntity wearer) {

    }

    public AccessoryContainer.SlotType getSlotType() {
        return this.slotType;
    }
}
