package com.aetherteam.aetherii.inventory.menu.slot;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.inventory.menu.ArkeniumForgeMenu;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ForgeCharmSlot extends Slot {
    private final ArkeniumForgeMenu menu;
    private final int charmIndex;
    private Charms.Type charmType;
    private Charms.Tier charmTier;
    private boolean isLocked;

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
                if (this.getItem().isEmpty() && !charmHolder.getStack().isEmpty()) {
                    this.set(charmHolder.getStack());
                    if (!this.isLocked()) {
                        this.setLocked(true);
                    }
                }
                return true;
            }
        } else {
            this.charmType = null;
            this.charmTier = null;
            if (this.isLocked()) {
                this.setLocked(false);
            }
        }
        return false;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !this.isLocked() && this.isActive() && stack.getItem() instanceof CharmItem charmItem && charmItem.getType() == this.charmType && charmItem.getTier().getValue() <= this.charmTier.getValue();
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

    public Charms.Type getCharmType() {
        return this.charmType;
    }

    public Charms.Tier getCharmTier() {
        return this.charmTier;
    }

    @Nullable
    @Override
    public ResourceLocation getNoItemIcon() {
        if (this.charmType != null && this.charmTier != null) {
            return ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_" + this.charmType.name().toLowerCase(Locale.ROOT) + "_charm_" + this.charmTier.getValue());
        }
        return null;
    }
}
