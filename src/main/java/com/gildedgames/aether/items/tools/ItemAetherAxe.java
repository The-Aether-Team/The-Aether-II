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
	private EnumAetherToolMaterial aetherToolMaterial;

	public ItemAetherAxe(EnumAetherToolMaterial aetherToolMaterial)
	{
		super(aetherToolMaterial.getToolMaterial());

		this.setCreativeTab(Aether.getCreativeTabs().tabTools);
		this.aetherToolMaterial = aetherToolMaterial;
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
}
