package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.advancement.trigger.AetherIIAdvancementTriggers;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.aetherteam.aetherii.data.resources.registries.AetherIIItemReinforcements;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.aetherteam.aetherii.network.packet.clientbound.ForgeSoundPacket;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.aetherteam.aetherii.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    public static final ResourceLocation SLOT_PRIMARY = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/slot_primary");
    public static final ResourceLocation SLOT_SECONDARY = new ResourceLocation(AetherII.MODID, "container/arkenium_forge/slot_secondary");
    private final Container container;
    @Nullable
    private String itemName;
    private Consumer<ItemStack> inputUpdater = (input) -> {};

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(11));
    }

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory, Container container) {
        super(AetherIIMenuTypes.ARKENIUM_FORGE.get(), containerId);
        this.container = container;

        this.addSlot(new Slot(this.container, 0, 29, 65) {
            @Override
            public void setChanged() {
                if (!this.getItem().isEmpty()) {
                    for (Slot slot : ArkeniumForgeMenu.this.slots) {
                        if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                            Charms.CharmHolder charmHolder = Charms.getCharmHolderForItem(this.getItem(), forgeCharmSlot.getCharmIndex());
                            if (charmHolder != null) {
                                if (!charmHolder.getStack().isEmpty()) {
                                    forgeCharmSlot.set(charmHolder.getStack());
                                }
                            }
                        }
                    }
                }
                super.setChanged();
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                ArkeniumForgeMenu.this.resetCharmSlots(player, stack);
                super.onTake(player, stack);
            }
        });

        this.addSlot(new Slot(this.container, 1, 69, 153) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isPrimaryMaterial(stack);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_PRIMARY);
            }
        });
        this.addSlot(new Slot(this.container, 2, 91, 153) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isSecondaryMaterial(stack);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_SECONDARY);
            }
        });

        int index = 3;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 2; i++) {
                this.addSlot(new ForgeCharmSlot(this, this.container, index, 54 + (52 * i), 39 + (17 * j), index - 3));
                index++;
            }
        }

        this.addStandardInventorySlots(playerInventory, 8, 173);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (slotIndex == 0) {
                this.resetCharmSlots(player, slotStack);
            }
            if (slotIndex > 10) {
                if (this.isPrimaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isSecondaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isCharm(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 3, 11, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 11 && slotIndex < 47) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 11 && slotIndex < 38) {
                    if (!this.moveItemStackTo(slotStack, 38, 47, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 38 && slotIndex < 47 && !this.moveItemStackTo(slotStack, 11, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 11, 47, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return itemStack;
    }

    private void resetCharmSlots(Player player, ItemStack stack) {
        for (Slot otherSlots : ArkeniumForgeMenu.this.slots) {
            if (otherSlots instanceof ForgeCharmSlot forgeCharmSlot) {
                if (!forgeCharmSlot.getItem().isEmpty()) {
                    if (forgeCharmSlot.isLocked(stack)) {
                        forgeCharmSlot.set(ItemStack.EMPTY);
                    } else {
                        ArkeniumForgeMenu.this.quickMoveStack(player, forgeCharmSlot.index);
                    }
                }
            }
        }
    }

    public boolean upgradeItem(RegistryAccess registryAccess, ReinforcementTier tierToUpgradeTo) {
        if (tierToUpgradeTo != null) {
            int tierNumberToUpgradeTo = tierToUpgradeTo.getTierNumber();
            ItemStack input = this.getInput();
            ReinforcementTier currentReinforcementTier = AetherIIDataComponents.get(input, AetherIIDataComponents.REINFORCEMENT_TIER);

            if (!ReinforcementTier.isItemAtMaxTier(registryAccess, input) && ((currentReinforcementTier == null && tierNumberToUpgradeTo > 0) || (currentReinforcementTier != null && tierNumberToUpgradeTo > currentReinforcementTier.getTierNumber()))) {
                if (!input.isEmpty()) {
                    int primaryCost = ReinforcementTier.getPrimaryCostForTier(registryAccess, input, tierNumberToUpgradeTo);
                    int secondaryCost = ReinforcementTier.getSecondaryCostForTier(registryAccess, input, tierNumberToUpgradeTo);
                    if (primaryCost != -1 && secondaryCost != -1) {
                        ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, input);
                        if (reinforcement != null) {
                            input = reinforcement.modify(input, tierNumberToUpgradeTo).copy();
                            this.inputUpdater.accept(input);
                            this.getPrimaryMaterial().shrink(primaryCost);
                            this.getSecondaryMaterial().shrink(secondaryCost);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean slotCharms(Player player) {
        return this.replaceCharms(player, this.getInput(), true);
    }

    public boolean replaceCharms(Player player, ItemStack stack, boolean lock) {
        boolean flag = false;
        List<Charms.CharmHolder> charmHolders = Charms.getCharmsForItem(stack);
        Charms newCharms = new Charms();
        if (charmHolders != null) {
            for (Slot slot : this.slots) {
                if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                    Charms.CharmHolder charmHolder = Charms.getCharmHolderForItem(stack, forgeCharmSlot.getCharmIndex());
                    if (charmHolder != null) {
                        if (forgeCharmSlot.isLocked(stack)) {
                            newCharms.charmHolders().add(forgeCharmSlot.getCharmIndex(), new Charms.CharmHolder(charmHolder));
                        } else {
                            newCharms.charmHolders().add(forgeCharmSlot.getCharmIndex(), new Charms.CharmHolder(charmHolder, slot.getItem()));
                            if (!slot.getItem().isEmpty() && lock) {
                                if (player instanceof ServerPlayer serverPlayer) {
                                    AetherIIAdvancementTriggers.FORGING_CHARM.get().trigger(serverPlayer, slot.getItem());
                                }
                            }
                        }
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            AetherIIDataComponents.set(stack, AetherIIDataComponents.CHARMS, newCharms);
        }
        return flag;
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(0).hasItem()) {
                ItemStack itemstack = this.getSlot(0).getItem();
                if (StringUtil.isNullOrEmpty(s)) {
                    itemstack.resetHoverName();
                } else {
                    itemstack.setHoverName(Component.literal(s));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void playSound() {
        if (this.container instanceof ArkeniumForgeBlockEntity blockEntity) {
            PacketDistributor.sendToAllPlayers(new ForgeSoundPacket(blockEntity.getBlockPos()));
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.stripColor(itemName).trim();
        return s.length() <= 50 ? s : null;
    }

    public ItemStack getInput() {
        return this.container.getItem(0);
    }

    public ItemStack getPrimaryMaterial() {
        return this.container.getItem(1);
    }

    public ItemStack getSecondaryMaterial() {
        return this.container.getItem(2);
    }

    public boolean isPrimaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_PRIMARY_MATERIAL);
    }

    public boolean isSecondaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_SECONDARY_MATERIAL);
    }

    public boolean isCharm(ItemStack itemStack) {
        return itemStack.getItem() instanceof CharmItem;
    }

    public int getTierForMaterials(RegistryAccess registryAccess) {
        ItemStack input = this.getInput();
        if (!ReinforcementTier.isItemAtMaxTier(registryAccess, input)) {
            ItemReinforcement reinforcement = AetherIIItemReinforcements.get(registryAccess, input);
            if (reinforcement != null) {
                for (int i = reinforcement.upgrades().length - 1; i >= 0; i--) {
                    ItemReinforcement.Cost cost = reinforcement.upgrades()[i].cost();
                    boolean primaryMaterial = this.getPrimaryMaterial().is(cost.primaryCost().item()) && this.getPrimaryMaterial().getCount() >= ReinforcementTier.getPrimaryCostForTier(registryAccess, input, i + 1);
                    boolean secondaryMaterial = cost.secondaryCost().isEmpty() || (this.getSecondaryMaterial().is(cost.secondaryCost().get().item()) && this.getSecondaryMaterial().getCount() >= ReinforcementTier.getSecondaryCostForTier(registryAccess, input, i + 1));
                    if (primaryMaterial && secondaryMaterial) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    public boolean hasNewCharms() {
        for (Slot slot : this.slots) {
            if (slot instanceof ForgeCharmSlot charmSlot && !charmSlot.isLocked(this.getInput()) && !charmSlot.getItem().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void registerUpdater(Consumer<ItemStack> updater) {
        this.inputUpdater = updater;
    }

    private void addStandardInventorySlots(Inventory playerInventory, int leftCol, int topRow) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, leftCol + column * 18, topRow + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, leftCol + column * 18, topRow + 58));
        }
    }
}
