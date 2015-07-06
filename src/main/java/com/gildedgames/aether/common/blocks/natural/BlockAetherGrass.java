package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.blocks.util.variants.IAetherBlockWithSubtypes;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockAetherGrass extends Block implements IAetherBlockWithSubtypes
{
	public static final BlockVariant
			AETHER_GRASS = new BlockVariant(0, "normal"),
			ENCHANTED_AETHER_GRASS = new BlockVariant(1, "enchanted");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", AETHER_GRASS, ENCHANTED_AETHER_GRASS);

	public BlockAetherGrass()
	{
		super(Material.grass);
		this.setStepSound(Block.soundTypeGrass);
		this.setHardness(0.5F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, AETHER_GRASS));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote && state.getValue(PROPERTY_VARIANT) == AETHER_GRASS)
		{
			if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getBlock().getLightOpacity(world, pos.up()) > 2)
			{
				world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState());
			}
			else
			{
				if (world.getLightFromNeighbors(pos.up()) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						BlockPos randomNeighbor = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						Block neighborBlock = world.getBlockState(randomNeighbor.up()).getBlock();
						IBlockState neighborState = world.getBlockState(randomNeighbor);

						if (neighborState.getBlock() == BlocksAether.aether_dirt &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4 && neighborBlock.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							world.setBlockState(randomNeighbor, this.getDefaultState().withProperty(PROPERTY_VARIANT, AETHER_GRASS));
						}
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(PROPERTY_VARIANT)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, PROPERTY_VARIANT);
	}

	@Override
	public String getSubtypeUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
