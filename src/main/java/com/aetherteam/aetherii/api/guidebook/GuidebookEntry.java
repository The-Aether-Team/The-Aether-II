package com.aetherteam.aetherii.api.guidebook;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class GuidebookEntry {
    public static final DataTemplate<ResourceLocation> ICON = new DataTemplate<>("icon_discovered", ResourceLocation.CODEC::fieldOf);
    public static final DataTemplate<Optional<String>> NAME = new DataTemplate<>("name", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<Optional<String>> SLOT_NAME = new DataTemplate<>("slot_name", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<Optional<String>> SLOT_SUBTITLE = new DataTemplate<>("slot_subtitle", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<String> DESCRIPTION_KEY = new DataTemplate<>("description_key", Codec.STRING::fieldOf);

    private final Info<ResourceLocation> icon;
    private final Info<Optional<String>> name;
    private final Info<Optional<String>> slotName;
    private final Info<Optional<String>> slotSubtitle;
    private final Info<String> descriptionKey;

    private final Map<String, Info<?>> values = new HashMap<>();

    public GuidebookEntry(ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey) {
        this.icon = this.info(ICON, icon);
        this.name = this.info(NAME, name);
        this.slotName = this.info(SLOT_NAME, slotName);
        this.slotSubtitle = this.info(SLOT_SUBTITLE, slotSubtitle);
        this.descriptionKey = this.info(DESCRIPTION_KEY, descriptionKey);
    }

    public GuidebookEntry(Info<ResourceLocation> icon, Info<Optional<String>> name, Info<Optional<String>> slotName, Info<Optional<String>> slotSubtitle, Info<String> descriptionKey) {
        this.icon = icon;
        this.name = name;
        this.slotName = slotName;
        this.slotSubtitle = slotSubtitle;
        this.descriptionKey = descriptionKey;
    }

    public Info<ResourceLocation> getIcon() {
        return this.icon;
    }

    public Info<Optional<String>> getName() {
        return this.name;
    }

    public Info<Optional<String>> getSlotName() {
        return this.slotName;
    }

    public Info<Optional<String>> getSlotSubtitle() {
        return this.slotSubtitle;
    }

    public Info<String> getDescriptionKey() {
        return this.descriptionKey;
    }

    public Map<String, Info<?>> getValues() {
        return ImmutableMap.copyOf(this.values);
    }

    protected <T> Info<T> info(DataTemplate<T> data, T value) {
        Info<T> info = new Info<>(value, false, false);
        this.values.put(data.id(), info);
        return info;
    }

    public record DataTemplate<T>(String id, Function<String, MapCodec<T>> codec) {
        public MapCodec<T> mapCodec() {
            return this.codec().apply(this.id());
        }
    }

    public static final class Info<T> { //todo this needs a codec and also a "viewed" value.
        private final T value;
        private boolean visible;
        private boolean viewed;

        public Info(T value, boolean visible, boolean viewed) {
            this.value = value;
            this.visible = visible;
            this.viewed = viewed;
        }

        public void reveal() {
            this.visible = true;
        }

        public void view() {
            this.viewed = true;
        }

        public T getValue() {
            return this.value;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public boolean isViewed() {
            return this.viewed;
        }
    }
}
