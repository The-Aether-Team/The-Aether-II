package com.gildedgames.aether.api.world;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWorldObjectHoverable
{
	@SideOnly(Side.CLIENT)
	ITextComponent getHoverText(World world, RayTraceResult result);
}
