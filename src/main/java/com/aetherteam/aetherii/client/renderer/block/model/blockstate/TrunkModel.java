package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraftforge.client.model.BakedModelWrapper;

public class TrunkModel extends BakedModelWrapper<BakedModel> {
    public TrunkModel(BakedModel originalModel) {
        super(originalModel);
    }

    public record Unbaked(ResourceLocation corner, ResourceLocation cornerTall) {
        public static final ResourceLocation ID = new ResourceLocation(AetherII.MODID, "trunk_corners");
    }

    public record Holder(String name, WallSide value) {
    }
}
