package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Moa;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

public class MoaRenderState extends LivingEntityRenderState {
    public float flyAmount;
    public boolean sitting;
    public ItemStack saddle = ItemStack.EMPTY;
    public ItemStack saddlebag = ItemStack.EMPTY;
    public Moa.KeratinColor keratinColor = Moa.KeratinColor.DEFAULT;
    public Moa.EyeColor eyeColor = Moa.EyeColor.DEFAULT;
    public Moa.FeatherColor featherColor = Moa.FeatherColor.DEFAULT;
    public Moa.FeatherShape featherShape = Moa.FeatherShape.DEFAULT;

    public boolean isSaddled() {
        return !this.saddle.isEmpty();
    }
}
