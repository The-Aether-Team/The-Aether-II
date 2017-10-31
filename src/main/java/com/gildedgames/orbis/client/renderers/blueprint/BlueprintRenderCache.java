package com.gildedgames.orbis.client.renderers.blueprint;

import com.gildedgames.orbis.common.world_objects.Blueprint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;

public class BlueprintRenderCache extends BlueprintRegionAccess
{
	private final int[] lightvalues;

	private final int volume;

	public BlueprintRenderCache(final Blueprint region, final World worldIn)
	{
		super(region, worldIn);

		this.volume = region.getBlockDataContainer().getVolume();
		this.lightvalues = new int[this.volume];
		Arrays.fill(this.lightvalues, -1);
	}

	@Override
	public int getCombinedLight(final BlockPos pos, final int p_175626_2_)
	{
		final int index = this.getIndex(pos);

		if (index < 0 || index >= this.volume)
		{
			return super.getCombinedLight(pos, p_175626_2_);
		}

		int value = this.lightvalues[index];

		if (value == -1)
		{
			value = super.getCombinedLight(pos, p_175626_2_);
			this.lightvalues[index] = value;
		}

		return value;
	}

	private int getIndex(final BlockPos pos)
	{
		final BlockPos newPos = this.blueprint.transformForBlueprint(pos);
		return newPos.getZ() + this.blueprint.getLength() * newPos.getY() + this.blueprint.getLength() * this.blueprint.getHeight() * newPos.getX();
	}

}
