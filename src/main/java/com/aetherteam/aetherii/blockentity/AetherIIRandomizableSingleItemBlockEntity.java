package com.aetherteam.aetherii.blockentity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;

import javax.annotation.Nullable;

public abstract class AetherIIRandomizableSingleItemBlockEntity extends BlockEntity implements ContainerSingleItem {
    private static final String TAG_ITEM = "item";
    private static final String TAG_LOOT_TABLE = "LootTable";
    private static final String TAG_LOOT_TABLE_SEED = "LootTableSeed";

    private ItemStack item = ItemStack.EMPTY;
    @Nullable
    protected ResourceLocation lootTable;
    protected long lootTableSeed;

    protected AetherIIRandomizableSingleItemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag) && !this.item.isEmpty()) {
            tag.put(TAG_ITEM, this.item.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (!this.tryLoadLootTable(tag)) {
            this.item = tag.contains(TAG_ITEM, Tag.TAG_COMPOUND) ? ItemStack.of(tag.getCompound(TAG_ITEM)) : ItemStack.EMPTY;
        } else {
            this.item = ItemStack.EMPTY;
        }
    }

    protected boolean tryLoadLootTable(CompoundTag tag) {
        if (tag.contains(TAG_LOOT_TABLE, Tag.TAG_STRING)) {
            this.lootTable = new ResourceLocation(tag.getString(TAG_LOOT_TABLE));
            this.lootTableSeed = tag.getLong(TAG_LOOT_TABLE_SEED);
            return true;
        }
        return false;
    }

    protected boolean trySaveLootTable(CompoundTag tag) {
        if (this.lootTable == null) {
            return false;
        }
        tag.putString(TAG_LOOT_TABLE, this.lootTable.toString());
        if (this.lootTableSeed != 0L) {
            tag.putLong(TAG_LOOT_TABLE_SEED, this.lootTableSeed);
        }
        return true;
    }

    public void unpackLootTable(@Nullable Player player) {
        if (this.lootTable != null && this.level != null && this.level.getServer() != null) {
            LootTable table = this.level.getServer().getLootData().getLootTable(this.lootTable);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger(serverPlayer, this.lootTable);
            }

            this.lootTable = null;
            LootParams.Builder builder = new LootParams.Builder((ServerLevel) this.level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition));
            if (player != null) {
                builder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);
            }
            table.fill(this, builder.create(LootContextParamSets.CHEST), this.lootTableSeed);
        }
    }

    public void setLootTable(@Nullable ResourceLocation lootTable, long lootTableSeed) {
        this.lootTable = lootTable;
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public ItemStack getTheItem() {
        this.unpackLootTable(null);
        return this.item;
    }

    public ItemStack splitTheItem(int count) {
        this.unpackLootTable(null);
        ItemStack split = this.item.split(count);
        if (this.item.isEmpty()) {
            this.item = ItemStack.EMPTY;
        }
        return split;
    }

    public void setTheItem(ItemStack item) {
        this.unpackLootTable(null);
        this.item = item;
        this.setChanged();
    }

    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        this.unpackLootTable(null);
        return this.item.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        this.unpackLootTable(null);
        return slot == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        this.unpackLootTable(null);
        if (slot != 0 || count <= 0 || this.item.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack split = this.item.split(count);
        if (this.item.isEmpty()) {
            this.item = ItemStack.EMPTY;
        }
        this.setChanged();
        return split;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        this.unpackLootTable(null);
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        ItemStack current = this.item;
        this.item = ItemStack.EMPTY;
        return current;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.unpackLootTable(null);
        if (slot == 0) {
            this.item = stack;
            if (this.item.getCount() > this.getMaxStackSize()) {
                this.item.setCount(this.getMaxStackSize());
            }
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.item = ItemStack.EMPTY;
        this.setChanged();
    }
}
