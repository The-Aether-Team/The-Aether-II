package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.utility.MusicBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.EngravedDisc;
import com.aetherteam.aetherii.mixin.mixins.client.accessor.JukeboxSongPlayerAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.MusicBlockPlayPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.transaction.RootCommitJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.Optional;

public class MusicBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    private ItemStack item;
    private final SongPlayer songPlayer;
    private final RootCommitJournal itemChangedJournal;

    public MusicBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(AetherIIBlockEntityTypes.MUSIC_BLOCK.get(), worldPosition, blockState);
        this.item = ItemStack.EMPTY;
        this.songPlayer = new SongPlayer(this::onSongChanged, this.getBlockPos());
        this.itemChangedJournal = new RootCommitJournal(() -> {
            if (!this.isRemoved()) {
                this.itemChanged();
            }
        });
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MusicBlockEntity blockEntity) {
        blockEntity.songPlayer.tick(level, blockState);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ItemStack newItem = input.read("RecordItem", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        if (!this.item.isEmpty() && !ItemStack.isSameItemSameComponents(newItem, this.item)) {
            this.songPlayer.stop(this.level, this.getBlockState());
        }
        this.item = newItem;
        input.getLong("ticks_since_song_started").ifPresent((ticksSinceSongStarted) -> EngravedDisc.getSong(this.item).ifPresent((song) -> this.songPlayer.setSongWithoutPlaying(song, ticksSinceSongStarted)));
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!this.getTheItem().isEmpty()) {
            output.store("RecordItem", ItemStack.CODEC, this.getTheItem());
        }
        if (this.songPlayer.getSong() != null) {
            output.putLong("ticks_since_song_started", this.songPlayer.getTicksSinceSongStarted());
        }
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        return itemStack.has(AetherIIDataComponents.ENGRAVED_DISC) && this.getItem(slot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container into, int slot, ItemStack itemStack) {
        return into.hasAnyMatching(ItemStack::isEmpty);
    }

    @Override
    public void onTransfer(int slot, int amountChange, TransactionContext transaction) {
        this.itemChangedJournal.updateSnapshots(transaction);
    }

    @Override
    public ItemStack getTheItem() {
        return this.item;
    }

    @Override
    public ItemStack splitTheItem(int count) {
        ItemStack retrievedItem = this.item;
        this.setTheItem(ItemStack.EMPTY);
        return retrievedItem;
    }

    @Override
    public void setItem(int slot, ItemStack stack, boolean insideTransaction) {
        if (slot == 0) {
            if (insideTransaction) {
                this.item = stack;
            } else {
                this.setTheItem(stack);
            }
        }
    }

    @Override
    public void setTheItem(ItemStack itemStack) {
        this.item = itemStack;
        this.itemChanged();
    }

    private void itemChanged() {
        boolean itemWasInserted = !this.item.isEmpty();
        Optional<Holder<JukeboxSong>> maybeSong = EngravedDisc.getSong(this.item);
        this.notifyItemChanged(itemWasInserted);
        if (itemWasInserted && maybeSong.isPresent()) {
            this.songPlayer.play(this.level, maybeSong.get());
        } else {
            this.songPlayer.stop(this.level, this.getBlockState());
        }
    }

    private void notifyItemChanged(boolean wasInserted) {
        if (this.level != null && this.level.getBlockState(this.getBlockPos()) == this.getBlockState()) {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(MusicBlock.HAS_RECORD, wasInserted), 2);
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public SongPlayer getSongPlayer() {
        return this.songPlayer;
    }

    public void onSongChanged() {
        this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
        this.setChanged();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));
        this.level.levelEvent(1011, this.getBlockPos(), 0);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.popOutTheItem();
    }

    public void popOutTheItem() {
        if (this.level != null && !this.level.isClientSide()) {
            BlockPos pos = this.getBlockPos();
            ItemStack itemBeforePoppingOut = this.getTheItem();
            if (!itemBeforePoppingOut.isEmpty()) {
                this.removeTheItem();
                Vec3 itemPos = Vec3.atLowerCornerWithOffset(pos, 0.5F, 1.01, 0.5F).offsetRandomXZ(this.level.getRandom(), 0.7F);
                ItemStack itemStack = itemBeforePoppingOut.copy();
                ItemEntity entity = new ItemEntity(this.level, itemPos.x(), itemPos.y(), itemPos.z(), itemStack);
                entity.setDefaultPickUpDelay();
                this.level.addFreshEntity(entity);
                this.onSongChanged();
            }
        }
    }

    public int getComparatorOutput() {
        return EngravedDisc.getSong(this.item).map(Holder::value).map(JukeboxSong::comparatorOutput).orElse(0);
    }

    public static class SongPlayer extends JukeboxSongPlayer {
        private final BlockPos blockPos;
        private final OnSongChanged onSongChanged;

        public SongPlayer(OnSongChanged onSongChanged, BlockPos blockPos) {
            super(onSongChanged, blockPos);
            this.onSongChanged = onSongChanged;
            this.blockPos = blockPos;
        }

        @Override
        public void play(LevelAccessor level, Holder<JukeboxSong> song) {
            JukeboxSongPlayerAccessor accessor = (JukeboxSongPlayerAccessor) this;
            accessor.aether_ii$setSong(song);
            accessor.aether_ii$setTicksSinceSongStarted(0L);
            if (level instanceof ServerLevel serverLevel) {
                PacketDistributor.sendToPlayersInDimension(serverLevel, new MusicBlockPlayPacket(song, this.blockPos));
            }
            this.onSongChanged.notifyChange();
        }
    }
}
