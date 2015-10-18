package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.common.AetherCreativeTabs;
import com.gildedgames.aether.common.AetherMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemElementalSword extends ItemSword
{
	public enum SwordElement
	{
		FIRE("Ignites mobs")
		{
			@Override
			public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
			{
				int fireMultiplier = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);

				target.setFire(6 + (fireMultiplier * 4));
			}
		},
		LIGHTNING("Smites with lightning")
		{
			@Override
			public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
			{
				if (!target.worldObj.isRemote)
				{
					target.worldObj.addWeatherEffect(new EntityLightningBolt(target.worldObj, target.posX, target.posY, target.posZ));
				}
			}
		},
		HOLY("Obliterates undead")
		{
			@Override
			public void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
			{
				float damage = target.isEntityUndead() ? 12.0f : AetherMaterials.LEGENDARY_TOOL.getDamageVsEntity();

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

		private final String abilityDesc;

		SwordElement(String abilityDesc)
		{
			this.abilityDesc = abilityDesc;
		}

		public String getFormattedAbilityDesc()
		{
			return EnumChatFormatting.BLUE + "Ability: " + EnumChatFormatting.WHITE + this.abilityDesc;
		}

		public abstract void onEntityAttacked(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker);
	}

	private final SwordElement element;

	public ItemElementalSword(SwordElement element)
	{
		super(AetherMaterials.LEGENDARY_TOOL);

		this.element = element;

		this.setMaxDamage(500);

		this.setCreativeTab(AetherCreativeTabs.tabWeapons);
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

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		tooltip.add(this.element.getFormattedAbilityDesc());
	}
}
