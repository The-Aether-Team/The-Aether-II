package com.gildedgames.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.AetherCreativeTabs;

public class ItemAetherPickaxe extends ItemPickaxe
{
	private EnumAetherToolMaterial toolType;

	public ItemAetherPickaxe(EnumAetherToolMaterial type)
	{
		super(type.getToolMaterial());

		this.setCreativeTab(AetherCreativeTabs.tabTools);
		this.toolType = type;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entity)
	{
		if (!world.isRemote)
		{
			this.toolType.onBlockDestroyed(pos, world);
		}

		return super.onBlockDestroyed(stack, world, block, pos, entity);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
		return this.toolType.getDigSpeed(stack, block, super.getStrVsBlock(stack, block));
	}

}
