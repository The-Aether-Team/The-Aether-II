package com.aetherteam.aetherii.client.renderer.entity.state;

import com.aetherteam.aetherii.entity.passive.Moa;
import net.minecraft.resources.ResourceLocation;
import com.aetherteam.aetherii.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;

public class MoaRenderState extends LivingEntityRenderState {
    public float flyAmount;
    public boolean sitting;
    public ItemStack saddle = ItemStack.EMPTY;
    public ItemStack saddlebag = ItemStack.EMPTY;
    public Moa.KeratinColor keratinColor = Moa.KeratinColor.DEFAULT;
    public Moa.EyeColor eyeColor = Moa.EyeColor.DEFAULT;
    public Moa.FeatherColor featherColor = Moa.FeatherColor.DEFAULT;
    public Moa.FeatherShape featherShape = Moa.FeatherShape.DEFAULT;
    @Nullable
    public Moa.SpecialVariant specialVariant = null;
    @Nullable
    public EntityReference<LivingEntity> vehicleReference;
    public float opacity;

    public boolean isSaddled() {
        return !this.saddle.isEmpty();
    }

    public boolean hasSpecialTexture() {
        return this.specialVariant != null && (this.isBaby ? this.specialVariant.babyTexture : this.specialVariant.defaultTexture) != null;
    }

    @Nullable
    @Contract("!null->!null")
    public ResourceLocation getSpecialDefaultTextureOr(@Nullable ResourceLocation fallback) {
        if (this.specialVariant == null) return fallback;
        ResourceLocation texture = this.specialVariant.defaultTexture;
        return texture != null ? texture : fallback;
    }

    @Nullable
    @Contract("!null->!null")
    public ResourceLocation getSpecialBabyTextureOr(@Nullable ResourceLocation fallback) {
        if (this.specialVariant == null) return fallback;
        ResourceLocation texture = this.specialVariant.babyTexture;
        return texture != null ? texture : fallback;
    }

}
