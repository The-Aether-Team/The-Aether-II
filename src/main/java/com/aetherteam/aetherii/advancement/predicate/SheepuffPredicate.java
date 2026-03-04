package com.aetherteam.aetherii.advancement.predicate;

import com.aetherteam.aetherii.entity.passive.Sheepuff;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record SheepuffPredicate(Optional<Boolean> puffed, Optional<Boolean> sheared, Optional<Sheepuff.SheepuffColor> color) implements EntitySubPredicate {
    public static final MapCodec<SheepuffPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.BOOL.optionalFieldOf("puffed").forGetter(SheepuffPredicate::puffed),
            Codec.BOOL.optionalFieldOf("sheared").forGetter(SheepuffPredicate::sheared),
            Sheepuff.SheepuffColor.CODEC.optionalFieldOf("color").forGetter(SheepuffPredicate::color)
    ).apply(instance, SheepuffPredicate::new));

    @Override
    public MapCodec<? extends EntitySubPredicate> codec() {
        return AetherIIEntitySubPredicates.SHEEPUFF.get();
    }

    @Override
    public boolean matches(Entity entity, ServerLevel serverLevel, @Nullable Vec3 vec3) {
        if (entity instanceof Sheepuff sheepuff) {
            if (this.sheared.isPresent() && sheepuff.isSheared() == this.sheared.get()) {
                if (this.color.isPresent()) {
                    return this.color.get() == sheepuff.getColor();
                } else if (this.puffed.isPresent()) {
                    return this.puffed.get() == sheepuff.getPuffed();
                }
            }
        }
        return false;
    }

    public static SheepuffPredicate hasWool(Sheepuff.SheepuffColor color) {
        return new SheepuffPredicate(Optional.empty(), Optional.of(false), Optional.of(color));
    }

    public static SheepuffPredicate isPuffed(boolean puffed) {
        return new SheepuffPredicate(Optional.of(puffed), Optional.of(false), Optional.empty());
    }
}
