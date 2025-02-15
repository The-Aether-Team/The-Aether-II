package com.aetherteam.aetherii.api.guidebook;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class GuidebookEntry {
    public static final DataTemplate<ResourceLocation> ICON = new DataTemplate<>("icon", ResourceLocation.CODEC::fieldOf);
    public static final DataTemplate<Optional<String>> NAME = new DataTemplate<>("name", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<Optional<String>> SLOT_NAME = new DataTemplate<>("slot_name", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<Optional<String>> SLOT_SUBTITLE = new DataTemplate<>("slot_subtitle", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<String> DESCRIPTION_KEY = new DataTemplate<>("description_key", Codec.STRING::fieldOf);

    private final ResourceLocation icon;
    private final Optional<String> name;
    private final Optional<String> slotName;
    private final Optional<String> slotSubtitle;
    private final String descriptionKey;

    protected final Map<String, Info> values = new HashMap<>();

    public GuidebookEntry(ResourceLocation icon, Optional<String> name, Optional<String> slotName, Optional<String> slotSubtitle, String descriptionKey) {
        this.icon = this.info(ICON, icon);
        this.name = this.info(NAME, name);
        this.slotName = this.info(SLOT_NAME, slotName);
        this.slotSubtitle = this.info(SLOT_SUBTITLE, slotSubtitle);
        this.descriptionKey = this.info(DESCRIPTION_KEY, descriptionKey);
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }

    public Optional<String> getName() {
        return this.name;
    }

    public Optional<String> getSlotName() {
        return this.slotName;
    }

    public Optional<String> getSlotSubtitle() {
        return this.slotSubtitle;
    }

    public String getDescriptionKey() {
        return this.descriptionKey;
    }

    public Map<String, Info> getValues() {
        return ImmutableMap.copyOf(this.values);
    }

    protected <T> T info(DataTemplate<T> data, T value) {
        this.values.put(data.id(), new Info(false, false));
        return value;
    }

    public record DataTemplate<T>(String id, Function<String, MapCodec<T>> codec) {
        public MapCodec<T> mapCodec() {
            return this.codec().apply(this.id());
        }
    }

    public static final class Info {
        public static final Codec<Info> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("visible").forGetter(Info::isVisible),
                Codec.BOOL.fieldOf("viewed").forGetter(Info::isViewed)
        ).apply(instance, Info::new));

        private boolean visible;
        private boolean viewed;

        public Info(boolean visible, boolean viewed) {
            this.visible = visible;
            this.viewed = viewed;
        }

        public void reveal() {
            this.visible = true;
        }

        public void view() {
            this.viewed = true;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public boolean isViewed() {
            return this.viewed;
        }

        @Override
        public String toString() {
            return "Info{" + "visible=" + this.isVisible() + ", viewed=" + this.isViewed() + '}';
        }
    }
}
