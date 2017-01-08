package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import com.gildedgames.aether.common.registry.content.MaterialsAether;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ItemElementalSword extends ItemAetherSword
{
	public enum SwordElement
	{
		FIRE
				{
					@Override
					public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
					{
						int fireMultiplier = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fire_aspect"), stack);

						target.setFire(6 + (fireMultiplier * 4));
					}
				},
		LIGHTNING
				{
					@Override
					public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
					{
						if (!target.worldObj.isRemote)
						{
							target.worldObj.addWeatherEffect(new EntityLightningBolt(target.worldObj, target.posX, target.posY, target.posZ, false));
						}
					}
				},
		HOLY
				{
					@Override
					public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
					{
						float damage = target.isEntityUndead() ? 12.0f : MaterialsAether.LEGENDARY_TOOL.getDamageVsEntity();

						if (attacker instanceof EntityPlayer)
						{
							target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), damage);
						}
						else
						{
							target.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage);
						}
					}
				};

		public abstract void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker);
	}

	private final SwordElement element;

	public ItemElementalSword(SwordElement element)
	{
		super(MaterialsAether.LEGENDARY_TOOL, ItemAbilityType.PASSIVE);

		this.element = element;

		this.setMaxDamage(500);

		this.setCreativeTab(CreativeTabsAether.WEAPONS);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		this.element.onEntityAttacked(stack, target, attacker);

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public float getDamageVsEntity()
	{
		if (this.element == SwordElement.HOLY)
		{
			// We apply our own DamageSource in SwordElement.HOLY.hitEntity(stack, target, attacker).
			return 0.0F;
		}

		return super.getDamageVsEntity();
	}
}
