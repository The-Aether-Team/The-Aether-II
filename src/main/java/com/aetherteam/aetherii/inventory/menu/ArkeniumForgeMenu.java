package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.aetherteam.aetherii.item.AetherIIDataComponents;
import com.aetherteam.aetherii.item.ReinforcementTier;
import com.aetherteam.aetherii.network.packet.clientbound.ForgeSoundPacket;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    private final Container container;
    private final Player player;
    @Nullable
    private String itemName;

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(3));
    }

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory, Container container) {
        super(AetherIIMenuTypes.ARKENIUM_FORGE.get(), containerId);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.container, 0, 29, 65));

        this.addSlot(new Slot(this.container, 1, 69, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isPrimaryMaterial(stack);
            }
        });
        this.addSlot(new Slot(this.container, 2, 91, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isSecondaryMaterial(stack);
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
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (slotIndex > 2) {
                if (this.isEquipment(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isPrimaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isSecondaryMaterial(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 3 && slotIndex < 30) {
                    if (!this.moveItemStackTo(slotStack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 30 && slotIndex < 39 && !this.moveItemStackTo(slotStack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 3, 39, false)) {
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

    public boolean upgradeItem() {
        int tierToUpgradeTo = this.getTierForMaterials();
        ItemStack input = this.getInput();
        ReinforcementTier reinforcementTier = input.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (!this.isItemAtMaxTier() && ((reinforcementTier == null && tierToUpgradeTo > 0) || (reinforcementTier != null && tierToUpgradeTo > reinforcementTier.getTier()))) {
            if (!input.isEmpty()) {
                ReinforcementTier tier = ReinforcementTier.values()[tierToUpgradeTo - 1];
                int primaryCost = this.getPrimaryCostForTier(tierToUpgradeTo);
                int secondaryCost = this.getSecondaryCostForTier(tierToUpgradeTo);
                if (tier != null && primaryCost != -1 && secondaryCost != -1) {
                    input.set(AetherIIDataComponents.REINFORCEMENT_TIER, tier);
                    input.set(DataComponents.MAX_DAMAGE, input.getMaxDamage() + tier.getExtraDurability());
                    this.getPrimaryMaterial().shrink(primaryCost);
                    this.getSecondaryMaterial().shrink(secondaryCost);
                    return true;
                }
            }
        }
        return false;
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

    public void playSound() {
        if (this.container instanceof ArkeniumForgeBlockEntity blockEntity) {
            PacketDistributor.sendToAllPlayers(new ForgeSoundPacket(blockEntity.getBlockPos()));
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
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

    public Map<Integer, ReinforcementTier.Cost> getCosts() {
        Map<Integer, ReinforcementTier.Cost> costs = new HashMap<>();
        for (ReinforcementTier tier : ReinforcementTier.values()) {
            int value = tier.getTier();
            ReinforcementTier.Cost cost = tier.getCost(this.getInput());
            if (costs != null) {
                costs.put(value, cost);
            }
        }
        return costs;
    }

    public int getTierCount() {
        return this.getCosts().size();
    }

    public boolean isItemAtMaxTier() {
        int max = this.getTierCount();
        ReinforcementTier tier = this.getInput().get(AetherIIDataComponents.REINFORCEMENT_TIER);
        return tier != null && tier.getTier() == max;
    }

    public int getTierForItem() {
        int max = this.getTierCount();
        ReinforcementTier tier = this.getInput().get(AetherIIDataComponents.REINFORCEMENT_TIER);
        if (tier != null) {
            return Math.min(tier.getTier(), max);
        } else {
            return 0;
        }
    }

    @Nullable
    public ReinforcementTier.Cost getCostForTier(int tier) {
        return this.getCosts().getOrDefault(tier, null);
    }

    public int getPrimaryCostForTier(int tier) {
        ReinforcementTier.Cost initialCost = this.getCostForTier(tier);
        if (initialCost != null) {
            int cost = initialCost.primaryCount();
            int minimumTier = this.getTierForItem();
            for (int i = tier - 1; i > minimumTier; i--) {
                ReinforcementTier.Cost costForTier = this.getCostForTier(i);
                if (costForTier != null) {
                    cost += costForTier.primaryCount();
                }
            }
            return cost;
        }
        return -1;
    }

    public int getSecondaryCostForTier(int tier) {
        ReinforcementTier.Cost initialCost = this.getCostForTier(tier);
        if (initialCost != null) {
            int cost = initialCost.secondaryCount();
            int minimumTier = this.getTierForItem();
            for (int i = tier - 1; i > minimumTier; i--) {
                ReinforcementTier.Cost costForTier = this.getCostForTier(i);
                if (costForTier != null) {
                    cost += costForTier.secondaryCount();
                }
            }
            return cost;
        }
        return -1;
    }

    public boolean isEquipment(ItemStack itemStack) {
        return !itemStack.isStackable() && itemStack.getCount() == 1 && itemStack.has(DataComponents.MAX_DAMAGE);
    }

    public boolean isPrimaryMaterial(ItemStack material) {
        for (ReinforcementTier.Cost cost : this.getCosts().values()) {
            if (cost != null && material.is(cost.primaryMaterial().asItem())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSecondaryMaterial(ItemStack material) {
        for (ReinforcementTier.Cost cost : this.getCosts().values()) {
            if (cost != null && material.is(cost.secondaryMaterial().asItem())) {
                return true;
            }
        }
        return false;
    }

    public int getTierForMaterials() {
        if (!this.isItemAtMaxTier()) {
            List<ReinforcementTier.Cost> costs = new ArrayList<>(this.getCosts().values());
            for (int i = this.getTierCount() - 1; i >= 0; i--) {
                ReinforcementTier.Cost cost = costs.get(i);
                if (this.getPrimaryMaterial().is(cost.primaryMaterial().asItem()) && this.getPrimaryMaterial().getCount() >= this.getPrimaryCostForTier(i + 1)
                        && this.getSecondaryMaterial().is(cost.secondaryMaterial().asItem()) && this.getSecondaryMaterial().getCount() >= this.getSecondaryCostForTier(i + 1)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }
}
