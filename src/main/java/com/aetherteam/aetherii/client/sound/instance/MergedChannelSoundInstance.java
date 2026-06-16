package com.aetherteam.aetherii.client.sound.instance;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.FloatSampleSource;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MergedChannelSoundInstance extends SimpleSoundInstance {
    public MergedChannelSoundInstance(Identifier location, SoundSource source, float volume, float pitch, RandomSource random, boolean looping, int delay, Attenuation attenuation, double x, double y, double z, boolean relative) {
        super(location, source, volume, pitch, random, looping, delay, attenuation, x, y, z, relative);
    }

    @Override
    public CompletableFuture<AudioStream> getStream(SoundBufferLibrary soundBuffers, Sound sound, boolean looping) {
        return super.getStream(soundBuffers, sound, looping).thenApply((audioStream) -> {
            if (audioStream instanceof FloatSampleSource floatSampleSource) {
                return new MergedChannelSampleSource(floatSampleSource);
            }
            return audioStream;
        });
    }

    public static MergedChannelSoundInstance forSong(SoundEvent sound, Vec3 pos) {
        return new MergedChannelSoundInstance(sound.location(), SoundSource.RECORDS, 4.0F, 1.0F, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.LINEAR, pos.x, pos.y, pos.z, false);
    }

    private static class MergedChannelSampleSource implements FloatSampleSource {
        private FloatSampleSource delegate;

        public MergedChannelSampleSource(FloatSampleSource delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean readChunk(FloatConsumer floatConsumer) throws IOException {
            if (this.delegate.getFormat().getChannels() == 2) {
                FloatArrayList samples = new FloatArrayList();
                if (this.delegate.readChunk(samples::add)) {
                    for (int i = 0; i < samples.size(); i += 2) {
                        float left = samples.getFloat(i);
                        float right = samples.getFloat(i + 1);
                        float average = (left + right) / 2;
                        floatConsumer.accept(average);
                    }
                    return true;
                }
            }
            return this.delegate.readChunk(floatConsumer);
        }

        @Override
        public AudioFormat getFormat() {
            AudioFormat delegateFormat = this.delegate.getFormat();
            return new AudioFormat(delegateFormat.getSampleRate(), delegateFormat.getSampleSizeInBits(), 1, true, false);
        }

        @Override
        public void close() throws IOException {
            this.delegate.close();
        }
    }
}
