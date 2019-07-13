package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockIceCrystal extends Block
{
	public static final int CRYSTAL_META = 0;

	public static final int CRYSTAL_A_META = 1;

	public static final int CRYSTAL_B_META = 2;

	public static final BlockVariant
			STALAGMITE = new BlockVariant(0, "stalagmite"),
			STALAGMITE_A = new BlockVariant(1, "stalagmite_a"),
			STALAGMITE_B = new BlockVariant(2, "stalagmite_b"),
			STALACTITE = new BlockVariant(3, "stalactite"),
			STALACTITE_A = new BlockVariant(4, "stalactite_a"),
			STALACTITE_B = new BlockVariant(5, "stalactite_b");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant",
			STALAGMITE, STALAGMITE_A, STALAGMITE_B, STALACTITE, STALACTITE_A, STALACTITE_B);

	private static final VoxelShape STALACTITE_BB = Block.makeCuboidShape(0.25D, 0.25D, 0.25D, 0.75D, 1D, 0.75D);

	private static final VoxelShape STALACTITE_SHORT_BB = Block.makeCuboidShape(0.25D, 0.5D, 0.25F, 0.75D, 1D, 0.75D);

	private static final VoxelShape STALAGMITE_BB = Block.makeCuboidShape(0.25D, 0.0D, 0.25D, 0.75D, 1D, 0.75D);

	private static final VoxelShape STALAGMITE_SHORT_BB = Block.makeCuboidShape(0.25D, 0.0D, 0.25F, 0.75D, 0.5D, 0.75D);

	public BlockIceCrystal(Block.Properties properties)
	{
		super(properties);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT);
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		if (world.getLightFor(LightType.BLOCK, pos) > 11 - this.getDefaultState().getOpacity(world, pos))
		{
			world.removeBlock(pos, false);
		}
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		if (entityIn instanceof PlayerEntity)
		{
			worldIn.removeBlock(pos, false);

			if (entityIn.world.isRemote())
			{
				entityIn.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1f, 1f);

				for (int i = 0; i < 50; i++)
				{
					final double x = pos.getX() + worldIn.rand.nextDouble();
					final double y = pos.getY() + 1.0D;
					final double z = pos.getZ() + worldIn.rand.nextDouble();

					worldIn.addParticle(ParticleTypes.SPLASH, x, y, z, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if (player.isCreative())
		{
			world.setBlockState(pos, state.cycle(PROPERTY_VARIANT));
			return true;
		}
		return false;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{

		if (state.get(PROPERTY_VARIANT) == STALACTITE || state.get(PROPERTY_VARIANT) == STALACTITE_A || state.get(PROPERTY_VARIANT) == STALACTITE_B)
		{
			if (world.isAirBlock(pos.up()) || world.getBlockState(pos.up()).getBlock() == Blocks.WATER)
			{
				world.removeBlock(pos, false);
			}
		}
		if (state.get(PROPERTY_VARIANT) == STALAGMITE || state.get(PROPERTY_VARIANT) == STALAGMITE_A || state.get(PROPERTY_VARIANT) == STALAGMITE_B)
		{
			if (world.isAirBlock(pos.down()) || world.getBlockState(pos.down()).getBlock() == Blocks.WATER)
			{
				world.removeBlock(pos, false);
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		int meta = state.get(PROPERTY_VARIANT).getMeta();

		if (meta == STALAGMITE.getMeta() || meta == STALAGMITE_A.getMeta() || meta == STALAGMITE_B.getMeta())
		{
			if (meta == STALAGMITE_B.getMeta())
			{
				return STALAGMITE_SHORT_BB;
			}
			return STALAGMITE_BB;
		}

		if (meta == STALACTITE_B.getMeta())
		{
			return STALACTITE_SHORT_BB;
		}
		return STALACTITE_BB;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}
}
