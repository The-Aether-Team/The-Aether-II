package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.StoredMusic;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Consumer;

public class MusicPlayerItem extends Item {
    public MusicPlayerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (!stack.has(AetherIIDataComponents.STORED_MUSIC) && other.has(DataComponents.JUKEBOX_PLAYABLE)) {
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
        if (!stack.has(AetherIIDataComponents.STORED_MUSIC) && other.has(DataComponents.JUKEBOX_PLAYABLE)) {
            Optional<Holder<JukeboxSong>> optional = JukeboxSong.fromStack(player.registryAccess(), other);
            if (optional.isPresent()) {
                stack.set(AetherIIDataComponents.STORED_MUSIC, new StoredMusic(other.getItemHolder(), optional.get().value().soundEvent()));
                other.shrink(1);
                return true;
            }
        } else if (action == ClickAction.SECONDARY && stack.has(AetherIIDataComponents.STORED_MUSIC) && other.isEmpty()) {
            slot.set(stack.get(AetherIIDataComponents.STORED_MUSIC).item().value().getDefaultInstance());
            stack.remove(AetherIIDataComponents.STORED_MUSIC);
            return false;
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        StoredMusic music = stack.get(AetherIIDataComponents.STORED_MUSIC);
        if (music != null && level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            Holder<SoundEvent> sound = music.sound();
            SoundSource category = SoundSource.MASTER;
            Vec3 pos = player.position();
            float volume = 1.0F;
            float pitch = 1.0F;
            double d0 = Mth.square(sound.value().getRange(volume));
            long i = serverLevel.getRandom().nextLong();
            double d1 = pos.x - serverPlayer.getX();
            double d2 = pos.y - serverPlayer.getY();
            double d3 = pos.z - serverPlayer.getZ();
            double d4 = d1 * d1 + d2 * d2 + d3 * d3;
            if (d4 <= d0) {
                serverPlayer.connection.send(new ClientboundSoundPacket(sound, category, pos.x(), pos.y(), pos.z(), volume, pitch, i));
                return InteractionResult.SUCCESS_SERVER;
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
}
