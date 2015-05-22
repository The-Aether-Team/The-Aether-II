package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemAetherPickaxe extends ItemPickaxe
{
	private EnumAetherToolMaterial aetherToolMaterial;

	public ItemAetherPickaxe(EnumAetherToolMaterial toolMaterial)
	{
		super(toolMaterial.getToolMaterial());

		this.setCreativeTab(AetherCreativeTabs.tabTools);
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
