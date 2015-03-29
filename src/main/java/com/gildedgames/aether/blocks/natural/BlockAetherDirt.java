package com.gildedgames.aether.blocks.natural;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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
import com.gildedgames.aether.blocks.util.IAetherBlockVariant;
import com.gildedgames.aether.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.creativetabs.AetherCreativeTabs;

public class BlockAetherDirt extends Block implements IAetherBlockWithVariants
{
	public enum AetherGrassVariant implements IAetherBlockVariant
	{
		NORMAL(0, "aether_dirt"),
		GRASS(1, "aether_grass"),
		ENCHANTED_GRASS(2, "aether_enchanted_grass");

		private static final AetherGrassVariant[] metaLookup = new AetherGrassVariant[AetherGrassVariant.values().length];

		static
		{
			for (AetherGrassVariant variant : AetherGrassVariant.values())
			{
				metaLookup[variant.getMetadata()] = variant;
			}
		}

		private int metadata;

		private String name;

		AetherGrassVariant(int metadata, String name)
		{
			this.metadata = metadata;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		@Override
		public int getMetadata()
		{
			return this.metadata;
		}

		public static AetherGrassVariant getVariantFromMetadata(int meta)
		{
			return AetherGrassVariant.metaLookup[meta];
		}
	}

	public static final PropertyEnum GRASS_TYPE = PropertyEnum.create("variant", AetherGrassVariant.class);

	public BlockAetherDirt()
	{
		super(Material.ground);
		this.setStepSound(Block.soundTypeGravel);
		this.setHardness(0.5F);
		this.setTickRandomly(true);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(GRASS_TYPE, AetherGrassVariant.NORMAL));
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (AetherGrassVariant type : AetherGrassVariant.values())
		{
			list.add(new ItemStack(itemIn, 1, type.getMetadata()));
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!world.isRemote && state.getValue(GRASS_TYPE) == AetherGrassVariant.NORMAL)
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

						if (neighborState.getBlock() == Aether.getBlocks().aether_dirt && neighborState.getValue(GRASS_TYPE) == AetherGrassVariant.NORMAL &&
								world.getLightFromNeighbors(randomNeighbor.up()) >= 4 && neighborBlock.getLightOpacity(world, randomNeighbor.up()) <= 2)
						{
							world.setBlockState(randomNeighbor, this.getDefaultState().withProperty(GRASS_TYPE, AetherGrassVariant.GRASS));
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
		return this.getDefaultState().withProperty(GRASS_TYPE, AetherGrassVariant.getVariantFromMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((AetherGrassVariant) state.getValue(GRASS_TYPE)).getMetadata();
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
		return AetherGrassVariant.getVariantFromMetadata(stack.getMetadata()).getName();
	}
}
