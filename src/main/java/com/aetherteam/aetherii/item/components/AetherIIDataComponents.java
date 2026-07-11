package com.aetherteam.aetherii.item.components;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.api.ItemReinforcement;
import com.aetherteam.aetherii.api.Mural;
import com.aetherteam.aetherii.api.registries.AetherIIRegistries;
import com.aetherteam.aetherii.blockentity.MuralSection;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.UUID;

public class AetherIIDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AetherII.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Moa.FeatherColor>> FEATHER_COLOR = DATA_COMPONENT_TYPES.register("feather_color", () -> DataComponentType.<Moa.FeatherColor>builder().persistent(Moa.FeatherColor.CODEC).networkSynchronized(Moa.FeatherColor.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MoaEggType>> MOA_EGG_TYPE = DATA_COMPONENT_TYPES.register("moa_egg_type", () -> DataComponentType.<MoaEggType>builder().persistent(MoaEggType.CODEC).networkSynchronized(MoaEggType.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MoaVariant>> MOA_VARIANT = DATA_COMPONENT_TYPES.register("moa/variant", () -> DataComponentType.<MoaVariant>builder().persistent(MoaVariant.CODEC).networkSynchronized(MoaVariant.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HEALING_STONE_CHARGES = DATA_COMPONENT_TYPES.register("healing_stone_charges", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.intRange(0, 5)).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ArmorStyle>> ARMOR_STYLE = DATA_COMPONENT_TYPES.register("armor_style", () -> DataComponentType.<ArmorStyle>builder().persistent(ArmorStyle.CODEC).networkSynchronized(ArmorStyle.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TagKey<Item>>> ARMOR_SET = DATA_COMPONENT_TYPES.register("armor_set", () -> DataComponentType.<TagKey<Item>>builder().persistent(TagKey.codec(Registries.ITEM)).networkSynchronized(TagKey.streamCodec(Registries.ITEM)).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DARTS_LOADED = DATA_COMPONENT_TYPES.register("darts_loaded", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BuildupContents>> BUILDUP_CONTENTS = DATA_COMPONENT_TYPES.register("buildup_contents", () -> DataComponentType.<BuildupContents>builder().persistent(BuildupContents.CODEC).networkSynchronized(BuildupContents.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<ItemReinforcement>>> ITEM_REINFORCEMENTS = DATA_COMPONENT_TYPES.register("item_reinforcement", () -> DataComponentType.<ResourceKey<ItemReinforcement>>builder().persistent(ResourceKey.codec(AetherIIRegistries.ITEM_REINFORCEMENT)).networkSynchronized(ResourceKey.streamCodec(AetherIIRegistries.ITEM_REINFORCEMENT)).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ReinforcementTier>> REINFORCEMENT_TIER = DATA_COMPONENT_TYPES.register("reinforcement_tier", () -> DataComponentType.<ReinforcementTier>builder().persistent(ReinforcementTier.CODEC).networkSynchronized(ReinforcementTier.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Charms>> CHARMS = DATA_COMPONENT_TYPES.register("charms", () -> DataComponentType.<Charms>builder().persistent(Charms.CODEC).networkSynchronized(Charms.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<GuidebookEntryData>>> GUIDEBOOK_ENTRY_DATA = DATA_COMPONENT_TYPES.register("guidebook_entry_data", () -> DataComponentType.<List<GuidebookEntryData>>builder().persistent(GuidebookEntryData.CODEC.listOf()).networkSynchronized(GuidebookEntryData.STREAM_CODEC.apply(ByteBufCodecs.list())).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MuralSection>> MURAL_SECTION = DATA_COMPONENT_TYPES.register("mural_section", () -> DataComponentType.<MuralSection>builder().persistent(MuralSection.CODEC).networkSynchronized(MuralSection.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Mural>>> MURAL = DATA_COMPONENT_TYPES.register("mural", () -> DataComponentType.<Holder<Mural>>builder().persistent(Mural.CODEC).networkSynchronized(Mural.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockState>> BLOCK_STATE = DATA_COMPONENT_TYPES.register("block_state", () -> DataComponentType.<BlockState>builder().persistent(BlockState.CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> COMPANION_UUID = DATA_COMPONENT_TYPES.register("companion_uuid", () -> DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CompoundTag>> COMPANION_NBT = DATA_COMPONENT_TYPES.register("companion_tag", () -> DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> MIMIC = DATA_COMPONENT_TYPES.register("mimic", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<EngravedDisc>> ENGRAVED_DISC = DATA_COMPONENT_TYPES.register("engraved_disc", () -> DataComponentType.<EngravedDisc>builder().persistent(EngravedDisc.CODEC).networkSynchronized(EngravedDisc.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StoredMusic>> STORED_MUSIC = DATA_COMPONENT_TYPES.register("stored_music", () -> DataComponentType.<StoredMusic>builder().persistent(StoredMusic.CODEC).networkSynchronized(StoredMusic.STREAM_CODEC).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BrokenStack>> BROKEN_STACK = DATA_COMPONENT_TYPES.register("broken_stack", () -> DataComponentType.<BrokenStack>builder().persistent(BrokenStack.CODEC).networkSynchronized(BrokenStack.STREAM_CODEC).build());
}