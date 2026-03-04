package com.aetherteam.aetherii.client.renderer.blockentity.state;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class MoaEggBlockRenderState extends BlockEntityRenderState {
    public int hatch;
    public Moa.KeratinColor keratinColor;
    public Moa.EyeColor eyesColor;
    public Moa.FeatherColor featherColor;
    public Moa.FeatherShape featherShape;
    public int tick;
}
