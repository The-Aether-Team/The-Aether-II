package com.gildedgames.aetherii.client.render;

import com.gildedgames.aetherii.entity.TestEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TestRenderer<T extends TestEntity> extends EntityRenderer<T> {
	public TestRenderer(EntityRendererProvider.Context pContext) {
		super(pContext);
	}

	@Override
	public ResourceLocation getTextureLocation(T pEntity) {
		return null;
	}
}
