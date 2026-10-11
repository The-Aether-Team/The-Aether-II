package com.aetherteam.aetherii.client.renderer.accessory;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.HumanoidArm;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record GlovesModelSet<T>(T right, T left) {
    public T get(HumanoidArm arm) {
        return switch (arm) {
            case RIGHT -> this.right();
            case LEFT -> this.left();
        };
    }

    public <U> GlovesModelSet<U> map(Function<? super T, ? extends U> mapper) {
        return new GlovesModelSet<>(mapper.apply(this.right()), mapper.apply(this.left()));
    }

    public void registerFrom(GlovesModelSet<LayerDefinition> values, BiConsumer<T, Supplier<LayerDefinition>> registry) {
        registry.accept(this.right, values::right);
        registry.accept(this.left, values::left);
    }

    public static <M extends HumanoidModel<?>> GlovesModelSet<M> bake(GlovesModelSet<ModelLayerLocation> locations, EntityModelSet modelSet, Function<ModelPart, M> factory) {
        return locations.map((id) -> factory.apply(modelSet.bakeLayer(id)));
    }
}
