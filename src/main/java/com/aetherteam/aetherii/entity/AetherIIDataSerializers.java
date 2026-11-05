package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.monster.dungeon.BladeshroomHunter;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class AetherIIDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, AetherII.MODID);

    public static final StreamCodec<ByteBuf, CompoundTag> COMPOUND_TAG_STREAM_CODEC = new StreamCodec<ByteBuf, CompoundTag>() {
        public CompoundTag decode(ByteBuf p_331901_) {
            return FriendlyByteBuf.readNbt(p_331901_);
        }

        public void encode(ByteBuf p_331539_, CompoundTag p_455271_) {
            FriendlyByteBuf.writeNbt(p_331539_, p_455271_);
        }
    };


    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Sheepuff.SheepuffColor>> SHEEPUFF_COLOR = ENTITY_DATA_SERIALIZERS.register("sheepuff_color", () -> EntityDataSerializer.forValueType(Sheepuff.SheepuffColor.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Kirrid.KirridColor>>> OPTIONAL_KIRRID_COLOR = ENTITY_DATA_SERIALIZERS.register("optional_kirrid_color", () -> EntityDataSerializer.forValueType(Kirrid.KirridColor.STREAM_CODEC.apply(ByteBufCodecs::optional)));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<SkyrootLizardVariant>>> SKYROOT_LIZARD_VARIANT = ENTITY_DATA_SERIALIZERS.register("skyroot_lizard_variant", () -> EntityDataSerializer.forValueType(SkyrootLizardVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<GlitterwingVariant>>> GLITTERWING_VARIANT = ENTITY_DATA_SERIALIZERS.register("glitterwing_variant", () -> EntityDataSerializer.forValueType(GlitterwingVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Holder<ShroudwingVariant>>> SHROUDWING_VARIANT = ENTITY_DATA_SERIALIZERS.register("shroudwing_variant", () -> EntityDataSerializer.forValueType(ShroudwingVariant.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<BladeshroomHunter.State>> BLADESHROOM_HUNTER_STATE = ENTITY_DATA_SERIALIZERS.register("bladeshroom_hunter_state", () -> EntityDataSerializer.forValueType(BladeshroomHunter.State.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<CloudSkiff.SteeringState>> CLOUD_SKIFF_STEERING_STATE = ENTITY_DATA_SERIALIZERS.register("cloud_skiff_steering_state", () -> EntityDataSerializer.forValueType(CloudSkiff.SteeringState.STREAM_CODEC));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<CompoundTag>> COMPOUND_TAG = ENTITY_DATA_SERIALIZERS.register("compound_tag", () -> EntityDataSerializer.forValueType(COMPOUND_TAG_STREAM_CODEC));
}
