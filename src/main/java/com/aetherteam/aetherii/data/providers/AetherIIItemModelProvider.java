package com.aetherteam.aetherii.data.providers;

import com.aetherteam.nitrogen.data.providers.NitrogenItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AetherIIItemModelProvider extends NitrogenItemModelProvider {
    public AetherIIItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void aercloudItem(Block block) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube_all")).texture("all", this.texture(this.blockName(block), "natural/")).renderType(new ResourceLocation("translucent"));
    }
}
