package com.gildedgames.aether.common.blocks.natural.leaves;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.client.renderer.particles.ParticleGolden;
import com.gildedgames.aether.client.renderer.particles.ParticleLeaf;
import com.gildedgames.aether.common.blocks.natural.plants.saplings.BlockAetherUniqueSapling;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockAetherLeaves extends LeavesBlock
{
	public static final BooleanProperty PROPERTY_DECAYABLE = BooleanProperty.create("decayable");

	public static final BooleanProperty PROPERTY_CHECK_DECAY = BooleanProperty.create("check_decay");

	private int[] surroundings;

	public BlockAetherLeaves(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_DECAYABLE, Boolean.TRUE).with(PROPERTY_CHECK_DECAY, Boolean.TRUE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(final BlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (Minecraft.getInstance().gameSettings.particleSetting != 0)
		{
			return;
		}

		super.animateTick(state, world, pos, rand);

		if (this == BlocksAether.amberoot_leaves)
		{
			if (rand.nextInt(100) > 90)
			{
				final double x = pos.getX() + (rand.nextFloat() * 6f) - 3f;
				final double y = pos.getY() + (rand.nextFloat() * 6f) - 3f;
				final double z = pos.getZ() + (rand.nextFloat() * 6f) - 3f;

				final ParticleGolden effect = new ParticleGolden(world, x, y, z, 0, 0, 0);

				Minecraft.getInstance().particles.addEffect(effect);
			}
		}

		if (world.isAirBlock(pos.down()))
		{
			if (rand.nextInt(100) > 97)
			{
				final double x = pos.getX() + rand.nextFloat();
				final double y = pos.getY();
				final double z = pos.getZ() + rand.nextFloat();

				final ParticleLeaf effect = new ParticleLeaf(world, x, y, z,
						-0.04D + (rand.nextFloat() * 0.08f),
						-0.05D + (rand.nextFloat() * -0.02f),
						-0.04D + (rand.nextFloat() * 0.08f),
						this);

				Minecraft.getInstance().particles.addEffect(effect);
			}
		}
	}


	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	protected ItemStack getSaplingItem()
	{
		if (this == BlocksAether.amberoot_leaves)
		{
			return new ItemStack(BlocksAether.unique_sapling, 1, BlockAetherUniqueSapling.AMBEROOT.getMeta());
		}

		return null;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_DECAYABLE, PROPERTY_CHECK_DECAY);
	}


/*	@Override
	public BlockPlanks.EnumType getWoodType(int meta)
	{
		return null;
	}*/

}
