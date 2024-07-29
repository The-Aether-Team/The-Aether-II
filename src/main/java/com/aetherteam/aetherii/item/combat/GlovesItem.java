package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

public class GlovesItem extends Item {
    protected final Holder<ArmorMaterial> material;
    protected ResourceLocation GLOVES_TEXTURE;

    public GlovesItem(Holder<ArmorMaterial> material, String glovesName, Properties properties) {
        this(material, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, glovesName), properties);
    }

    public GlovesItem(Holder<ArmorMaterial> material, ResourceLocation glovesName, Properties properties) {
        super(properties);
        this.material = material;
        this.setRenderTexture(glovesName.getNamespace(), glovesName.getPath());
    }

    public Holder<ArmorMaterial> getMaterial() {
        return this.material;
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
