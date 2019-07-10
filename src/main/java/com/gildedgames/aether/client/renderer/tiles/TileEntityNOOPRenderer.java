package com.gildedgames.aether.client.renderer.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNOOPRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
	@Override
	public void render(final T te, final double x, final double y, final double z, final float partialTicks, final int destroyStage,
			final float alpha)
	{

	}
}
