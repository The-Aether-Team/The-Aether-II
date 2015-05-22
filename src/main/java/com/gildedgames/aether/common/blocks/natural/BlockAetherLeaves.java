package com.gildedgames.aether.common.blocks.natural;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.blocks.util.IAetherBlockWithVariants;
import com.gildedgames.aether.common.blocks.util.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.blockstates.PropertyVariant;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockAetherLeaves extends BlockLeaves implements IAetherBlockWithVariants
{
	public static final BlockVariant
			BLUE_SKYROOT_LEAVES = new BlockVariant(0, "blue_skyroot_leaves"),
			GREEN_SKYROOT_LEAVES = new BlockVariant(1, "green_skyroot_leaves"),
			DARK_BLUE_SKYROOT_LEAVES = new BlockVariant(2, "dark_blue_skyroot_leaves"),
			GOLDEN_OAK_LEAVES = new BlockVariant(3, "golden_oak_leaves"),
			PURPLE_CRYSTAL_LEAVES = new BlockVariant(4, "purple_crystal_leaves"),
			PURPLE_FRUIT_LEAVES = new BlockVariant(5, "purple_fruit_leaves");

	public static final PropertyVariant LEAVES_VARIANT = PropertyVariant.create("variant", BLUE_SKYROOT_LEAVES, GREEN_SKYROOT_LEAVES, DARK_BLUE_SKYROOT_LEAVES, GOLDEN_OAK_LEAVES, PURPLE_CRYSTAL_LEAVES, PURPLE_FRUIT_LEAVES);

	public BlockAetherLeaves()
	{
		super();
		this.setCreativeTab(AetherCreativeTabs.tabBlocks);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		for (BlockVariant variant : LEAVES_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(itemIn, 1, variant.getMeta()));
		}
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return null;
	}

	@Override
	public EnumType getWoodType(int meta)
	{
		return null;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(LEAVES_VARIANT, LEAVES_VARIANT.getVariantFromMeta(meta)).withProperty(DECAYABLE, (meta & 6) == 0).withProperty(CHECK_DECAY, (meta & 6) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b0 = 0;
		int i = b0 | ((BlockVariant) state.getValue(LEAVES_VARIANT)).getMeta();

		if (!(Boolean) state.getValue(DECAYABLE))
		{
			i |= 6;
		}

		if ((Boolean) state.getValue(CHECK_DECAY))
		{
			i |= 6;
		}

		return i;
	}

	// return ((BlockVariant) state.getValue(LEAVES_VARIANT)).getMeta();

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, LEAVES_VARIANT, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public String getUnlocalizedNameFromStack(ItemStack stack)
	{
		return LEAVES_VARIANT.getVariantFromMeta(stack.getMetadata()).getName();
	}

}
