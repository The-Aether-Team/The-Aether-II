package com.gildedgames.aether.common.items.tools;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.events.listeners.items.ItemToolListener;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.init.MaterialsAether;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class ItemAetherShovel extends ItemSpade implements IDamageLevelsHolder
{
	private float slashDamageLevel = 0, pierceDamageLevel = 0, impactDamageLevel = 0;

	public ItemAetherShovel(final ToolMaterial material)
	{
		this(material, 1.5F, -3.0F);
	}

	public ItemAetherShovel(final ToolMaterial material, final float damageVsEntity, final float attackSpeed)
	{
		super(material);

		this.attackDamage = damageVsEntity + material.getAttackDamage();

		this.attackSpeed = attackSpeed;

		this.setHarvestLevel("shovel", material.getHarvestLevel());

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
		return material == Material.CLAY || material == Material.GROUND || material == Material.GRASS || material == Material.SAND || material == Material.SNOW || material == Material.CRAFTED_SNOW;
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

	public <T extends ItemAetherShovel> T setSlashDamageLevel(float level)
	{
		this.slashDamageLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherShovel> T setPierceDamageLevel(float level)
	{
		this.pierceDamageLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherShovel> T setImpactDamageLevel(float level)
	{
		this.impactDamageLevel = level;

		return (T) this;
	}

	public float getSlashDamageLevel()
	{
		return slashDamageLevel;
	}

	public float getPierceDamageLevel()
	{
		return pierceDamageLevel;
	}

	public float getImpactDamageLevel()
	{
		return impactDamageLevel;
	}
}
