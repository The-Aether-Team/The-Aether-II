package com.aetherteam.aetherii.advancement;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.AdvancementSoundOverride;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.client.AetherIIClientProxy;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AetherIIAdvancementSoundOverrides {
    public static final DeferredRegister<AdvancementSoundOverride> ADVANCEMENT_SOUND_OVERRIDES = DeferredRegister.create(AetherIIRegistries.ADVANCEMENT_SOUND_OVERRIDE, AetherII.MODID);
    public static final Supplier<IForgeRegistry<AdvancementSoundOverride>> ADVANCEMENT_SOUND_OVERRIDE_REGISTRY = ADVANCEMENT_SOUND_OVERRIDES.makeRegistry(() -> new RegistryBuilder<AdvancementSoundOverride>().setDefaultKey(AetherIIRegistries.ADVANCEMENT_SOUND_OVERRIDE.location()));

    public static final RegistryObject<AdvancementSoundOverride> GENERAL = ADVANCEMENT_SOUND_OVERRIDES.register("general", () -> new AdvancementSoundOverride(0, (advancement) -> checkRoot(advancement, new ResourceLocation(AetherII.MODID, "the_holy_isles")), AetherIISoundEvents.UI_TOAST_AETHER_GENERAL));
    public static final RegistryObject<AdvancementSoundOverride> SLIDER = ADVANCEMENT_SOUND_OVERRIDES.register("slider", () -> new AdvancementSoundOverride(1, (advancement) -> checkAdvancement(advancement, new ResourceLocation(AetherII.MODID, "slider")), AetherIISoundEvents.UI_TOAST_AETHER_SLIDER));
    public static final RegistryObject<AdvancementSoundOverride> EMPTY = ADVANCEMENT_SOUND_OVERRIDES.register("empty", () -> new AdvancementSoundOverride(10, advancement -> checkAdvancement(advancement, new ResourceLocation(AetherII.MODID, "enter_holy_isles")), () -> SoundEvents.EMPTY));

    @Nullable
    public static AdvancementSoundOverride get(ResourceLocation location) {
        return ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get().getValue(location);
    }

    /**
     * Retrieves the {@link SoundEvent} to use in an override for the given {@link Advancement}.
     * @param advancement The {@link Advancement}.
     * @return The new {@link SoundEvent}.
     */
    @Nullable
    public static SoundEvent retrieveOverride(Advancement advancement) {
        @Nullable AdvancementSoundOverride usedOverride = null;
        for (AdvancementSoundOverride override : AetherIIAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get()) {
            if (override.matches(advancement) && (usedOverride == null || override.priority() > usedOverride.priority())) {
                usedOverride = override;
            }
        }
        return usedOverride == null ? null : usedOverride.sound().get();
    }

    /**
     * Checks all the way up to the root of the advancement tree to determine if it matches a given root.
     */
    public static boolean checkRoot(Advancement holder, ResourceLocation root) {
        for (Advancement advancement = holder; advancement != null && advancement.getParent() != null; advancement = AetherIIClientProxy.getAdvancementParent(advancement)) {
            if (advancement.getParent().getId().equals(root)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for a specific advancement
     */
    public static boolean checkAdvancement(Advancement holder, ResourceLocation root) {
        return holder.getId().equals(root);
    }
}
