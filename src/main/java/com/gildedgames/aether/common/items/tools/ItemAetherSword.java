package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.common.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		return this.aetherToolMaterial.getDigSpeed(stack, state, super.getDigSpeed(stack, state));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		this.aetherToolMaterial.onEntityAttacked(stack, target, attacker);

		return super.hitEntity(stack, target, attacker);
	}

}
