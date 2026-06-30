package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.monster.BladeshroomHunter;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.aetherteam.aetherii.entity.variant.GlitterwingVariant;
import com.aetherteam.aetherii.entity.variant.ShroudwingVariant;
import com.aetherteam.aetherii.entity.variant.SkyrootLizardVariant;
import com.aetherteam.aetherii.entity.vehicle.CloudSkiff;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import com.aetherteam.aetherii.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class AetherIIDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, AetherII.MODID);
    public static final EntityDataSerializer<Optional<EntityReference<LivingEntity>>> OPTIONAL_LIVING_ENTITY_REFERENCE = EntityDataSerializer.optional(
            (buf, reference) -> buf.writeUUID(reference.getUUID()),
            buf -> EntityReference.of(buf.readUUID()));

    public static final RegistryObject<EntityDataSerializer<Sheepuff.SheepuffColor>> SHEEPUFF_COLOR = ENTITY_DATA_SERIALIZERS.register("sheepuff_color", () -> EntityDataSerializer.simple(
            (buf, color) -> buf.writeVarInt(color.id()),
            buf -> Sheepuff.SheepuffColor.BY_ID.apply(buf.readVarInt())));
    public static final RegistryObject<EntityDataSerializer<Optional<Kirrid.KirridColor>>> OPTIONAL_KIRRID_COLOR = ENTITY_DATA_SERIALIZERS.register("optional_kirrid_color", () -> EntityDataSerializer.optional(
            (buf, color) -> buf.writeVarInt(color.id()),
            buf -> Kirrid.KirridColor.BY_ID.apply(buf.readVarInt())));
    public static final RegistryObject<EntityDataSerializer<Holder<SkyrootLizardVariant>>> SKYROOT_LIZARD_VARIANT = ENTITY_DATA_SERIALIZERS.register("skyroot_lizard_variant", () -> holder(SkyrootLizardVariant.DIRECT_CODEC));
    public static final RegistryObject<EntityDataSerializer<Holder<GlitterwingVariant>>> GLITTERWING_VARIANT = ENTITY_DATA_SERIALIZERS.register("glitterwing_variant", () -> holder(GlitterwingVariant.DIRECT_CODEC));
    public static final RegistryObject<EntityDataSerializer<Holder<ShroudwingVariant>>> SHROUDWING_VARIANT = ENTITY_DATA_SERIALIZERS.register("shroudwing_variant", () -> holder(ShroudwingVariant.DIRECT_CODEC));
    public static final RegistryObject<EntityDataSerializer<BladeshroomHunter.State>> BLADESHROOM_HUNTER_STATE = ENTITY_DATA_SERIALIZERS.register("bladeshroom_hunter_state", () -> EntityDataSerializer.simple(
            (buf, state) -> buf.writeVarInt(state.id()),
            buf -> BladeshroomHunter.State.BY_ID.apply(buf.readVarInt())));
    public static final RegistryObject<EntityDataSerializer<CloudSkiff.SteeringState>> CLOUD_SKIFF_STEERING_STATE = ENTITY_DATA_SERIALIZERS.register("cloud_skiff_steering_state", () -> EntityDataSerializer.simpleEnum(CloudSkiff.SteeringState.class));
    public static final RegistryObject<EntityDataSerializer<CompoundTag>> COMPOUND_TAG = ENTITY_DATA_SERIALIZERS.register("compound_tag", () -> new EntityDataSerializer<CompoundTag>() {
        @Override
        public void write(FriendlyByteBuf buf, CompoundTag tag) {
            buf.writeNbt(tag);
        }

        @Override
        public CompoundTag read(FriendlyByteBuf buf) {
            return buf.readNbt();
        }

        @Override
        public CompoundTag copy(CompoundTag tag) {
            return tag.copy();
        }
    });

    static {
        EntityDataSerializers.registerSerializer(OPTIONAL_LIVING_ENTITY_REFERENCE);
    }

    private static <T> EntityDataSerializer<Holder<T>> holder(Codec<T> codec) {
        return EntityDataSerializer.simple(
                (buf, holder) -> buf.writeNbt(encode(codec, holder.value())),
                buf -> Holder.direct(decode(codec, buf.readNbt())));
    }

    private static <T> CompoundTag encode(Codec<T> codec, T value) {
        return (CompoundTag) codec.encodeStart(NbtOps.INSTANCE, value).getOrThrow(false, AetherII.LOGGER::error);
    }

    private static <T> T decode(Codec<T> codec, CompoundTag tag) {
        return codec.parse(NbtOps.INSTANCE, tag).getOrThrow(false, AetherII.LOGGER::error);
    }
}
