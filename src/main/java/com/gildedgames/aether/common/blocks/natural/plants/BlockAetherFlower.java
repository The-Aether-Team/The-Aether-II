package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.entities.monsters.EntityAechorPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherFlower extends BlockAetherPlant implements IBlockSnowy
{
	public static final BlockVariant
			WHITE_ROSE = new BlockVariant(0, "white_rose"),
			PURPLE_FLOWER = new BlockVariant(1, "purple_flower"),
			BURSTBLOSSOM = new BlockVariant(2, "burstblossom"),
			AECHOR_SPROUT = new BlockVariant(3, "aechor_sprout");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", WHITE_ROSE, PURPLE_FLOWER, BURSTBLOSSOM, AECHOR_SPROUT);

	public BlockAetherFlower(Block.Properties properties)
	{
		super(properties);

		this.setDefaultState(this.getStateContainer().getBaseState().with(PROPERTY_VARIANT, WHITE_ROSE).with(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void tick(BlockState state, World world, BlockPos pos, Random rand)
	{
		super.tick(state, world, pos, rand);

		if (!world.isRemote() && this.canGrow(world, pos, state, false))
		{
			this.grow(world, rand, pos, state);
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		ItemStack main = player.getHeldItemMainhand();
		ItemStack offhand = player.getHeldItemOffhand();

		boolean addSnow = false;

		if (!state.get(PROPERTY_SNOWY))
		{
			if (!main.isEmpty() && main.getItem() instanceof BlockItem)
			{
				if (((BlockItem) main.getItem()).getBlock() instanceof SnowBlock)
				{
					addSnow = true;
					main.shrink(1);
				}
			}
			else if (!offhand.isEmpty() && offhand.getItem() instanceof BlockItem && ((BlockItem) offhand.getItem()).getBlock() instanceof SnowBlock)
			{
				addSnow = true;
				offhand.shrink(1);
			}
		}

		if (addSnow)
		{
			world.setBlockState(pos, state.with(PROPERTY_SNOWY, Boolean.TRUE), 2);
		}

		return addSnow;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().with(SnowBlock.LAYERS, 1), 2);
			}
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PROPERTY_VARIANT, PROPERTY_SNOWY);
	}

	@Override
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}

	@Override
	public Vec3d getOffset(final BlockState state, final IBlockReader access, final BlockPos pos)
	{
		if (state.get(PROPERTY_SNOWY))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		BlockVariant variant = state.get(PROPERTY_VARIANT);

		return variant == AECHOR_SPROUT;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final BlockState state)
	{
		if (worldIn.getBrightness(pos.up()) >= 9 && worldIn.rand.nextInt(7) == 0)
		{
			worldIn.destroyBlock(pos, false);

			EntityAechorPlant aechorPlant = new EntityAechorPlant(worldIn);

			aechorPlant.posX = pos.getX() + .5f;
			aechorPlant.posY = pos.getY();
			aechorPlant.posZ = pos.getZ() + .5f;

			worldIn.addEntity(aechorPlant);
		}
	}

}
