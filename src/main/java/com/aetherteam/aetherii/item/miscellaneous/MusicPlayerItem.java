package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.StoredMusic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

import java.util.Optional;
import java.util.function.Consumer;

public class MusicPlayerItem extends Item {
    public MusicPlayerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!stack.has(AetherIIDataComponents.STORED_MUSIC) && other.has(DataComponents.JUKEBOX_PLAYABLE) && other.is(AetherIITags.Items.ENGRAVED_DISCS)) {
            Optional<Holder<JukeboxSong>> optional = JukeboxSong.fromStack(player.registryAccess(), other);
            if (optional.isPresent()) {
                stack.set(AetherIIDataComponents.STORED_MUSIC, new StoredMusic(other.getItemHolder(), optional.get().value().soundEvent()));
                other.shrink(1);
                return true;
            }
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (!stack.has(AetherIIDataComponents.STORED_MUSIC) && other.has(DataComponents.JUKEBOX_PLAYABLE) && other.is(AetherIITags.Items.ENGRAVED_DISCS)) {
            Optional<Holder<JukeboxSong>> optional = JukeboxSong.fromStack(player.registryAccess(), other);
            if (optional.isPresent()) {
                stack.set(AetherIIDataComponents.STORED_MUSIC, new StoredMusic(other.getItemHolder(), optional.get().value().soundEvent()));
                other.shrink(1);
                return true;
            }
        } else if (action == ClickAction.SECONDARY && stack.has(AetherIIDataComponents.STORED_MUSIC) && other.isEmpty()) {
            StoredMusic music = stack.get(AetherIIDataComponents.STORED_MUSIC);
            Holder<SoundEvent> sound = Holder.direct(SoundEvent.createVariableRangeEvent(music.sound().value().location()));
            SoundSource category = SoundSource.RECORDS;
            slot.set(stack.get(AetherIIDataComponents.STORED_MUSIC).item().value().getDefaultInstance());
            stack.remove(AetherIIDataComponents.STORED_MUSIC);
            if (player.level().isClientSide()) {
                AetherIIClientProxy.stopSoundEvent(sound.value(), category);
            }
            return false;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        StoredMusic music = stack.get(AetherIIDataComponents.STORED_MUSIC);
        if (music != null && player.level().isClientSide()) {
            Holder<SoundEvent> sound = Holder.direct(SoundEvent.createVariableRangeEvent(music.sound().value().location()));
            SoundSource category = SoundSource.RECORDS;
            if (!AetherIIClientProxy.isPlayingSoundEvent(music.sound().value())) {
                Vec3 pos = player.position();
                float volume = 1.0F;
                float pitch = 1.0F;
                double d0 = Mth.square(sound.value().getRange(volume));
                long i = player.level().getRandom().nextLong();
                double d1 = pos.x - player.getX();
                double d2 = pos.y - player.getY();
                double d3 = pos.z - player.getZ();
                double d4 = d1 * d1 + d2 * d2 + d3 * d3;
                if (d4 <= d0) {
                    AetherIIClientProxy.stopOtherMusicPlayerSound(category);
                    AetherIIClientProxy.playSoundEvent(sound, category, pos.x(), pos.y(), pos.z(), volume, pitch, i);
                    return InteractionResult.SUCCESS;
                }
            } else {
                AetherIIClientProxy.stopSoundEvent(sound.value(), category);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) { //todo
        tooltipAdder.accept(Component.literal("Stored Song:").withStyle(ChatFormatting.AQUA));
        StoredMusic music = stack.get(AetherIIDataComponents.STORED_MUSIC);
        if (music != null) {
            Holder<Item> item = music.item();
            ItemStack discStack = item.value().getDefaultInstance();
            JukeboxPlayable song = discStack.get(DataComponents.JUKEBOX_PLAYABLE);
            if (song != null) {
                song.addToTooltip(context, tooltipAdder, flag, stack);
            }
        }
    }

    public static void onItemDropped(ItemTossEvent event) {
        ItemStack stack = event.getEntity().getItem();
        tryStopMusic(stack);
    }

    public static void onContainerClose(PlayerContainerEvent.Close event) {
        for (Slot slot : event.getContainer().slots) {
            if (slot.container instanceof Inventory)
                continue; // Skip slots in player inventory

            ItemStack stack = slot.getItem();
            tryStopMusic(stack);
        }
    }

    public static void onPlayerDeath(LivingDropsEvent event) {
        for (ItemEntity itemEntity : event.getDrops()) {
            ItemStack stack = itemEntity.getItem();
            tryStopMusic(stack);
        }
    }

    private static void tryStopMusic(ItemStack stack){
        // Check if bundle and read through its contents for a music player
        BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
        if (contents != null) {
            // Music player only stacks to one, so just check the first item
            if(contents.isEmpty())
                return;

            ItemStack item = contents.getItemUnsafe(0);
            if(item.getItem() instanceof MusicPlayerItem)
                stack = item;
        }
        else if(!(stack.getItem() instanceof MusicPlayerItem)) {
            return;
        }

        StoredMusic music = stack.get(AetherIIDataComponents.STORED_MUSIC);

        if (music == null || !AetherIIClientProxy.isPlayingSoundEvent(music.sound().value())) {
            return;
        }

        Holder<SoundEvent> sound = Holder.direct(SoundEvent.createVariableRangeEvent(music.sound().value().location()));
        AetherIIClientProxy.stopSoundEvent(sound.value(), SoundSource.RECORDS);
    }
}
