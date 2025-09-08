package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

public class MoaRenderState extends LivingEntityRenderState {
    public float flyAmount;
    public boolean sitting;
    public ItemStack saddle = ItemStack.EMPTY;
    public ItemStack saddlebag = ItemStack.EMPTY;
    public String keratinColor = Moa.KeratinColor.BLUE.getSerializedName();
    public String eyeColor = Moa.EyeColor.BLUE.getSerializedName();
    public String featherColor = Moa.FeatherColor.BLUE.getSerializedName();
    public String featherShape = Moa.FeatherShape.FLAT.getSerializedName();

    public boolean isSaddled() {
        return !this.saddle.isEmpty();
    }
}
