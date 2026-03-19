//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.aetherteam.aetherii.client.renderer.blockentity.state;

import com.aetherteam.aetherii.blockentity.VaseBlockEntity;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jspecify.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
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