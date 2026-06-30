package com.aetherteam.aetherii.client.renderer.block.model.part;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.List;

public record AOModelPart(List<BakedQuad> quads, TextureAtlasSprite particleIcon) {
}
