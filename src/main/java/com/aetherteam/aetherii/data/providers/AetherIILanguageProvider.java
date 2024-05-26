package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;

public abstract class AetherIILanguageProvider extends NitrogenLanguageProvider {
    public AetherIILanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addDamageTypeTooltip(String path, String name) {
        this.addItemTooltip("damage." + path, name);
    }

    public void addItemTooltip(String path, String name) {
        this.addTooltip("item." + path, name);
    }

    public void addTooltip(String path, String name) {
        this.add(this.id + ".tooltip." + path, name);
    }

    public void addAttribute(Attribute attribute, String name) {
        this.add(attribute.getDescriptionId(), name);
    }
}
