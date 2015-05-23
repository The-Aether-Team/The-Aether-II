package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemAetherSword extends ItemSword
{
	private EnumAetherToolMaterial aetherToolMaterial;

	public ItemAetherSword(EnumAetherToolMaterial toolMaterial)
	{
		super(toolMaterial.getToolMaterial());

		this.aetherToolMaterial = toolMaterial;

		this.setCreativeTab(AetherCreativeTabs.tabWeapons);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entity)
	{
		this.aetherToolMaterial.onBlockDestroyed(pos, world);

		return super.onBlockDestroyed(stack, world, block, pos, entity);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
		return this.aetherToolMaterial.getDigSpeed(stack, block, super.getStrVsBlock(stack, block));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		this.aetherToolMaterial.onEntityAttacked(stack, target, attacker);

		return super.hitEntity(stack, target, attacker);
	}

}
