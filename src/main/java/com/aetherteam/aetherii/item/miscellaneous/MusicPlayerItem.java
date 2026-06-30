package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.DataComponentGetter;
import com.aetherteam.aetherii.item.components.EngravedDisc;
import com.aetherteam.aetherii.item.components.JukeboxSong;
import com.aetherteam.aetherii.item.components.StoredMusic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import com.aetherteam.aetherii.item.components.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MusicPlayerItem extends Item {
    public MusicPlayerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!AetherIIDataComponents.has(stack, AetherIIDataComponents.STORED_MUSIC) && AetherIIDataComponents.has(other, AetherIIDataComponents.ENGRAVED_DISC) && other.is(AetherIITags.Items.ENGRAVED_DISCS)) {
            Optional<Holder<JukeboxSong>> optional = EngravedDisc.getSong(other);
            if (optional.isPresent()) {
                AetherIIDataComponents.set(stack, AetherIIDataComponents.STORED_MUSIC, new StoredMusic(other.getItemHolder(), optional.get()));
                other.shrink(1);
                return true;
            }
        }
        return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        ItemStack other = slot.getItem();
        if (!AetherIIDataComponents.has(stack, AetherIIDataComponents.STORED_MUSIC) && AetherIIDataComponents.has(other, AetherIIDataComponents.ENGRAVED_DISC) && other.is(AetherIITags.Items.ENGRAVED_DISCS)) {
            Optional<Holder<JukeboxSong>> optional = EngravedDisc.getSong(other);
            if (optional.isPresent()) {
                AetherIIDataComponents.set(stack, AetherIIDataComponents.STORED_MUSIC, new StoredMusic(other.getItemHolder(), optional.get()));
                other.shrink(1);
                return true;
            }
        } else if (action == ClickAction.SECONDARY && AetherIIDataComponents.has(stack, AetherIIDataComponents.STORED_MUSIC) && other.isEmpty()) {
            StoredMusic music = AetherIIDataComponents.get(stack, AetherIIDataComponents.STORED_MUSIC);
            Holder<SoundEvent> sound = music.getSoundEvent();
            SoundSource category = SoundSource.RECORDS;
            slot.set(AetherIIDataComponents.get(stack, AetherIIDataComponents.STORED_MUSIC).item().value().getDefaultInstance());
            AetherIIDataComponents.remove(stack, AetherIIDataComponents.STORED_MUSIC);
            if (player.level().isClientSide()) {
                AetherIIClientProxy.stopMusicPlayer(sound.value(), category);
            }
            return false;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        StoredMusic music = AetherIIDataComponents.get(stack, AetherIIDataComponents.STORED_MUSIC);
        if (music != null && player.level().isClientSide()) {
            Holder<SoundEvent> sound = music.getSoundEvent();
            SoundSource category = SoundSource.RECORDS;
            if (!AetherIIClientProxy.isMusicPlayerActive(sound.value())) {
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
                    AetherIIClientProxy.startMusicPlayer(sound, category, pos.x(), pos.y(), pos.z(), volume, pitch, i);
                    AetherIIClientProxy.onMusicPlayerStart(music.song());
                    return InteractionResultHolder.success(stack);
                }
            } else {
                AetherIIClientProxy.stopMusicPlayer(sound.value(), category);
                return InteractionResultHolder.success(stack);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.level.Level level, List<Component> tooltipAdder, TooltipFlag flag) { //todo
        tooltipAdder.add(Component.literal("Stored Song:").withStyle(ChatFormatting.AQUA));
        StoredMusic music = AetherIIDataComponents.get(stack, AetherIIDataComponents.STORED_MUSIC);
        if (music != null) {
            Holder<Item> item = music.item();
            ItemStack discStack = item.value().getDefaultInstance();
            EngravedDisc song = AetherIIDataComponents.get(discStack, AetherIIDataComponents.ENGRAVED_DISC);
            if (song != null) {
                song.addToTooltip(level, tooltipAdder::add, flag, DataComponentGetter.EMPTY);
            }
        }
    }

    public static void entityPostTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END && player.level().isClientSide() && player.tickCount % 5 == 0) {
            boolean flag = false;
            List<ItemStack> stacks = new ArrayList<>();
            stacks.addAll(player.inventoryMenu.getItems());
            stacks.add(player.inventoryMenu.getCarried());
            stacks.add(player.containerMenu.getCarried());
            for (ItemStack stack : stacks) {
                if (stack.is(AetherIIItems.MUSIC_PLAYER.get())) {
                    flag = true;
                }
            }
            if (!flag) {
                AetherIIClientProxy.stopOtherMusicPlayerSound(SoundSource.RECORDS);
            }
        }
    }
}
