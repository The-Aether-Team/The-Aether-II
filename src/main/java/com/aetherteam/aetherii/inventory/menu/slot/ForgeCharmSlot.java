package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ForgeCharmSlot extends Slot {
    public static final ResourceLocation SLOT_CHARM = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gui/slot/container/arkenium_forge/slot_charm");
    private final ArkeniumForgeMenu menu;
    private final int charmIndex;
    private boolean isLocked;

    public ForgeCharmSlot(ArkeniumForgeMenu menu, Container container, int slot, int x, int y, int charmIndex) {
        super(container, slot, x, y);
        this.menu = menu;
        this.charmIndex = charmIndex;
    }

    @Override
    public boolean isActive() {
        if (!this.menu.getInput().isEmpty()) {
            List<ItemStack> charms = this.menu.getInput().get(AetherIIDataComponents.CHARMS);
            if (charms != null) {
                ItemStack charm = charms.get(this.charmIndex);
                if (this.getItem().isEmpty() && !charm.isEmpty()) {
                    this.set(charm);
                    this.setLocked(true);
                }
            }
            if (!this.isLocked()) {
                ReinforcementTier tier = this.menu.getInput().get(AetherIIDataComponents.REINFORCEMENT_TIER);
                if (tier != null) {
                    return this.charmIndex < tier.getCharmSlots();
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.menu.isCharm(stack) && this.isActive();
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.isLocked() && this.isActive();
    }

    @Override
    public boolean allowModification(Player player) {
        return !this.isLocked() && this.isActive();
    }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public int getCharmIndex() {
        return this.charmIndex;
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_CHARM);
    }
}
