package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.List;

public class FireImmunityEffect extends AbstractEffectProcessor<EntityEffectInstance>
{

	public FireImmunityEffect()
	{
		super("ability.fireImmunity.name", "ability.fireImmunity.desc");
	}

	@Override
	public void tick(Entity source, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase)source;

		living.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2, 0, false, false));

		living.extinguish();
	}

}
