package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ForgeCharmSlot extends Slot {
    private final ArkeniumForgeMenu menu;
    private final int charmIndex;
    private Charms.Type charmType;
    private Charms.Tier charmTier;

    public ForgeCharmSlot(ArkeniumForgeMenu menu, Container container, int slot, int x, int y, int charmIndex) {
        super(container, slot, x, y);
        this.menu = menu;
        this.charmIndex = charmIndex;
    }

    @Override
    public boolean isActive() {
        ItemStack input = this.menu.getInput();
        if (!input.isEmpty()) {
            Charms.CharmHolder charmHolder = Charms.getCharmHolderForItem(input, this.charmIndex);
            if (charmHolder != null) {
                this.charmType = charmHolder.getType();
                this.charmTier = charmHolder.getTier();
                return true;
            }
        } else {
            this.charmType = null;
            this.charmTier = null;
        }
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !this.isLocked(this.menu.getInput()) && this.isActive() && stack.getItem() instanceof CharmItem charmItem && charmItem.getType() == this.charmType && charmItem.getTier().getValue() <= this.charmTier.getValue();
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.isLocked(this.menu.getInput()) && this.isActive();
    }

    @Override
    public boolean allowModification(Player player) {
        return !this.isLocked(this.menu.getInput()) && this.isActive();
    }

    public boolean isLocked(ItemStack input) {
        boolean flag = false;
        if (!input.isEmpty()) {
            Charms.CharmHolder charmHolder = Charms.getCharmHolderForItem(input, this.charmIndex);
            if (charmHolder != null) {
                if (!charmHolder.getStack().isEmpty()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public int getCharmIndex() {
        return this.charmIndex;
    }

    public Charms.Type getCharmType() {
        return this.charmType;
    }

    public Charms.Tier getCharmTier() {
        return this.charmTier;
    }

    @Nullable
    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        if (this.charmType != null && this.charmTier != null) {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, new ResourceLocation(AetherII.MODID, "container/arkenium_forge/slot_" + this.charmType.name().toLowerCase(Locale.ROOT) + "_charm_" + this.charmTier.getValue()));
        }
        return null;
    }
}
