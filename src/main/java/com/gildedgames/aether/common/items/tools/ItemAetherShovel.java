package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemAetherShovel extends ItemSpade
{
	private EnumAetherToolMaterial aetherToolMaterial;

	public ItemAetherShovel(EnumAetherToolMaterial toolMaterial)
	{
		super(toolMaterial.getToolMaterial());

		this.aetherToolMaterial = toolMaterial;

		this.setCreativeTab(AetherCreativeTabs.tabTools);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase entity)
	{
		this.aetherToolMaterial.onBlockDestroyed(pos, world);

		return super.onBlockDestroyed(stack, world, block, pos, entity);
	}

	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		return this.aetherToolMaterial.getDigSpeed(stack, state, super.getDigSpeed(stack, state));
	}
}
