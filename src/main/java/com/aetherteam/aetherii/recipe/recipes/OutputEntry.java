package com.aetherteam.aetherii.recipe.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.aetherteam.aetherii.item.components.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import com.aetherteam.aetherii.util.random.Weighted;
import com.aetherteam.aetherii.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherii.item.components.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutputEntry {
    public static final Codec<BaseEntry> ENTRY_CODEC = Codec.unit(new EmptyEntry());
    public static final StreamCodec<FriendlyByteBuf, BaseEntry> ENTRY_STREAM_CODEC = StreamCodec.of(OutputEntry::toNetwork, OutputEntry::fromNetwork);

    public static BaseEntry fromJson(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return new EmptyEntry();
        }
        if (element.isJsonPrimitive()) {
            return new ItemEntry(templateFromJson(element));
        }
        JsonObject object = GsonHelper.convertToJsonObject(element, "output entry");
        String type = GsonHelper.getAsString(object, "type", "item");
        JsonElement value = object.has("value") ? object.get("value") : object;
        return switch (type) {
            case "list" -> listFromJson(value);
            case "empty" -> new EmptyEntry();
            default -> new ItemEntry(templateFromJson(value));
        };
    }

    public static JsonElement toJson(BaseEntry entry) {
        JsonObject object = new JsonObject();
        object.addProperty("type", entry.type().getSerializedName());
        if (entry instanceof ItemEntry itemEntry) {
            object.add("value", templateToJson(itemEntry.stack()));
        } else if (entry instanceof ListEntry listEntry) {
            JsonArray array = new JsonArray();
            for (Weighted<BaseEntry> weighted : listEntry.entries().unwrap()) {
                JsonObject weightedObject = new JsonObject();
                weightedObject.add("data", toJson(weighted.value()));
                weightedObject.addProperty("weight", weighted.weight());
                array.add(weightedObject);
            }
            object.add("value", array);
        } else {
            object.add("value", new JsonObject());
        }
        return object;
    }

    public static BaseEntry fromNetwork(FriendlyByteBuf buffer) {
        EntryType type = buffer.readEnum(EntryType.class);
        return switch (type) {
            case ITEM -> new ItemEntry(buffer.readItem());
            case LIST -> {
                WeightedList.Builder<BaseEntry> builder = WeightedList.builder();
                int size = buffer.readVarInt();
                for (int i = 0; i < size; i++) {
                    builder.add(fromNetwork(buffer), buffer.readVarInt());
                }
                yield new ListEntry(builder.build());
            }
            case EMPTY -> new EmptyEntry();
        };
    }

    public static void toNetwork(FriendlyByteBuf buffer, BaseEntry entry) {
        buffer.writeEnum(entry.type());
        if (entry instanceof ItemEntry itemEntry) {
            buffer.writeItem(itemEntry.stack().create());
        } else if (entry instanceof ListEntry listEntry) {
            buffer.writeVarInt(listEntry.entries().unwrap().size());
            for (Weighted<BaseEntry> weighted : listEntry.entries().unwrap()) {
                toNetwork(buffer, weighted.value());
                buffer.writeVarInt(weighted.weight());
            }
        }
    }

    public static ItemStackTemplate templateFromJson(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return new ItemStackTemplate(Items.AIR);
        }
        if (element.isJsonPrimitive()) {
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(element.getAsString()));
            return new ItemStackTemplate(item == null ? Items.AIR : item);
        }
        JsonObject object = GsonHelper.convertToJsonObject(element, "item output");
        String itemId = object.has("id") ? GsonHelper.getAsString(object, "id") : GsonHelper.getAsString(object, "item", "minecraft:air");
        int count = GsonHelper.getAsInt(object, "count", 1);
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(itemId));
        DataComponentPatch components = object.has("components") ? DataComponentPatch.fromJson(GsonHelper.getAsJsonObject(object, "components")) : null;
        return new ItemStackTemplate(item == null ? Items.AIR : item, count, components);
    }

    public static JsonObject templateToJson(ItemStackTemplate template) {
        JsonObject object = new JsonObject();
        object.addProperty("id", BuiltInRegistries.ITEM.getKey(template.item()).toString());
        if (template.count() != 1) {
            object.addProperty("count", template.count());
        }
        if (template.components() != null && !template.components().values().isEmpty()) {
            object.add("components", template.components().toJson());
        }
        return object;
    }

    private static BaseEntry listFromJson(JsonElement value) {
        WeightedList.Builder<BaseEntry> builder = WeightedList.builder();
        JsonArray array = GsonHelper.convertToJsonArray(value, "weighted output list");
        for (JsonElement element : array) {
            JsonObject object = GsonHelper.convertToJsonObject(element, "weighted output");
            builder.add(fromJson(object.get("data")), GsonHelper.getAsInt(object, "weight", 1));
        }
        return new ListEntry(builder.build());
    }

    public record ListEntry(WeightedList<BaseEntry> entries) implements BaseEntry {
        @Override
        public List<ItemStackTemplate> list() {
            List<ItemStackTemplate> stacks = new ArrayList<>();
            this.entries().unwrap().stream().map(Weighted::value).forEach((baseEntry) -> stacks.addAll(baseEntry.list()));
            return stacks;
        }

        @Override
        public ItemStack process(RandomSource random) {
            return !this.entries().isEmpty() ? this.entries().getRandomOrThrow(random).process(random) : ItemStack.EMPTY;
        }

        @Override
        public EntryType type() {
            return EntryType.LIST;
        }
    }

    public record ItemEntry(ItemStackTemplate stack) implements BaseEntry {
        public ItemEntry(ItemLike itemLike) {
            this(new ItemStackTemplate(itemLike.asItem()));
        }

        public ItemEntry(ItemStack stack) {
            this(ItemStackTemplate.fromNonEmptyStack(stack));
        }

        @Override
        public List<ItemStackTemplate> list() {
            return List.of(this.stack());
        }

        @Override
        public ItemStack process(RandomSource random) {
            return this.stack().create();
        }

        @Override
        public EntryType type() {
            return EntryType.ITEM;
        }
    }

    public record EmptyEntry() implements BaseEntry {
        @Override
        public List<ItemStackTemplate> list() {
            return List.of();
        }

        @Override
        public ItemStack process(RandomSource random) {
            return ItemStack.EMPTY;
        }

        @Override
        public EntryType type() {
            return EntryType.EMPTY;
        }
    }

    public interface BaseEntry {
        List<ItemStackTemplate> list();

        ItemStack process(RandomSource random);

        EntryType type();
    }

    public enum EntryType implements StringRepresentable {
        LIST,
        ITEM,
        EMPTY;

        public static final StringRepresentable.EnumCodec<EntryType> CODEC = StringRepresentable.fromEnum(EntryType::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
