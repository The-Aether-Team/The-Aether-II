package com.gildedgames.aether.api.world;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IWorldObjectHoverable
{
	@OnlyIn(Dist.CLIENT)
	ITextComponent getHoverText(World world, RayTraceResult result);
}
