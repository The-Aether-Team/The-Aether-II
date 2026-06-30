package com.aetherteam.aetherii.api.guidebook;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GuidebookEntry {
    public static final DataTemplate<ResourceLocation> ID = new DataTemplate<>("id", ResourceLocation.CODEC::fieldOf);
    public static final DataTemplate<ResourceLocation> ICON = new DataTemplate<>("icon", ResourceLocation.CODEC::fieldOf);
    public static final DataTemplate<String> NAME = new DataTemplate<>("name", Codec.STRING::fieldOf);
    public static final DataTemplate<String> SLOT_NAME = new DataTemplate<>("slot_name", Codec.STRING::fieldOf);
    public static final DataTemplate<Optional<String>> SLOT_SUBTITLE = new DataTemplate<>("slot_subtitle", Codec.STRING::optionalFieldOf);
    public static final DataTemplate<String> DESCRIPTION_KEY = new DataTemplate<>("description_key", Codec.STRING::fieldOf);

    public static final MapCodec<GuidebookEntry> MAP_CODEC =
            RecordCodecBuilder.mapCodec(in -> in.group(
                    BestiaryEntry.ID.mapCodec().forGetter(GuidebookEntry::getId),
                    BestiaryEntry.ICON.mapCodec().forGetter(GuidebookEntry::getIcon),
                    BestiaryEntry.NAME.mapCodec().forGetter(GuidebookEntry::getName),
                    BestiaryEntry.SLOT_NAME.mapCodec().forGetter(GuidebookEntry::getSlotName),
                    BestiaryEntry.SLOT_SUBTITLE.mapCodec().forGetter(GuidebookEntry::getSlotSubtitle),
                    BestiaryEntry.DESCRIPTION_KEY.mapCodec().forGetter(GuidebookEntry::getDescriptionKey)
            ).apply(in, GuidebookEntry::new));

    private final ResourceLocation id;
    private final ResourceLocation icon;
    private final String name;
    private final String slotName;
    private final Optional<String> slotSubtitle;
    private final String descriptionKey;

    protected final Map<String, Info> values = new HashMap<>();

    public GuidebookEntry(ResourceLocation id, ResourceLocation icon, String name, String slotName, Optional<String> slotSubtitle, String descriptionKey) {
        this.id = this.info(ID, id);
        this.icon = this.info(ICON, icon);
        this.name = this.info(NAME, name);
        this.slotName = this.info(SLOT_NAME, slotName);
        this.slotSubtitle = this.info(SLOT_SUBTITLE, slotSubtitle);
        this.descriptionKey = this.info(DESCRIPTION_KEY, descriptionKey);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public ResourceLocation getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public String getSlotName() {
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
        return this.info(data.id(), value);
    }

    protected <T> T info(String id, T value) {
        this.values.put(id, new Info(false, false));
        return value;
    }

    public GuidebookEntry root() {
        return this;
    }

    public record DataTemplate<T>(String id, Function<String, MapCodec<T>> codec) {
        public String getId() {
            return this.id();
        }

        public MapCodec<T> mapCodec() {
            return this.codec().apply(this.id());
        }
    }

    public static final class Info {
        public static final Codec<Info> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("visible").forGetter(Info::isVisible),
                Codec.BOOL.fieldOf("viewed").forGetter(Info::isViewed)
        ).apply(instance, Info::new));
        public static final StreamCodec<FriendlyByteBuf, Info> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL, Info::isVisible,
                ByteBufCodecs.BOOL, Info::isViewed,
                Info::new);

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
