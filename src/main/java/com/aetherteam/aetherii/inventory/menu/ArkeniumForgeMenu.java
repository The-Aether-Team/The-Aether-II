package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Player player;
    public final Container equipmentContainer = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            ArkeniumForgeMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    public final Container materialsContainer = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            ArkeniumForgeMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    @Nullable
    private String itemName;

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(AetherIIMenuTypes.ARKENIUM_FORGE.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.equipmentContainer, 0, 29, 65));

        this.addSlot(new Slot(this.materialsContainer, 0, 69, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(AetherIIItems.ARKENIUM_PLATES);
            }
        });
        this.addSlot(new Slot(this.materialsContainer, 1, 91, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(AetherIIItems.INERT_GRAVITITE); //todo placeholder
            }
        });

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 169 + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 227));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, AetherIIBlocks.ARKENIUM_FORGE.get());
    }

//    @Override
//    public void slotsChanged(Container inventory) { //todo
//        ItemStack itemstack = this.materialsContainer.getItem(0);
//        ItemStack itemstack1 = this.materialsContainer.getItem(1);
//        ItemStack itemstack2 = this.resultContainer.getItem(2);
//        if (itemstack2.isEmpty() || !itemstack.isEmpty() && !itemstack1.isEmpty()) {
//            if (!itemstack.isEmpty() && !itemstack1.isEmpty()) {
//                this.setupResultSlot(itemstack, itemstack1, itemstack2);
//            }
//        } else {
//            this.resultContainer.removeItemNoUpdate(2);
//        }
//    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null; //todo
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.equipmentContainer));
        this.access.execute((level, pos) -> this.clearContainer(player, this.materialsContainer));
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(0).hasItem()) {
                ItemStack itemstack = this.getSlot(0).getItem();
                if (StringUtil.isBlank(s)) {
                    itemstack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
        return s.length() <= 50 ? s : null;
    }
}
