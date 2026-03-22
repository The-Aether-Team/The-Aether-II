//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.aetherteam.aetherii.client.renderer.blockentity.state;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class VaseRenderState extends BlockEntityRenderState {
    public float yRot;
    public VaseBlockEntity.@Nullable WobbleStyle wobbleStyle;
    public float wobbleProgress;
    public Direction direction;
    public Identifier vaseTexture;

    public VaseRenderState() {
        this.direction = Direction.NORTH;
    }
}