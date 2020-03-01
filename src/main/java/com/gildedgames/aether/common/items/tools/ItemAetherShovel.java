package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.events.listeners.items.ItemToolListener;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemAetherShovel extends ItemSpade
{
	public ItemAetherShovel(final ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("shovel", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TAB_TOOLS);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolListener.onEntityHit(stack, this.toolMaterial, target, attacker);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		Material material = state.getMaterial();
		float original = this.toolMaterial.getEfficiency();

		if (material == Material.CLAY || material == Material.GROUND || material == Material.GRASS || material == Material.SAND || material == Material.SNOW || material == Material.CRAFTED_SNOW)
		{
			return ItemToolListener.getBreakSpeed(stack, state, original);
		}

		return super.getDestroySpeed(stack, state);
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		if (this.toolMaterial == MaterialsAether.SKYROOT_TOOL)
		{
			return 200;
		}

		return super.getItemBurnTime(itemStack);
	}
}
