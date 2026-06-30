package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.utility.MusicBlock;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.EngravedDisc;
import com.aetherteam.aetherii.network.PacketDistributor;
import com.aetherteam.aetherii.network.packet.clientbound.MusicBlockPlayPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.JukeboxSong;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MusicBlockEntity extends BlockEntity implements ContainerSingleItem {
    private ItemStack item = ItemStack.EMPTY;
    private final SongPlayer songPlayer;

    public MusicBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(AetherIIBlockEntityTypes.MUSIC_BLOCK.get(), worldPosition, blockState);
        this.songPlayer = new SongPlayer(this::onSongChanged, this.getBlockPos());
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MusicBlockEntity blockEntity) {
        blockEntity.songPlayer.tick(level, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ItemStack newItem = tag.contains("RecordItem") ? ItemStack.of(tag.getCompound("RecordItem")) : ItemStack.EMPTY;
        if (!this.item.isEmpty() && !ItemStack.matches(newItem, this.item)) {
            this.songPlayer.stop(this.level, this.getBlockState());
        }
        this.item = newItem;
        if (tag.contains("ticks_since_song_started")) {
            EngravedDisc.getSong(this.item).ifPresent(song -> this.songPlayer.setSongWithoutPlaying(song, tag.getLong("ticks_since_song_started")));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.getTheItem().isEmpty()) {
            tag.put("RecordItem", this.getTheItem().save(new CompoundTag()));
        }
        if (this.songPlayer.getSong() != null) {
            tag.putLong("ticks_since_song_started", this.songPlayer.getTicksSinceSongStarted());
        }
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        return AetherIIDataComponents.has(itemStack, AetherIIDataComponents.ENGRAVED_DISC) && this.getItem(slot).isEmpty();
    }

    @Override
    public boolean canTakeItem(Container into, int slot, ItemStack itemStack) {
        return into.hasAnyMatching(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot != 0 || amount <= 0 || this.item.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack removed = this.item.split(amount);
        if (this.item.isEmpty()) {
            this.item = ItemStack.EMPTY;
        }
        this.itemChanged();
        return removed;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot == 0) {
            this.setTheItem(stack);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public ItemStack getTheItem() {
        return this.item;
    }

    public ItemStack removeTheItem() {
        ItemStack removed = this.item;
        this.setTheItem(ItemStack.EMPTY);
        return removed;
    }

    public ItemStack splitTheItem(int count) {
        return this.removeItem(0, count);
    }

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

    public SongPlayer getSongPlayer() {
        return this.songPlayer;
    }

    public void onSongChanged() {
        if (this.level != null) {
            this.level.updateNeighborsAt(this.getBlockPos(), this.getBlockState().getBlock());
        }
        this.setChanged();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level != null) {
            this.level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));
            this.level.levelEvent(1011, this.getBlockPos(), 0);
        }
    }

    public void popOutTheItem() {
        if (this.level != null && !this.level.isClientSide()) {
            BlockPos pos = this.getBlockPos();
            ItemStack itemBeforePoppingOut = this.getTheItem();
            if (!itemBeforePoppingOut.isEmpty()) {
                this.removeTheItem();
                Vec3 itemPos = Vec3.atLowerCornerWithOffset(pos, 0.5F, 1.01, 0.5F).offsetRandom(this.level.getRandom(), 0.7F);
                ItemEntity entity = new ItemEntity(this.level, itemPos.x(), itemPos.y(), itemPos.z(), itemBeforePoppingOut.copy());
                entity.setDefaultPickUpDelay();
                this.level.addFreshEntity(entity);
                this.onSongChanged();
            }
        }
    }

    public int getComparatorOutput() {
        return EngravedDisc.getSong(this.item).map(Holder::value).map(JukeboxSong::comparatorOutput).orElse(0);
    }

    public static class SongPlayer {
        private final Runnable onSongChanged;
        private final BlockPos blockPos;
        @Nullable
        private Holder<JukeboxSong> song;
        private long ticksSinceSongStarted;

        public SongPlayer(Runnable onSongChanged, BlockPos blockPos) {
            this.onSongChanged = onSongChanged;
            this.blockPos = blockPos;
        }

        public void tick(Level level, BlockState state) {
            if (this.song != null) {
                ++this.ticksSinceSongStarted;
                if (this.ticksSinceSongStarted >= (long) (this.song.value().lengthInSeconds() * 20.0F)) {
                    this.stop(level, state);
                }
            }
        }

        public void play(@Nullable LevelAccessor level, Holder<JukeboxSong> song) {
            this.song = song;
            this.ticksSinceSongStarted = 0L;
            this.onSongChanged.run();
            if (level instanceof Level actualLevel) {
                actualLevel.gameEvent(GameEvent.JUKEBOX_PLAY, this.blockPos, GameEvent.Context.of(actualLevel.getBlockState(this.blockPos)));
            }
            if (level instanceof ServerLevel serverLevel) {
                PacketDistributor.sendToPlayersInDimension(serverLevel, new MusicBlockPlayPacket(song, this.blockPos));
            }
        }

        public void stop(@Nullable LevelAccessor level, BlockState state) {
            if (this.song != null) {
                this.song = null;
                this.ticksSinceSongStarted = 0L;
                this.onSongChanged.run();
                if (level instanceof Level actualLevel) {
                    actualLevel.levelEvent(1011, this.blockPos, 0);
                    actualLevel.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, this.blockPos, GameEvent.Context.of(state));
                }
            }
        }

        public void setSongWithoutPlaying(Holder<JukeboxSong> song, long ticksSinceSongStarted) {
            this.song = song;
            this.ticksSinceSongStarted = ticksSinceSongStarted;
            this.onSongChanged.run();
        }

        @Nullable
        public Holder<JukeboxSong> getSong() {
            return this.song;
        }

        public boolean isPlaying() {
            return this.song != null;
        }

        public long getTicksSinceSongStarted() {
            return this.ticksSinceSongStarted;
        }
    }
}
