package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.blockentity.ArkeniumForgeBlockEntity;
import com.aetherteam.aetherii.inventory.menu.slot.ForgeCharmSlot;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.Charms;
import com.aetherteam.aetherii.item.components.ReinforcementTier;
import com.aetherteam.aetherii.item.equipment.charms.CharmItem;
import com.aetherteam.aetherii.network.packet.clientbound.ForgeSoundPacket;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ArkeniumForgeMenu extends AbstractContainerMenu {
    public static final ResourceLocation SLOT_PRIMARY = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_primary");
    public static final ResourceLocation SLOT_SECONDARY = ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "container/arkenium_forge/slot_secondary");
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
            public ResourceLocation getNoItemIcon() {
                return SLOT_PRIMARY;
            }
        });
        this.addSlot(new Slot(this.container, 2, 91, 149) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return ArkeniumForgeMenu.this.isSecondaryMaterial(stack);
            }

            @Override
            public ResourceLocation getNoItemIcon() {
                return SLOT_SECONDARY;
            }
        });

        int index = 3;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 2; i++) {
                this.addSlot(new ForgeCharmSlot(this, this.container, index, 54 + (52 * i), 39 + (17 * j), index - 3));
                index++;
            }
        }

        this.addStandardInventorySlots(playerInventory, 8, 169);
    }

    public void changeInput() {
        ItemStack input = this.getInput();
        for (Slot slot : this.slots) {
            if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                if (input.isEmpty()) {
                    if (!forgeCharmSlot.getItem().isEmpty()) {
                        if (forgeCharmSlot.isLocked()) {
                            forgeCharmSlot.set(ItemStack.EMPTY);
                        } else {
                            this.quickMoveStack(this.player, forgeCharmSlot.index);
                        }
                    }
                }
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
                } else if (slotIndex >= 11 && slotIndex < 38) {
                    if (!this.moveItemStackTo(slotStack, 38, 47, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIndex >= 38 && slotIndex < 47 && !this.moveItemStackTo(slotStack, 11, 38, false)) {
                    return ItemStack.EMPTY;
                } else {
                    if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
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

    public boolean upgradeItem(ReinforcementTier tierToUpgradeTo) {
        if (tierToUpgradeTo != null) {
            int tierNumberToUpgradeTo = tierToUpgradeTo.getTierNumber();
            ItemStack input = this.getInput();
            ReinforcementTier currentReinforcementTier = input.get(AetherIIDataComponents.REINFORCEMENT_TIER);

            if (!ReinforcementTier.isItemAtMaxTier(input) && ((currentReinforcementTier == null && tierNumberToUpgradeTo > 0) || (currentReinforcementTier != null && tierNumberToUpgradeTo > currentReinforcementTier.getTierNumber()))) {
                if (!input.isEmpty()) {
                    int primaryCost = ReinforcementTier.getPrimaryCostForTier(input, tierNumberToUpgradeTo);
                    int secondaryCost = ReinforcementTier.getSecondaryCostForTier(input, tierNumberToUpgradeTo);
                    if (primaryCost != -1 && secondaryCost != -1) {
                        int minTier = 0;
                        if (currentReinforcementTier != null) {
                            minTier = currentReinforcementTier.getTierNumber();
                        }

                        boolean flag = true;
                        int extraDurability = 0;
                        for (ReinforcementTier tier : List.of(ReinforcementTier.values()).subList(minTier, tierNumberToUpgradeTo)) {
                            ReinforcementTier.Stats stats = tier.getStat(input);
                            if (stats != null) {
                                extraDurability += stats.durabilityToAdd();
                            } else {
                                flag = false;
                                break;
                            }
                        }

                        if (flag) {
                            Charms newCharms = this.upgradeCharmSlots(tierToUpgradeTo);
                            if (newCharms != null) {
                                input.set(AetherIIDataComponents.CHARMS, newCharms);
                            }

                            input.set(DataComponents.MAX_DAMAGE, input.getMaxDamage() + extraDurability);
                            input.set(AetherIIDataComponents.REINFORCEMENT_TIER, tierToUpgradeTo);

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

    public Charms upgradeCharmSlots(ReinforcementTier tierToUpgradeTo) {
        ItemStack input = this.getInput();
        ReinforcementTier currentReinforcementTier = input.get(AetherIIDataComponents.REINFORCEMENT_TIER);
        ReinforcementTier.Stats newStats = tierToUpgradeTo.getStat(input);
        Charms newCharms = null;

        if (newStats != null) {
            Charms newStatCharms = newStats.charmsToSet();

            if (newStatCharms != null) {
                Charms charms = input.get(AetherIIDataComponents.CHARMS);
                newCharms = new Charms();

                if (charms != null) {
                    List<Charms.CharmHolder> currentCharmHolders = charms.charmHolders();
                    List<Charms.CharmHolder> newStatCharmHolders = newStatCharms.charmHolders();
                    List<Charms.CharmHolder> newCharmHolders = newCharms.charmHolders();
                    List<Charms.CharmHolder> currentStatCharmHolders = List.of();
                    if (currentReinforcementTier != null) {
                        ReinforcementTier.Stats currentStats = currentReinforcementTier.getStat(input);
                        if (currentStats != null) {
                            currentStatCharmHolders = currentStats.charmsToSet().charmHolders();
                        }
                    }

                    int baseSize = currentCharmHolders.size() - currentStatCharmHolders.size();
                    int size = baseSize + newStatCharmHolders.size();
                    for (int i = 0; i < size; i++) {
                        if (i < baseSize) {
                            newCharmHolders.add(i, new Charms.CharmHolder(currentCharmHolders.get(i)));
                        } else {
                            Charms.CharmHolder newStatCharmHolder = newStatCharmHolders.get(i - baseSize);
                            if (i < currentCharmHolders.size()) {
                                Charms.CharmHolder currentStatCharmHolder = currentStatCharmHolders.get(i);
                                newCharmHolders.add(i, new Charms.CharmHolder(newStatCharmHolder.getType(), newStatCharmHolder.getTier(), currentStatCharmHolder.getStack()));
                            } else {
                                newCharmHolders.add(i, newStatCharmHolder);
                            }
                        }
                    }
                } else {
                    newCharms = newStatCharms;
                }
            }
        }
        return newCharms;
    }

    public boolean slotCharms() {
        return this.replaceCharms(this.getInput(), true);
    }

    public boolean replaceCharms(ItemStack stack, boolean lock) {
        boolean flag = false;
        List<Charms.CharmHolder> charmHolders = Charms.getCharmsForItem(stack);
        Charms newCharms = new Charms();
        if (charmHolders != null) {
            for (Slot slot : this.slots) {
                if (slot instanceof ForgeCharmSlot forgeCharmSlot) {
                    Charms.CharmHolder charmHolder = Charms.getCharmHolderForItem(stack, forgeCharmSlot.getCharmIndex());
                    if (charmHolder != null) {
                        if (forgeCharmSlot.isLocked()) {
                            newCharms.charmHolders().add(forgeCharmSlot.getCharmIndex(), new Charms.CharmHolder(charmHolder));
                        } else {
                            newCharms.charmHolders().add(forgeCharmSlot.getCharmIndex(), new Charms.CharmHolder(charmHolder, slot.getItem()));
                            if (!slot.getItem().isEmpty() && lock) {
                                forgeCharmSlot.setLocked(true);
                            }
                        }
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            stack.set(AetherIIDataComponents.CHARMS, newCharms);
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

    public boolean isPrimaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_PRIMARY_MATERIAL);
    }

    public boolean isSecondaryMaterial(ItemStack material) {
        return material.is(AetherIITags.Items.FORGE_SECONDARY_MATERIAL);
    }

    public boolean isCharm(ItemStack itemStack) {
        return itemStack.getItem() instanceof CharmItem;
    }

    public int getTierForMaterials() {
        ItemStack input = this.getInput();
        if (!ReinforcementTier.isItemAtMaxTier(input)) {
            List<ReinforcementTier.Cost> costs = new ArrayList<>(ReinforcementTier.getCosts(input).values());
            for (int i = costs.size() - 1; i >= 0; i--) {
                ReinforcementTier.Cost cost = costs.get(i);
                if (cost != null) {
                    if (((cost.primaryMaterial().asItem() == Items.AIR && cost.secondaryMaterial().asItem() != Items.AIR) || (this.getPrimaryMaterial().is(cost.primaryMaterial().asItem()) && this.getPrimaryMaterial().getCount() >= ReinforcementTier.getPrimaryCostForTier(input, i + 1)))
                            && ((cost.primaryMaterial().asItem() != Items.AIR && cost.secondaryMaterial().asItem() == Items.AIR) || (this.getSecondaryMaterial().is(cost.secondaryMaterial().asItem()) && this.getSecondaryMaterial().getCount() >= ReinforcementTier.getSecondaryCostForTier(input, i + 1)))) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
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
