package com.aetherteam.aetherii.inventory.menu;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.inventory.container.AccessoryContainer;
import com.aetherteam.aetherii.inventory.menu.slot.AccessorySlot;
import com.aetherteam.aetherii.inventory.menu.slot.SaddlebagSlot;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.MoaFeedItem;
import com.aetherteam.aetherii.item.miscellaneous.MoaSaddlebagItem;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.CraftingMenuAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GuidebookEquipmentMenu extends AbstractContainerMenu {
    public static final ResourceLocation RELIC_SLOT_LOCATION = new ResourceLocation(AetherII.MODID, "accessories/slot_relic");
    public static final ResourceLocation HANDWEAR_SLOT_LOCATION = new ResourceLocation(AetherII.MODID, "accessories/slot_handwear");
    public static final ResourceLocation ACCESSORY_SLOT_LOCATION = new ResourceLocation(AetherII.MODID, "accessories/slot_accessory");
    private static final Map<EquipmentSlot, ResourceLocation> TEXTURE_EMPTY_SLOTS = Map.of(
            EquipmentSlot.FEET,
            InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
            EquipmentSlot.LEGS,
            InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
            EquipmentSlot.CHEST,
            InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
            EquipmentSlot.HEAD,
            InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
    );
    private static final ResourceLocation EMPTY_SADDLE_SLOT = new ResourceLocation(AetherII.MODID, "guidebook/equipment/slot_moa_saddle");
    private static final ResourceLocation EMPTY_SADDLEBAG_SLOT = new ResourceLocation(AetherII.MODID, "guidebook/equipment/slot_saddlebag");
    private static final ResourceLocation EMPTY_FEED_SLOT = new ResourceLocation(AetherII.MODID, "guidebook/equipment/slot_moa_feed");
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 2, 2);
    private final ResultContainer resultSlots = new ResultContainer();
    private final Player owner;
    @Nullable
    private final Moa moa;

    public GuidebookEquipmentMenu(int containerId, Inventory playerInventory) {
        this(AetherIIMenuTypes.GUIDEBOOK.get(), containerId, playerInventory, -1);
    }

    public GuidebookEquipmentMenu(int containerId, Inventory playerInventory, Entity entity) {
        this(AetherIIMenuTypes.GUIDEBOOK.get(), containerId, playerInventory, entity.getId());
    }

    public GuidebookEquipmentMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(AetherIIMenuTypes.GUIDEBOOK.get(), containerId, playerInventory, ByteBufCodecs.INT.decode(extraData));
    }

    public GuidebookEquipmentMenu(MenuType<GuidebookEquipmentMenu> menuType, int containerId, Inventory playerInventory, int entityId) {
        super(menuType, containerId);
        this.owner = playerInventory.player;
        if (this.owner.getVehicle() instanceof Moa vehicle) {
            this.moa = vehicle;
            this.addSlotListener(this.moa);
        } else {
            if (playerInventory.player.level().getEntity(entityId) instanceof Moa moaEntity) {
                this.moa = moaEntity;
                this.addSlotListener(this.moa);
            } else {
                this.moa = null;
            }
        }

        this.createLeftPage(playerInventory);
        this.createRightPage(playerInventory);
    }

    private void createRightPage(Inventory playerInventory) {
        int xOffset = this.owner.isCreative() ? 19 : 0; //todo theres a bug with this being offset when it shouldnt be sometimes i guess

        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 202 + xOffset, 50));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new Slot(this.craftSlots, j + i * 2,  xOffset + 146 + j * 18, 40 + i * 18));
            }
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 102 + j1 * 18, 92 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 102 + i1 * 18, 150));
        }
    }

    private void createLeftPage(Inventory playerInventory) {
        if (this.moa != null) {
            SimpleContainer moaInventory = this.moa.getInventory(); //todo improve all these slots

            this.addSlot(new Slot(moaInventory, 0, -64, 38) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(AetherIIItems.MOA_SADDLE.get());
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SADDLE_SLOT);
                }
            });
            this.addSlot(new Slot(moaInventory, 1, -64, 56) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof MoaSaddlebagItem;
                }

                @Override
                public boolean mayPickup(Player player) {
                    return GuidebookEquipmentMenu.this.getItems().subList(6, 30).stream().allMatch(ItemStack::isEmpty) && super.mayPickup(player);
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    GuidebookEquipmentMenu.this.moa.setSaddlebagStack(this.getItem());
                    GuidebookEquipmentMenu.this.recalculateSaddlebagSlots();
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SADDLEBAG_SLOT);
                }
            });
            this.addSlot(new Slot(moaInventory, 2, -64, 74) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof MoaFeedItem;
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_FEED_SLOT);
                }
            });

            this.addSlot(new Slot(moaInventory, 3, 64, 38) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new Slot(moaInventory, 4, 64, 56) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
            this.addSlot(new Slot(moaInventory, 5, 64, 74) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });

            for (int l = 0; l < 3; l++) {
                for (int j1 = 0; j1 < 8; j1++) {
                    int slotId = j1 + l * 8;
                    this.addSlot(new SaddlebagSlot(moaInventory, 6 + slotId, -63 + j1 * 18, 114 + l * 18, slotId % 8 >= this.moa.getSaddlebagRowSize()));
                }
            }
        } else {
            AccessoryContainer accessories = AetherIIDataAttachments.get(owner, AetherIIDataAttachments.ACCESSORIES);
            this.addSlot(new AccessorySlot(accessories, this.owner, AccessoryContainer.SlotType.RELIC, 0, 64, 38, RELIC_SLOT_LOCATION));
            this.addSlot(new AccessorySlot(accessories, this.owner, AccessoryContainer.SlotType.RELIC, 1, 64, 56, RELIC_SLOT_LOCATION));
            this.addSlot(new AccessorySlot(accessories, this.owner, AccessoryContainer.SlotType.HANDWEAR, 2, 64, 74, HANDWEAR_SLOT_LOCATION));
            this.addSlot(new AccessorySlot(accessories, this.owner, AccessoryContainer.SlotType.ACCESSORY, 3, 64, 92, ACCESSORY_SLOT_LOCATION));

            for (int k = 0; k < 4; k++) {
                EquipmentSlot equipmentslot = SLOT_IDS[k];
                ResourceLocation resourceLocation = TEXTURE_EMPTY_SLOTS.get(equipmentslot);
                this.addSlot(new Slot(playerInventory, 39 - k, -64, 38 + k * 18) {
                    @Override
                    public void setByPlayer(ItemStack newItem) {
                        ItemStack oldItem = this.getItem().copy();
                        GuidebookEquipmentMenu.this.owner.onEquipItem(equipmentslot, oldItem, newItem);
                        super.setByPlayer(newItem);
                    }

                    @Override
                    public int getMaxStackSize() {
                        return 1;
                    }

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return Mob.getEquipmentSlotForItem(stack) == equipmentslot;
                    }

                    @Override
                    public boolean mayPickup(Player player) {
                        ItemStack stack = this.getItem();
                        return (stack.isEmpty() || player.isCreative() || !stack.isEnchanted()) && super.mayPickup(player);
                    }

                    @Override
                    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                        return Pair.of(InventoryMenu.BLOCK_ATLAS, resourceLocation);
                    }
                });
            }

            this.addSlot(new Slot(playerInventory, 40, -64, 112) {
                @Override
                public void setByPlayer(ItemStack newItem) {
                    ItemStack oldItem = this.getItem().copy();
                    GuidebookEquipmentMenu.this.owner.onEquipItem(EquipmentSlot.OFFHAND, oldItem, newItem);
                    super.setByPlayer(newItem);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
                }
            });
        }
    }

    private void recalculateSaddlebagSlots() {
        if (this.moa != null) {
            for (int l = 0; l < 3; l++) {
                for (int j1 = 0; j1 < 8; j1++) {
                    int slotId = j1 + l * 8;
                    Slot slot = this.slots.get(6 + slotId);
                    if (slot instanceof SaddlebagSlot saddlebagSlot) {
                        saddlebagSlot.setHidden(slotId % 8 >= this.moa.getSaddlebagRowSize());
                    }
                }
            }
        } else {
            for (Slot slot : this.slots) {
                if (slot instanceof SaddlebagSlot saddlebagSlot) {
                    saddlebagSlot.setHidden(true);
                }
            }
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container inventory) {
        if (this.owner.level() instanceof ServerLevel serverLevel) { //todo
            CraftingMenuAccessor.callSlotChangedCraftingGrid(this, serverLevel, this.owner, this.craftSlots, this.resultSlots);
        }
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.resultSlots.clearContent();
        if (!player.level().isClientSide()) {
            this.clearContainer(player, this.craftSlots);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (this.moa != null) {
            ItemStack itemstack = ItemStack.EMPTY;
            Slot slot = this.slots.get(index);
            if (slot.hasItem()) {
                ItemStack itemstack1 = slot.getItem();
                itemstack = itemstack1.copy();
                if (index >= 0 && index < 6) {
                    if (!this.moveItemStackTo(itemstack1, 35, 71, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index == 30) {
                    if (!this.moveItemStackTo(itemstack1, 35, 71, true)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickCraft(itemstack1, itemstack);
                } else if (index >= 31 && index < 35) {
                    if (!this.moveItemStackTo(itemstack1, 35, 71, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.is(AetherIIItems.MOA_SADDLE.get()) && !this.slots.get(0).hasItem()) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() instanceof MoaSaddlebagItem && !this.slots.get(1).hasItem()) {
                    if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.getItem() instanceof MoaFeedItem && this.slots.get(2).mayPlace(itemstack)) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 6 && index < 30) {
                    if (!this.moveItemStackTo(itemstack1, 35, 71, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 35 && index < 62) {
                    if (!this.moveItemStackTo(itemstack1, 6, 30, false)) {
                        if (!this.moveItemStackTo(itemstack1, 62, 71, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (index >= 62 && index < 71) {
                    if (!this.moveItemStackTo(itemstack1, 35, 62, false)) {
                        if (!this.moveItemStackTo(itemstack1, 6, 30, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                } else if (!this.moveItemStackTo(itemstack1, 35, 71, false)) {
                    if (!this.moveItemStackTo(itemstack1, 6, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                if (itemstack1.isEmpty()) {
                    slot.setByPlayer(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }

                if (itemstack1.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }

                slot.onTake(player, itemstack1);
                if (index == 0) {
                    player.drop(itemstack1, false);
                }
            }

            return itemstack;
        } else {
            ItemStack itemstack = ItemStack.EMPTY;
            Slot slot = this.slots.get(index);
            if (slot.hasItem()) {
                ItemStack itemstack1 = slot.getItem();
                itemstack = itemstack1.copy();
                EquipmentSlot equipmentslot = player.getEquipmentSlotForItem(itemstack);
                if (index >= 0 && index < 9) {
                    if (!this.moveItemStackTo(itemstack1, 14, 50, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index == 9) {
                    if (!this.moveItemStackTo(itemstack1, 14, 50, true)) {
                        return ItemStack.EMPTY;
                    }
                    slot.onQuickCraft(itemstack1, itemstack);
                } else if (index >= 10 && index < 14) {
                    if (!this.moveItemStackTo(itemstack1, 14, 50, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_RELICS) && (!this.slots.get(0).hasItem() || !this.slots.get(1).hasItem())) {
                    if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_HANDWEAR) && !this.slots.get(2).hasItem()) {
                    if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (itemstack.is(AetherIITags.Items.EQUIPMENT_ACCESSORIES) && !this.slots.get(3).hasItem()) {
                    if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(7 - equipmentslot.getIndex()).hasItem()) {
                    int i = 7 - equipmentslot.getIndex();
                    if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(8).hasItem()) {
                    if (!this.moveItemStackTo(itemstack1, 8, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 14 && index < 41) {
                    if (!this.moveItemStackTo(itemstack1, 41, 50, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 41 && index < 50) {
                    if (!this.moveItemStackTo(itemstack1, 14, 41, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 14, 50, false)) {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.isEmpty()) {
                    slot.setByPlayer(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }

                if (itemstack1.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }

                slot.onTake(player, itemstack1);
                if (index == 0) {
                    player.drop(itemstack1, false);
                }
            }

            return itemstack;
        }
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    @Nullable
    public Moa getMoa() {
        return this.moa;
    }
}
