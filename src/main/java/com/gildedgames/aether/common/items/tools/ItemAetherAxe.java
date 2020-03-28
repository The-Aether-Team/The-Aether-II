package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.events.listeners.items.ItemToolListener;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemAetherAxe extends ItemAxe
{
	public ItemAetherAxe(final ToolMaterial material)
	{
		// The parent constructor will crash trying to set parameters, we need to do it here
		this(material, 6.0F, -3.2F);
	}

	public ItemAetherAxe(final ToolMaterial material, final float damageVsEntity, final float attackSpeed)
	{
		super(material, damageVsEntity, attackSpeed);

		this.setHarvestLevel("axe", material.getHarvestLevel());

		this.setCreativeTab(CreativeTabsAether.TAB_TOOLS);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		super.hitEntity(stack, target, attacker);

		return ItemToolListener.onEntityHit(stack, this.toolMaterial, target, attacker);
	}

	public boolean canMine(IBlockState state)
	{
		Material material = state.getMaterial();
		return material == Material.WOOD || material == Material.PLANTS || material == Material.VINE;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		float original = this.toolMaterial.getEfficiency();

		if (this.canMine(state))
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
