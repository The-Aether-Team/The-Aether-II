package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.blocks.IBlockMultiName;
import com.gildedgames.aether.common.blocks.IBlockSnowy;
import com.gildedgames.aether.common.blocks.properties.BlockVariant;
import com.gildedgames.aether.common.blocks.properties.PropertyVariant;
import com.gildedgames.aether.common.entities.monsters.EntityAechorPlant;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		this.setSoundType(SoundType.PLANT);

		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, WHITE_ROSE).withProperty(PROPERTY_SNOWY, Boolean.FALSE));
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);

		if (!worldIn.isRemote && this.canGrow(worldIn, pos, state, false))
		{
			this.grow(worldIn, rand, pos, state);
		}

		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11)
		{
			if (state.getValue(PROPERTY_SNOWY))
			{
				worldIn.setBlockState(pos, state.withProperty(PROPERTY_SNOWY, Boolean.FALSE), 2);
			}
		}
	}

	@Override
	public int getLightValue(final IBlockState state)
	{
		return this.lightValue;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ)
	{
		ItemStack main = playerIn.getHeldItemMainhand();
		ItemStack offhand = playerIn.getHeldItemOffhand();

		boolean addSnow = false;

		if (!state.getValue(PROPERTY_SNOWY))
		{
			if (main != null && main.getItem() instanceof ItemBlock)
			{
				if (((ItemBlock) main.getItem()).getBlock() instanceof BlockSnow)
				{
					addSnow = true;
					main.shrink(1);
				}
			}
			else if (offhand != null && offhand.getItem() instanceof ItemBlock && ((ItemBlock) offhand.getItem()).getBlock() instanceof BlockSnow)
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
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
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
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final boolean snowy = meta >= PROPERTY_VARIANT.getAllowedValues().size();

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta - (snowy ? PROPERTY_VARIANT.getAllowedValues().size() : 0)))
				.withProperty(PROPERTY_SNOWY, snowy);
	}

	@Override
	public int getMetaFromState(final IBlockState state)
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
	public Vec3d getOffset(final IBlockState state, final IBlockAccess access, final BlockPos pos)
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
	public boolean canGrow(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		return variant == AECHOR_SPROUT;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state)
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
