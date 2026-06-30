package com.aetherteam.aetherii.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ItemAttributeModifiers {
    private static final ResourceLocation BASE_ATTACK_DAMAGE_ID = new ResourceLocation("minecraft", "base_attack_damage");
    private static final ResourceLocation BASE_ATTACK_SPEED_ID = new ResourceLocation("minecraft", "base_attack_speed");
    private static final Codec<AttributeModifier.Operation> OPERATION_CODEC = Codec.STRING.comapFlatMap(ItemAttributeModifiers::operationByName, ItemAttributeModifiers::operationName);
    private static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ATTRIBUTE.byNameCodec().fieldOf("type").forGetter(entry -> entry.attribute().value()),
            ResourceLocation.CODEC.fieldOf("id").forGetter(entry -> ItemAttributeModifiers.id(entry.modifier())),
            Codec.DOUBLE.fieldOf("amount").forGetter(entry -> entry.modifier().getAmount()),
            OPERATION_CODEC.fieldOf("operation").forGetter(entry -> entry.modifier().getOperation()),
            EquipmentSlotGroup.CODEC.fieldOf("slot").forGetter(Entry::slot)
    ).apply(instance, (attribute, id, amount, operation, slot) -> new Entry(attribute, ItemAttributeModifiers.modifier(id, amount, operation), slot)));
    private static final Codec<List<Entry>> ENTRY_LIST_CODEC = ENTRY_CODEC.listOf();
    public static final Codec<ItemAttributeModifiers> CODEC = Codec.either(ENTRY_LIST_CODEC, Codec.unit(List.<Entry>of()))
            .xmap(either -> new ItemAttributeModifiers(either.map(entries -> entries, entries -> entries)), modifiers -> Either.left(modifiers.modifiers()));
    private final List<Entry> modifiers;

    public ItemAttributeModifiers(List<Entry> modifiers) {
        this.modifiers = List.copyOf(modifiers);
    }

    public List<Entry> modifiers() {
        return this.modifiers;
    }

    public void forEach(EquipmentSlotGroup slot, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
        for (Entry entry : this.modifiers) {
            if (entry.slot().test(slot)) {
                consumer.accept(entry.attribute(), entry.modifier());
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static AttributeModifier modifier(ResourceLocation id, double amount, AttributeModifier.Operation operation) {
        return new AttributeModifier(uuid(id), id.toString(), amount, operation);
    }

    public static ResourceLocation id(AttributeModifier modifier) {
        ResourceLocation id = ResourceLocation.tryParse(modifier.getName());
        return id != null ? id : new ResourceLocation("minecraft", modifier.getId().toString());
    }

    public static UUID uuid(ResourceLocation id) {
        if (id.equals(BASE_ATTACK_DAMAGE_ID)) {
            return Item.BASE_ATTACK_DAMAGE_UUID;
        } else if (id.equals(BASE_ATTACK_SPEED_ID)) {
            return Item.BASE_ATTACK_SPEED_UUID;
        }
        return UUID.nameUUIDFromBytes(id.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static boolean hasModifier(AttributeInstance instance, ResourceLocation id) {
        return instance.getModifier(uuid(id)) != null;
    }

    public static void removeModifier(AttributeInstance instance, ResourceLocation id) {
        AttributeModifier modifier = instance.getModifier(uuid(id));
        if (modifier != null) {
            instance.removeModifier(modifier);
        }
    }

    public record Entry(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
        public static final Codec<Entry> CODEC = ENTRY_CODEC;

        public Entry(Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
            this(Holder.direct(attribute), modifier, slot);
        }

        public Entry(RegistryObject<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
            this(Holder.direct(attribute.get()), modifier, slot);
        }

        public boolean matches(Holder<Attribute> attribute, ResourceLocation id) {
            return this.attribute.value() == attribute.value() && ItemAttributeModifiers.id(this.modifier).equals(id);
        }

        public boolean matches(Attribute attribute, ResourceLocation id) {
            return this.attribute.value() == attribute && ItemAttributeModifiers.id(this.modifier).equals(id);
        }

        public boolean matches(RegistryObject<Attribute> attribute, ResourceLocation id) {
            return this.matches(attribute.get(), id);
        }
    }

    private static DataResult<AttributeModifier.Operation> operationByName(String name) {
        return switch (name) {
            case "add_value", "addition" -> DataResult.success(AttributeModifier.Operation.ADDITION);
            case "add_multiplied_base", "multiply_base" -> DataResult.success(AttributeModifier.Operation.MULTIPLY_BASE);
            case "add_multiplied_total", "multiply_total" -> DataResult.success(AttributeModifier.Operation.MULTIPLY_TOTAL);
            default -> DataResult.error(() -> "Unknown attribute modifier operation: " + name);
        };
    }

    private static String operationName(AttributeModifier.Operation operation) {
        return switch (operation) {
            case ADDITION -> "add_value";
            case MULTIPLY_BASE -> "add_multiplied_base";
            case MULTIPLY_TOTAL -> "add_multiplied_total";
        };
    }

    public static class Builder {
        private final List<Entry> entries = new ArrayList<>();

        public Builder add(Holder<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
            this.entries.add(new Entry(attribute, modifier, slot));
            return this;
        }

        public Builder add(Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
            this.entries.add(new Entry(attribute, modifier, slot));
            return this;
        }

        public Builder add(RegistryObject<Attribute> attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
            this.entries.add(new Entry(attribute, modifier, slot));
            return this;
        }

        public ItemAttributeModifiers build() {
            return new ItemAttributeModifiers(this.entries);
        }
    }
}
