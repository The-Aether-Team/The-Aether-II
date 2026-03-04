package com.aetherteam.aetherii.client.renderer.blockentity.state;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class MoaEggRenderState extends BlockEntityRenderState {
    public int tick;
    public int hatch;
    public Moa.FeatherShape featherShape;
    public Moa.FeatherColor featherColor;
    public Moa.KeratinColor keratinColor;
    public Moa.EyeColor eyeColor;
}
