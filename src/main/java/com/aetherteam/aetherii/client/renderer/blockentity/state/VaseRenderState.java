//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.aetherteam.aetherii.client.renderer.blockentity.state;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.Nullable;

public class VaseRenderState {
    public float yRot;
    public VaseBlockEntity.@Nullable WobbleStyle wobbleStyle;
    public float wobbleProgress;
    public Direction direction;
    public ResourceLocation vaseTexture;

    public VaseRenderState() {
        this.direction = Direction.NORTH;
    }
}
