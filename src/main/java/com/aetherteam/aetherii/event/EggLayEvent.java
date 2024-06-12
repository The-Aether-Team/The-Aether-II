package com.aetherteam.aetherii.event;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.entity.EntityEvent;

import javax.annotation.Nullable;

/**
 * EggLayEvent is fired before a Moa lays an egg.
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If the event is not canceled, the Moa will lay an egg.
 * <br>
 * This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, the Moa will not lay an egg.
 */
public class EggLayEvent extends EntityEvent implements ICancellableEvent {
    @Nullable
    private ItemStack item;
    @Nullable
    private SoundEvent sound;
    private float volume;
    private float pitch;

    /**
     * @param entity The {@link Entity} laying the egg.
     * @param sound  The original {@link SoundEvent} played by laying the egg.
     * @param volume The original volume of the sound as a {@link Float}.
     * @param pitch  The original pitch of the sound as a {@link Float}.
     * @param item   The original egg {@link ItemStack} to be laid.
     */
    public EggLayEvent(Entity entity, @Nullable SoundEvent sound, float volume, float pitch, @Nullable ItemStack item) {
        super(entity);
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.item = item;
    }

    /**
     * This method is {@link Nullable}. If null, no egg item will be laid.
     *
     * @return The egg {@link Item} to be laid.
     */
    @Nullable
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Sets a new egg item to be laid.
     *
     * @param item The egg {@link Item}.
     */
    public void setItem(@Nullable ItemStack item) {
        this.item = item;
    }

    /**
     * This method is {@link Nullable}. If null, no sound will play.
     *
     * @return The {@link SoundEvent} to play when the egg is laid.
     */
    @Nullable
    public SoundEvent getSound() {
        return this.sound;
    }

    /**
     * Sets a new laying sound to play.
     *
     * @param sound The laying {@link SoundEvent}.
     */
    public void setSound(@Nullable SoundEvent sound) {
        this.sound = sound;
    }

    /**
     * @return The volume of the laying sound as a {@link Float}
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * Sets a new volume for the laying sound.
     *
     * @param volume The volume as a {@link Float}.
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * @return The pitch of the laying sound as a {@link Float}
     */
    public float getPitch() {
        return this.pitch;
    }

    /**
     * Sets a new pitch for the laying sound.
     *
     * @param pitch The pitch as a {@link Float}.
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}