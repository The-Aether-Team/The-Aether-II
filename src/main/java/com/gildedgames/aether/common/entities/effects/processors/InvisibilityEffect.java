package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.List;

public class InvisibilityEffect extends AbstractEffectProcessor<EntityEffectInstance>
{

	public InvisibilityEffect()
	{
		super("ability.invisibility.localizedName", "ability.invisibility.desc");
	}

	@Override
	public void tick(Entity source, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		((EntityLivingBase) source).addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 5, 0, true, false));
	}

}
