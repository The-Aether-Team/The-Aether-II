package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.entities.monsters.EntityAechorPlant;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockAetherFlower extends BlockAetherPlant implements IBlockMultiName, IBlockSnowy
{
	public static final BlockVariant
			WHITE_ROSE = new BlockVariant(0, "white_rose"),
			PURPLE_FLOWER = new BlockVariant(1, "purple_flower"),
			BURSTBLOSSOM = new BlockVariant(2, "burstblossom"),
			AECHOR_SPROUT = new BlockVariant(3, "aechor_sprout");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", WHITE_ROSE, PURPLE_FLOWER, BURSTBLOSSOM, AECHOR_SPROUT);

	public BlockAetherFlower()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WHITE_ROSE).withProperty(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final BlockState state, final Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);

		if (!worldIn.isRemote && this.canGrow(worldIn, pos, state, false))
		{
			this.grow(worldIn, rand, pos, state);
		}
	}

	@Override
	public int getLightValue(final BlockState state)
	{
		return this.lightValue;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX,
			float hitY, float hitZ)
	{
		ItemStack main = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();

		boolean addSnow = false;

		if (!state.getValue(PROPERTY_SNOWY))
		{
			if (main != null && main.getItem() instanceof BlockItem)
			{
				if (((BlockItem) main.getItem()).getBlock() instanceof BlockSnow)
				{
					addSnow = true;
					main.shrink(1);
				}
			}
			else if (offhand != null && offhand.getItem() instanceof BlockItem && ((BlockItem) offhand.getItem()).getBlock() instanceof BlockSnow)
			{
				addSnow = true;
				offhand.shrink(1);
			}
		}

		if (addSnow)
		{
			worldIn.setBlockState(pos, state.withProperty(PROPERTY_SNOWY, Boolean.TRUE), 2);
		}

		return addSnow;
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, BlockState state)
	{
		if (state.getValue(PROPERTY_SNOWY))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().withProperty(BlockSnow.LAYERS, 1), 2);
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void getSubBlocks(final ItemGroup tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public int damageDropped(final BlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public BlockState getStateFromMeta(final int meta)
	{
		final boolean snowy = meta >= PROPERTY_VARIANT.getAllowedValues().size();

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta - (snowy ? PROPERTY_VARIANT.getAllowedValues().size() : 0)))
				.withProperty(PROPERTY_SNOWY, snowy);
	}

	@Override
	public int getMetaFromState(final BlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta() + (state.getValue(PROPERTY_SNOWY) ? PROPERTY_VARIANT.getAllowedValues().size() : 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT, PROPERTY_SNOWY);
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public Vec3d getOffset(final BlockState state, final IBlockReader access, final BlockPos pos)
	{
		if (state.getValue(PROPERTY_SNOWY))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public String getTranslationKey(final ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}

	@Override
	public boolean canGrow(final World worldIn, final BlockPos pos, final BlockState state, final boolean isClient)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

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
		if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && worldIn.rand.nextInt(7) == 0)
		{
			worldIn.destroyBlock(pos, false);

			EntityAechorPlant aechorPlant = new EntityAechorPlant(worldIn);

			aechorPlant.posX = pos.getX() + .5f;
			aechorPlant.posY = pos.getY();
			aechorPlant.posZ = pos.getZ() + .5f;

			worldIn.spawnEntity(aechorPlant);
		}
	}

}
