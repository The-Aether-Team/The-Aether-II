package com.aetherteam.aetherii.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class RandomDecorator extends TreeDecorator {
    public static final MapCodec<RandomDecorator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("placement_chance").forGetter(decorator -> decorator.placementChance),
            TreeDecorator.CODEC.listOf().fieldOf("tree_decorators").forGetter(decorator -> decorator.treeDecorators)
    ).apply(instance, RandomDecorator::new));

    private final float placementChance;
    private final List<TreeDecorator> treeDecorators;

    public RandomDecorator(float placementChance, List<TreeDecorator> treeDecorators) {
        this.placementChance = placementChance;
        this.treeDecorators = treeDecorators;
    }

    @Override
    public void place(Context context) {
        if (context.random().nextFloat() <= this.placementChance) {
            for (TreeDecorator treeDecorator : this.treeDecorators) {
                treeDecorator.place(context);
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return AetherIITreeDecoratorTypes.RANDOM.get();
    }
}
