package com.aetherteam.aetherii.client.renderer.block.model.state;

import com.mojang.math.Transformation;
import com.mojang.serialization.Codec;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import org.joml.Matrix4fc;

public class TrunkModelState implements ModelState { //todo
    public static final Codec<TrunkModelState> CODEC = Codec.unit(new TrunkModelState());

    public TrunkModelState() { }

    @Override
    public Transformation transformation() {
        return Transformation.identity();
    }

    @Override
    public Matrix4fc faceTransformation(Direction facing) {
        return NO_TRANSFORM;
    }

    @Override
    public Matrix4fc inverseFaceTransformation(Direction facing) {
        return NO_TRANSFORM;
    }
}
