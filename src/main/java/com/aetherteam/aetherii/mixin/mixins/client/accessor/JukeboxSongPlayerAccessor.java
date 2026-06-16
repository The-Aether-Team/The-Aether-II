package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(JukeboxSongPlayer.class)
public interface JukeboxSongPlayerAccessor {
    @Accessor("ticksSinceSongStarted")
    void aether_ii$setTicksSinceSongStarted(long ticksSinceSongStarted);

    @Accessor("song")
    void aether_ii$setSong(Holder<JukeboxSong> song);
}
