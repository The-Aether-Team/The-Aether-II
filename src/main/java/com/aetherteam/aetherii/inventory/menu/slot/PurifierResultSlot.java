package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.blockentity.AlkahestPurifierBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PurifierResultSlot extends AbstractResultSlot {
    public PurifierResultSlot(Player player, Container container, int slot, int x, int y) {
        super(player, container, slot, x, y);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        Player player = this.player;
        if (player instanceof ServerPlayer serverplayer) {
            Container container = this.container;
            if (container instanceof AlkahestPurifierBlockEntity alkahestPurifierBlockEntity) {
                alkahestPurifierBlockEntity.awardUsedRecipesAndPopExperience(serverplayer);
            }
        }
        this.removeCount = 0;
    }
}
