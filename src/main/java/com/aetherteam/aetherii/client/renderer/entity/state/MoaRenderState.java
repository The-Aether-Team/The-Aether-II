package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.SaddleableRenderState;
import net.minecraft.world.item.ItemStack;

public class MoaRenderState extends LivingEntityRenderState implements SaddleableRenderState {
    public float flyAmount;
    public boolean sitting;
    public ItemStack saddle;
    public ItemStack saddlebag;
    public String keratinColor = Moa.KeratinColor.BLUE.getSerializedName();
    public String eyeColor = Moa.EyeColor.BLUE.getSerializedName();
    public String featherColor = Moa.FeatherColor.BLUE.getSerializedName();
    public String featherShape = Moa.FeatherShape.FLAT.getSerializedName();

    @Override
    public boolean isSaddled() {
        return !this.saddle.isEmpty();
    }
}
