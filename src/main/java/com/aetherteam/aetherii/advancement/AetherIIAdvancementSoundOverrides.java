package com.aetherteam.aetherii.advancement;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.AdvancementSoundOverride;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class AetherIIAdvancementSoundOverrides {
    public static final DeferredRegister<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDES = DeferredRegister.create(AetherIIRegistries.ADVANCEMENT_SOUND_OVERRIDE, AetherII.MODID);
    public static final Registry<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY = ADVANCEMENT_SOUND_OVERRIDES.makeRegistry((builder) -> builder.sync(true).defaultKey(AetherIIRegistries.ADVANCEMENT_SOUND_OVERRIDE.location()));

    public static final DeferredHolder<AdvancementSoundOverride, AdvancementSoundOverride> GENERAL = ADVANCEMENT_SOUND_OVERRIDES.register("general", () -> new AdvancementSoundOverride(0, (advancements, advancement) -> checkRoot(advancements, advancement, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, "the_highlands")), AetherIISoundEvents.UI_TOAST_AETHER_GENERAL));

    @Nullable
    public static AdvancementSoundOverride get(ResourceLocation location) {
        return ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.getValue(location);
    }

    /**
     * Retrieves the {@link SoundEvent} to use in an override for the given {@link Advancement}.
     * @param advancement The {@link Advancement}.
     * @return The new {@link SoundEvent}.
     */
    @Nullable
    public static SoundEvent retrieveOverride(ClientAdvancements advancements, AdvancementHolder advancement) {
        @Nullable AdvancementSoundOverride usedOverride = null;
        for (AdvancementSoundOverride override : AetherIIAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY) {
            if (override.matches(advancements, advancement) && (usedOverride == null || override.priority() > usedOverride.priority())) {
                usedOverride = override;
            }
        }
        return usedOverride == null ? null : usedOverride.sound().get();
    }

    /**
     * Checks all the way up to the root of the advancement tree to determine if it matches a given root.
     */
    public static boolean checkRoot(ClientAdvancements advancements, AdvancementHolder holder, ResourceLocation root) {
        for (AdvancementHolder advancement = holder; advancement != null && advancement.value().parent().isPresent(); advancement = advancements.get(advancement.value().parent().get())) {
            if (advancement.value().parent().get().equals(root)) {
                return true;
            }
        }
        return false;
    }
}
