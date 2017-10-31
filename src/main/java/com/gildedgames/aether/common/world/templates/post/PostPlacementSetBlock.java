package com.gildedgames.aether.common.world.templates.post;

import com.gildedgames.aether.api.world.generation.PostPlacementTemplate;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.aether.common.world.util.GenUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PostPlacementSetBlock implements PostPlacementTemplate
{
	private final IBlockState state;

	private final BlockPos offset;

	public PostPlacementSetBlock(final IBlockState state, final BlockPos offset)
	{
		this.state = state;
		this.offset = offset;
	}

	@Override
	public void postGenerate(final World world, final Random rand, final TemplateLoc loc)
	{
		final BlockPos placeAt = GenUtil.rotate(loc.getPos(), loc.getPos().add(this.offset), loc.getSettings().getRotation());

		world.setBlockState(placeAt, this.state);

		this.state.getBlock().onBlockAdded(world, placeAt, this.state);
	}
}
