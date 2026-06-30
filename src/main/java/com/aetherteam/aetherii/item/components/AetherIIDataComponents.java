package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import com.aetherteam.aetherii.item.components.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.IdentityHashMap;
import java.util.Map;

public class AetherIIDataComponents {
    public static final DataComponentType<Moa.FeatherColor> FEATHER_COLOR = component("feather_color", Moa.FeatherColor.CODEC);
    public static final DataComponentType<MoaEggType> MOA_EGG_TYPE = component("moa_egg_type", MoaEggType.CODEC);
    public static final DataComponentType<MoaVariant> MOA_VARIANT = component("moa/variant", MoaVariant.CODEC);
    public static final DataComponentType<Integer> HEALING_STONE_CHARGES = component("healing_stone_charges", Codec.INT);
    public static final DataComponentType<ArmorStyle> ARMOR_STYLE = component("armor_style", ArmorStyle.CODEC);
    public static final DataComponentType<TagKey<Item>> ARMOR_SET = component("armor_set", TagKey.codec(net.minecraft.core.registries.Registries.ITEM));
    public static final DataComponentType<Integer> DARTS_LOADED = component("darts_loaded", Codec.INT);
    public static final DataComponentType<BuildupContents> BUILDUP_CONTENTS = component("buildup_contents", BuildupContents.CODEC);
    public static final DataComponentType<ResourceKey<ItemReinforcement>> ITEM_REINFORCEMENTS = component("item_reinforcement", ResourceKey.codec(AetherIIRegistries.ITEM_REINFORCEMENT));
    public static final DataComponentType<ReinforcementTier> REINFORCEMENT_TIER = component("reinforcement_tier", ReinforcementTier.CODEC);
    public static final DataComponentType<Charms> CHARMS = component("charms", Charms.CODEC);
    public static final DataComponentType<List<GuidebookEntryData>> GUIDEBOOK_ENTRY_DATA = component("guidebook_entry_data", GuidebookEntryData.CODEC.listOf());
    public static final DataComponentType<MuralSection> MURAL_SECTION = component("mural_section", MuralSection.CODEC);
    public static final DataComponentType<Holder<Mural>> MURAL = component("mural", Mural.CODEC);
    public static final DataComponentType<BlockState> BLOCK_STATE = component("block_state", BlockState.CODEC);
    public static final DataComponentType<UUID> COMPANION_UUID = component("companion_uuid", UUIDUtil.CODEC);
    public static final DataComponentType<CompoundTag> COMPANION_NBT = component("companion_tag", CompoundTag.CODEC);
    public static final DataComponentType<Boolean> MIMIC = component("mimic", Codec.BOOL);
    public static final DataComponentType<EngravedDisc> ENGRAVED_DISC = component("engraved_disc", EngravedDisc.CODEC);
    public static final DataComponentType<StoredMusic> STORED_MUSIC = component("stored_music", StoredMusic.CODEC);
    public static final DataComponentType<BrokenStack> BROKEN_STACK = component("broken_stack", BrokenStack.CODEC);

    private static final String TAG = AetherII.MODID + "_components";
    private static final Map<Item, Map<DataComponentType<?>, Object>> DEFAULTS = new IdentityHashMap<>();

    private static <T> DataComponentType<T> component(String id, Codec<T> codec) {
        return new DataComponentType<>(new ResourceLocation(AetherII.MODID, id), codec);
    }

    public static <T> boolean has(ItemStack stack, DataComponentType<T> type) {
        CompoundTag root = stack.getTag();
        if (root != null && root.contains(TAG) && root.getCompound(TAG).contains(type.id().toString())) {
            return true;
        }
        Map<DataComponentType<?>, Object> defaults = DEFAULTS.get(stack.getItem());
        return defaults != null && defaults.containsKey(type);
    }

    public static <T> T get(ItemStack stack, DataComponentType<T> type) {
        CompoundTag root = stack.getTag();
        if (root != null && root.contains(TAG)) {
            CompoundTag components = root.getCompound(TAG);
            String key = type.id().toString();
            if (components.contains(key)) {
                DataResult<T> result = type.codecOrThrow().parse(NbtOps.INSTANCE, components.get(key));
                return result.result().orElse(null);
            }
        }
        return getDefault(stack, type);
    }

    public static <T> T getOrDefault(ItemStack stack, DataComponentType<T> type, T defaultValue) {
        T value = get(stack, type);
        return value != null ? value : defaultValue;
    }

    public static <T> void set(ItemStack stack, DataComponentType<T> type, T value) {
        if (value == null) {
            remove(stack, type);
            return;
        }
        CompoundTag root = stack.getOrCreateTag();
        CompoundTag components = root.getCompound(TAG);
        type.codecOrThrow().encodeStart(NbtOps.INSTANCE, value).result().ifPresent(tag -> {
            components.put(type.id().toString(), tag);
            root.put(TAG, components);
        });
    }

    public static <T> void remove(ItemStack stack, DataComponentType<T> type) {
        CompoundTag root = stack.getTag();
        if (root != null && root.contains(TAG)) {
            CompoundTag components = root.getCompound(TAG);
            components.remove(type.id().toString());
            if (components.isEmpty()) {
                root.remove(TAG);
            } else {
                root.put(TAG, components);
            }
        }
    }

    public static void registerDefaults(Item item, Map<DataComponentType<?>, Object> defaults) {
        if (!defaults.isEmpty()) {
            DEFAULTS.put(item, Map.copyOf(defaults));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getDefault(ItemStack stack, DataComponentType<T> type) {
        Map<DataComponentType<?>, Object> defaults = DEFAULTS.get(stack.getItem());
        return defaults != null ? (T) defaults.get(type) : null;
    }
}
