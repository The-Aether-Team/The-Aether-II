package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.blockentity.AltarBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AltarResultSlot extends AbstractResultSlot {
    public AltarResultSlot(Player player, Container container, int slot, int x, int y) {
        super(player, container, slot, x, y);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player, this.removeCount);
        Player player = this.player;
        if (player instanceof ServerPlayer serverplayer) {
            Container container = this.container;
            if (container instanceof AltarBlockEntity altarBlockEntity) {
                altarBlockEntity.awardUsedRecipesAndPopExperience(serverplayer);
            }
        }
        this.removeCount = 0;
    }
}
