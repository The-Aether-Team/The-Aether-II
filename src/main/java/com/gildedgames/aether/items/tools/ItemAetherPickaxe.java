package com.gildedgames.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.aether.Aether;

public class ItemAetherPickaxe extends ItemPickaxe
{
	private EnumAetherToolMaterial aetherToolMaterial;

	public ItemAetherPickaxe(EnumAetherToolMaterial toolMaterial)
	{
		super(toolMaterial.getToolMaterial());

		this.setCreativeTab(Aether.getCreativeTabs().tabTools);
		this.aetherToolMaterial = toolMaterial;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entity)
	{
		if (!world.isRemote)
		{
			this.aetherToolMaterial.onBlockDestroyed(pos, world);
		}

		return super.onBlockDestroyed(stack, world, block, pos, entity);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
	{
		return this.aetherToolMaterial.getDigSpeed(stack, block, super.getStrVsBlock(stack, block));
	}

}
