package com.gildedgames.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemAetherSword extends ItemSword
{
	private EnumAetherToolMaterial toolType;

	public ItemAetherSword(EnumAetherToolMaterial toolType)
	{
		super(toolType.getToolMaterial());

		this.toolType = toolType;
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

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		toolType.onEntityAttacked(stack, target, attacker);

		return super.hitEntity(stack, target, attacker);
	}

}
