package com.gildedgames.aether.blocks.natural;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherCreativeTabs;
import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.blocks.util.blockstates.PropertyVariant;

public class BlockAetherDirt extends Block implements IAetherBlockWithVariants
{
	public static final BlockVariant
	AETHER_DIRT = new BlockVariant(0, "aether_dirt"),
	AETHER_GRASS = new BlockVariant(1, "aether_grass"),
	ENCHANTED_AETHER_GRASS = new BlockVariant(2, "aether_enchanted_grass");

	public static final PropertyVariant GRASS_TYPE = PropertyVariant.create("variant", AETHER_DIRT, AETHER_GRASS, ENCHANTED_AETHER_GRASS);

	public BlockAetherDirt()
	{
		super(Material.ground);
		this.setStepSound(Block.soundTypeGravel);
		this.setHardness(0.5F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(GRASS_TYPE, AETHER_DIRT));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : GRASS_TYPE.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote && state.getValue(GRASS_TYPE) == AETHER_GRASS)
		{
			if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getBlock().getLightOpacity(world, pos.up()) > 2)
			{
				world.setBlockState(pos, Aether.getBlocks().aether_dirt.getDefaultState());
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

						if (neighborState.getBlock() == Aether.getBlocks().aether_dirt && neighborState.getValue(GRASS_TYPE) == AETHER_DIRT &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4 && neighborBlock.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							world.setBlockState(randomNeighbor, this.getDefaultState().withProperty(GRASS_TYPE, AETHER_GRASS));
						}
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(Aether.getBlocks().aether_dirt);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(GRASS_TYPE, GRASS_TYPE.getVariantFromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockVariant) state.getValue(GRASS_TYPE)).getMeta();
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { GRASS_TYPE });
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	public String getVariantNameFromStack(ItemStack stack)
	{
		return GRASS_TYPE.getVariantFromMeta(stack.getMetadata()).getName();
	}
}
