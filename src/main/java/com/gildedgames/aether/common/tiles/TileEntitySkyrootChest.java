package com.gildedgames.aether.common.tiles;

import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySkyrootChest extends TileEntityChest
{
	@Override
	public String getName()
	{
		return this.hasCustomName() ? super.getName() : "container.skyroot_chest";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(this.getPos().add(-1, 0, -1), this.getPos().add(2, 2, 2));
	}
}
