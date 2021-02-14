package com.gildedgames.aether.common.items.weapons.swords;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.registrar.ItemsAether;
import com.gildedgames.aether.common.entities.effects.IEffectDamageHolder;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import com.gildedgames.aether.common.init.CreativeTabsAether;
import com.gildedgames.aether.common.items.ItemAbilityType;
import com.gildedgames.aether.common.items.tools.ItemAetherShovel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class ItemAetherSword extends ItemSword implements IDamageLevelsHolder, IEffectDamageHolder
{
	private Map<StatusEffect, Integer> statusEffects = new HashMap<>();

	private float slashDamageLevel = 0, pierceDamageLevel = 0, impactDamageLevel = 0;

	private final ItemAbilityType abilityType;

	public ItemAetherSword(final ToolMaterial material, final ItemAbilityType abilityType)
	{
		super(material);

		this.abilityType = abilityType;

		this.setCreativeTab(CreativeTabsAether.TAB_WEAPONS);
	}

	@Override
	public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker)
	{
		IAetherStatusEffectPool statusEffectPool = target.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool != null)
		{
			if (!this.statusEffects.isEmpty())
			{
				for (Map.Entry<StatusEffect, Integer> effect : this.statusEffects.entrySet())
				{
					IAetherStatusEffects actualEffect = statusEffectPool.createEffect(effect.getKey().getEffectType().name, target);

					if (!statusEffectPool.effectExists(actualEffect.getEffectType()))
					{
						statusEffectPool.applyStatusEffect(actualEffect.getEffectType(), effect.getValue());
					}
					else
					{
						statusEffectPool.modifyActiveEffectBuildup(actualEffect.getEffectType(),
								statusEffectPool.getBuildupFromEffect(actualEffect.getEffectType()) + effect.getValue());
					}
				}
			}
		}

		return super.hitEntity(stack, target, attacker);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag flag)
	{
		if (this.abilityType != ItemAbilityType.NONE)
		{
			tooltip.add(String.format("%s: %s",
					TextFormatting.BLUE + I18n.format("item.aether.tooltip.ability"),
					TextFormatting.WHITE + I18n.format(this.getTranslationKey() + ".ability.desc")));

			if (!this.abilityType.isPassive())
			{
				tooltip.add(String.format("%s: %s",
						TextFormatting.DARK_AQUA + I18n.format("item.aether.tooltip.use"),
						TextFormatting.WHITE + I18n.format(this.getTranslationKey() + ".use.desc")));
			}
		}
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		if (this == ItemsAether.skyroot_sword)
		{
			return 100;
		}

		return super.getItemBurnTime(itemStack);
	}

	public <T extends ItemAetherSword> T setSlashDamageLevel(float level)
	{
		this.slashDamageLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherSword> T setPierceDamageLevel(float level)
	{
		this.pierceDamageLevel = level;

		return (T) this;
	}

	public <T extends ItemAetherSword> T setImpactDamageLevel(float level)
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

	public <T extends ItemAetherSword> T addStatusEffect(StatusEffect effect, int amount)
	{
		this.statusEffects.put(effect, amount);

		return (T) this;
	}

	public Map<StatusEffect, Integer> getStatusEffects()
	{
		return this.statusEffects;
	}
}
