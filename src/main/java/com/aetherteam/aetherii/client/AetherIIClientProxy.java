package com.aetherteam.aetherii.client;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.sound.instance.MergedChannelSoundInstance;
import com.aetherteam.aetherii.client.sound.instance.MusicPlayerSoundInstance;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import com.aetherteam.aetherii.item.components.JukeboxSong;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AetherIIClientProxy {
    public static boolean isMusicPlayerActive(SoundEvent soundEvent) {
        return false;
    }

    public static void startMusicPlayer(Holder<SoundEvent> sound, SoundSource source, double x, double y, double z, float volume, float pitch, long seed) {
        Minecraft.getInstance().getSoundManager().play(new MusicPlayerSoundInstance(sound.value(), source, volume, pitch, net.minecraft.util.RandomSource.create(seed), x, y, z));
    }

    public static void onMusicPlayerStart(Holder<JukeboxSong> song) {
        Minecraft.getInstance().gui.setNowPlaying(song.value().description());
    }

    public static void stopMusicPlayer(SoundEvent soundEvent, SoundSource source) {
        Minecraft.getInstance().getSoundManager().stop(soundEvent.getLocation(), source);
    }

    public static void stopOtherMusicPlayerSound(SoundSource source) {
    }

    public static void playMusicBlock(Holder<JukeboxSong> songHolder, BlockPos pos) {
        if (Minecraft.getInstance().level == null) {
            return;
        }
        JukeboxSong song = songHolder.value();
        SoundInstance instance = MergedChannelSoundInstance.forSong(song.soundEvent().value(), Vec3.atCenterOf(pos));
        Minecraft.getInstance().getSoundManager().play(instance);
        Minecraft.getInstance().gui.setNowPlaying(song.description());
        for (LivingEntity entity : Minecraft.getInstance().level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(3.0))) {
            entity.setRecordPlayingNearby(pos, true);
        }
    }

    public static boolean isAerbunnyInteractable() {
        return AetherIIKeyMappings.ALLOW_DISMOUNTING_PASSENGER.isDown();
    }

    @Nullable
    public static Advancement getAdvancementParent(Advancement advancement) {
        if (Minecraft.getInstance().player != null && advancement.getParent() != null) {
            return Minecraft.getInstance().player.connection.getAdvancements().getAdvancements().get(advancement.getParent().getId());
        }
        return null;
    }

    public static void setSectionDirty(SectionPos pos) {
        if (Minecraft.getInstance().levelRenderer != null) {
            Minecraft.getInstance().levelRenderer.setSectionDirty(pos.x(), pos.y(), pos.z());
        }
    }

    @Nullable
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Nullable
    public static <T> T getClientPlayerData(Supplier<AetherIIDataAttachments.AttachmentType<T>> holder) {
        if (Minecraft.getInstance().player != null) {
            return AetherIIDataAttachments.get(Minecraft.getInstance().player, holder);
        }
        return null;
    }
}
