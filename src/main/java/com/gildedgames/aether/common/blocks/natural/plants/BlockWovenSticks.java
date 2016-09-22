package com.gildedgames.aether.common.blocks.natural.plants;

import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.gildedgames.aether.common.blocks.natural.plants.BlockAetherFlower.*;

public class BlockWovenSticks extends Block implements IBlockVariants
{

	public static final BlockVariant
			SKYROOT = new BlockVariant(0, "skyroot"),
			BLIGHTWILLOW = new BlockVariant(1, "blightwillow"),
			EARTHSHIFTER = new BlockVariant(2, "earthshifter"),
			EMBEROOT = new BlockVariant(3, "emberoot"),
			FROSTPINE = new BlockVariant(4, "frostpine");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SKYROOT, BLIGHTWILLOW, EARTHSHIFTER, EMBEROOT, FROSTPINE);
	
	public BlockWovenSticks()
	{
		super(Material.GROUND);
		
		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.5F);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_VARIANT, SKYROOT));
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		return (variant == EMBEROOT ? (int)(0.6F * 15.0F) : this.lightValue);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(PROPERTY_VARIANT, PROPERTY_VARIANT.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
	
}
