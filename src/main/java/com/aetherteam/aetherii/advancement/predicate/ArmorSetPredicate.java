package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record ArmorSetPredicate(TagKey<Item> armor) implements EntitySubPredicate {
    public static final MapCodec<ArmorSetPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            TagKey.codec(Registries.ITEM).fieldOf("armor").forGetter(ArmorSetPredicate::armor)
    ).apply(instance, ArmorSetPredicate::new));

    @Override
    public MapCodec<ArmorSetPredicate> codec() {
        return AetherIIEntitySubPredicates.ARMOR_SET.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof LivingEntity livingEntity) {
            return EquipmentUtil.hasArmorAbility(livingEntity, this.armor());
        }
        return false;
    }
}