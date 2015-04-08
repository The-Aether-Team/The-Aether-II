package com.gildedgames.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.Aether;

public class ItemAetherAxe extends ItemAxe
{
	private EnumAetherToolMaterial toolType;

	public ItemAetherAxe(EnumAetherToolMaterial type)
	{
		super(type.getToolMaterial());

		this.setCreativeTab(Aether.getCreativeTabs().tabTools);
		this.toolType = type;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entity)
	{
		this.toolType.onBlockDestroyed(pos, world);

		return super.onBlockDestroyed(stack, world, block, pos, entity);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
		return this.toolType.getDigSpeed(stack, block, super.getStrVsBlock(stack, block));
	}
}
