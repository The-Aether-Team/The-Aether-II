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
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemAetherPickaxe extends ItemPickaxe
{
	public ItemAetherPickaxe(final ToolMaterial material)
	{
		super(material);

		this.setHarvestLevel("pickaxe", material.getHarvestLevel());

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

		if (material == Material.IRON || material == Material.ANVIL || material == Material.ROCK)
		{
			return ItemToolListener.getBreakSpeed(stack, state, original);
		}

		return super.getDestroySpeed(stack, state);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

		if (stack.getItem() == ItemsAether.arkenium_pickaxe)
		{
			if (slot == EntityEquipmentSlot.MAINHAND)
			{
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2D, 0));
			}
		}

		return multimap;
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
