package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GlovesItem extends Item {
    protected ResourceLocation GLOVES_TEXTURE;

    public GlovesItem(String glovesName, Properties properties) {
        this(ResourceLocation.fromNamespaceAndPath(AetherII.MODID, glovesName), properties);
    }

    public GlovesItem(ResourceLocation glovesName, Properties properties) {
        super(properties);
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.GLOVES_TEXTURE = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/handwear/" + registryName + "_accessory.png");
    }

    public ResourceLocation getGlovesTexture() {
        return this.GLOVES_TEXTURE;
    }

    public static int getDurability(int durabilityFactor) {
        return 13 * durabilityFactor;
    } //todo
}
