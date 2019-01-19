package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class EffectAmbrosiumPoisoning extends AetherEffects
{
	public EffectAmbrosiumPoisoning()
	{
		super(effectTypes.AMBROSIUM_POISONING, null);
	}

	@Override
	public void applyEffect(EntityLivingBase entity)
	{
		/**
		 * Ambrosium Poisoning has a unique effect, and is handled elsewhere.
		 * reduce all resistances by 50%.
		 * @see com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerEffectsModule#applyAilment(effectTypes, int)
		 *
		 * Negate weak player HP regeneration.
		 * @see com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherHooks#onLivingHeal(LivingHealEvent)
		 */
	}
}
