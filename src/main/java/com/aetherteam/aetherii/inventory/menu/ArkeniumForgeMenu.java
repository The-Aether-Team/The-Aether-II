package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.network.packet.clientbound.ForgeSoundPacket;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    public static final List<Holder<Item>> REINFORCEABLE = List.of(
            AetherIIItems.SKYROOT_SHORTSWORD,
            AetherIIItems.SKYROOT_HAMMER,
            AetherIIItems.SKYROOT_SPEAR,
            AetherIIItems.SKYROOT_SHIELD,
            AetherIIItems.HOLYSTONE_SHORTSWORD,
            AetherIIItems.HOLYSTONE_HAMMER,
            AetherIIItems.HOLYSTONE_SPEAR,
            AetherIIItems.HOLYSTONE_SHIELD,
            AetherIIItems.ZANITE_SHORTSWORD,
            AetherIIItems.ZANITE_HAMMER,
            AetherIIItems.ZANITE_SPEAR,
            AetherIIItems.ZANITE_SHIELD,
            AetherIIItems.ARKENIUM_SHORTSWORD,
            AetherIIItems.ARKENIUM_HAMMER,
            AetherIIItems.ARKENIUM_SPEAR,
            AetherIIItems.ARKENIUM_SHIELD,
            AetherIIItems.GRAVITITE_SHORTSWORD,
            AetherIIItems.GRAVITITE_HAMMER,
            AetherIIItems.GRAVITITE_SPEAR,
            AetherIIItems.GRAVITITE_SHIELD,
            AetherIIItems.SKYROOT_AXE,
            AetherIIItems.HOLYSTONE_AXE,
            AetherIIItems.ZANITE_AXE,
            AetherIIItems.ARKENIUM_AXE,
            AetherIIItems.GRAVITITE_AXE,
            AetherIIItems.SKYROOT_PICKAXE,
            AetherIIItems.HOLYSTONE_PICKAXE,
            AetherIIItems.ZANITE_PICKAXE,
            AetherIIItems.ARKENIUM_PICKAXE,
            AetherIIItems.GRAVITITE_PICKAXE,
            AetherIIItems.SKYROOT_SHOVEL,
            AetherIIItems.HOLYSTONE_SHOVEL,
            AetherIIItems.ZANITE_SHOVEL,
            AetherIIItems.ARKENIUM_SHOVEL,
            AetherIIItems.GRAVITITE_SHOVEL,
            AetherIIItems.SKYROOT_TROWEL,
            AetherIIItems.HOLYSTONE_TROWEL,
            AetherIIItems.ZANITE_TROWEL,
            AetherIIItems.ARKENIUM_TROWEL,
            AetherIIItems.GRAVITITE_TROWEL,
            AetherIIItems.TAEGORE_HIDE_HELMET,
            AetherIIItems.BURRUKAI_PELT_HELMET,
            AetherIIItems.ZANITE_HELMET,
            AetherIIItems.ARKENIUM_HELMET,
            AetherIIItems.GRAVITITE_HELMET,
            AetherIIItems.TAEGORE_HIDE_CHESTPLATE,
            AetherIIItems.BURRUKAI_PELT_CHESTPLATE,
            AetherIIItems.ZANITE_CHESTPLATE,
            AetherIIItems.ARKENIUM_CHESTPLATE,
            AetherIIItems.GRAVITITE_CHESTPLATE,
            AetherIIItems.TAEGORE_HIDE_LEGGINGS,
            AetherIIItems.BURRUKAI_PELT_LEGGINGS,
            AetherIIItems.ZANITE_LEGGINGS,
            AetherIIItems.ARKENIUM_LEGGINGS,
            AetherIIItems.GRAVITITE_LEGGINGS,
            AetherIIItems.TAEGORE_HIDE_BOOTS,
            AetherIIItems.BURRUKAI_PELT_BOOTS,
            AetherIIItems.ZANITE_BOOTS,
            AetherIIItems.ARKENIUM_BOOTS,
            AetherIIItems.GRAVITITE_BOOTS,
            AetherIIItems.TAEGORE_HIDE_GLOVES,
            AetherIIItems.BURRUKAI_PELT_GLOVES,
            AetherIIItems.ZANITE_GLOVES,
            AetherIIItems.ARKENIUM_GLOVES,
            AetherIIItems.GRAVITITE_GLOVES);
    public static final ResourceLocation SLOT_PRIMARY = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gui/slot/container/arkenium_forge/slot_primary");
    public static final ResourceLocation SLOT_SECONDARY = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "gui/slot/container/arkenium_forge/slot_secondary");
    private final Container container;
    private final Player player;
    @Nullable
    private String itemName;

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(11));
    }

    public ArkeniumForgeMenu(int containerId, Inventory playerInventory, Container container) {
        super(AetherIIMenuTypes.ARKENIUM_FORGE.get(), containerId);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(this.container, 0, 29, 65) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isEquipment(stack);
            }

            @Override
            public void setChanged() {
                super.setChanged();
                ArkeniumForgeMenu.this.changeInput();
            }
        });

        this.addSlot(new Slot(this.container, 1, 69, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isPrimaryMaterial(stack);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, SLOT_PRIMARY);
            }
        });
        this.addSlot(new Slot(this.container, 2, 91, 149) {
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
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                this.addSlot(new ForgeCharmSlot(this, this.container, index, 54 + (52 * i), 39 + (17 * j), index - 3));
                index++;
            }
        }

        for (int k = 0; k < 3; k++) {
            for (int i1 = 0; i1 < 9; i1++) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 169 + k * 18));
            }
        }

        for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 227));
        }
    }

    public void changeInput() {
        ItemStack input = this.getInput();
        if (input.isEmpty()) {
            for (Slot slot : this.slots) {
                if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                    if (!forgeCharmSlot.getItem().isEmpty()) {
                        if (forgeCharmSlot.isLocked()) {
                            forgeCharmSlot.set(ItemStack.EMPTY);
                        } else {
                            this.quickMoveStack(this.player, forgeCharmSlot.index);
                        }
                    }
                }
            }
        } else {
            if (input.get(AetherIIDataComponents.CHARMS) == null) { //todo better handling?
                input.set(AetherIIDataComponents.CHARMS, new ArrayList<>(List.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY)));
            }
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
            if (slotIndex > 10) {
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
                } else if (this.isCharm(slotStack)) {
                    if (!this.moveItemStackTo(slotStack, 3, 11, false)) {
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

                    int minTier = 0;
                    if (reinforcementTier != null) {
                        minTier = reinforcementTier.getTier();
                    }
                    int extraDurability = List.of(ReinforcementTier.values()).subList(minTier, tierToUpgradeTo).stream().mapToInt(ReinforcementTier::getExtraDurability).sum();
                    input.set(DataComponents.MAX_DAMAGE, input.getMaxDamage() + extraDurability);

                    this.getPrimaryMaterial().shrink(primaryCost);
                    this.getSecondaryMaterial().shrink(secondaryCost);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean slotCharms() {
        List<ItemStack> charms = this.getInput().get(AetherIIDataComponents.CHARMS);
        boolean flag = false;
        if (charms != null) {
            for (Slot slot : this.slots) {
                if (slot instanceof ForgeCharmSlot charmSlot && !charmSlot.isLocked() && !charmSlot.getItem().isEmpty()) {
                    charms.set(charmSlot.getCharmIndex(), charmSlot.getItem());
                    charmSlot.setLocked(true);
                    flag = true;
                }
            }
        }
        return flag;
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
        int count = 0;
        List<ReinforcementTier.Cost> costs = new ArrayList<>(this.getCosts().values());
        for (int i = costs.size() - 1; i >= 0; i--) {
            ReinforcementTier.Cost cost = costs.get(i);
            if (cost != null) {
                count++;
            }
        }
        return count;
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
        return itemStack.is(AetherIITags.Items.CAN_BE_REINFORCED);
    }

    public boolean isPrimaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_PRIMARY_MATERIAL);
    }

    public boolean isSecondaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_SECONDARY_MATERIAL);
    }

    public int getTierForMaterials() {
        if (!this.isItemAtMaxTier()) {
            List<ReinforcementTier.Cost> costs = new ArrayList<>(this.getCosts().values());
            for (int i = costs.size() - 1; i >= 0; i--) {
                ReinforcementTier.Cost cost = costs.get(i);
                if (cost != null) {
                    if (((cost.primaryMaterial().asItem() == Items.AIR && cost.secondaryMaterial().asItem() != Items.AIR) || (this.getPrimaryMaterial().is(cost.primaryMaterial().asItem()) && this.getPrimaryMaterial().getCount() >= this.getPrimaryCostForTier(i + 1)))
                            && ((cost.primaryMaterial().asItem() != Items.AIR && cost.secondaryMaterial().asItem() == Items.AIR) || (this.getSecondaryMaterial().is(cost.secondaryMaterial().asItem()) && this.getSecondaryMaterial().getCount() >= this.getSecondaryCostForTier(i + 1)))) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    public boolean isCharm(ItemStack itemStack) {
        return itemStack.is(Items.BARRIER); //todo placeholder
    }

    public boolean hasNewCharms() {
        for (Slot slot : this.slots) {
            if (slot instanceof ForgeCharmSlot charmSlot && !charmSlot.isLocked() && !charmSlot.getItem().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
