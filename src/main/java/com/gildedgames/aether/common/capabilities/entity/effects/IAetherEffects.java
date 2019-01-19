package com.gildedgames.aether.common.capabilities.entity.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface IAetherEffects
{
	int TICKS_PER_SECOND = 20;

	boolean isEffectApplied();
	int getActiveEffectTime();
	effectTypes getEffectType();

	AttributeModifier getAttributeModifier();

	/**
	 * Effect's need to be "built up" before they are applied.
	 * @param buildup amount to add to current buildup status.
	 * @param addResistance amount of additional resistance to manipulated buildup.
	 */
	void addBuildup(int buildup, double addResistance);

	/**
	 * reduce the current build up amount.
	 */
	void reduceBuildup();

	void tickStart(TickEvent.PlayerTickEvent event);
	void onUpdate();
	void tickEnd(EntityLivingBase entity);

	/**
	 * Managed when an effect should apply to an entity, or be removed
	 * from an entity.
	 * @param entity entity to apply or remove effect from.
	 */
	void applyEffect(EntityLivingBase entity);

	/**
	 * Reset any data related to effect.
	 */
	void resetEffect();


	enum effectTypes
	{
		AMBROSIUM_POISONING("aether.effect.ambrosium_poisoning",0, 1, 1, 120),
		TOXIN("aether.effect.toxin",1, 1, 1, 10),
		COCKATRICE_VENOM("aether.effect.cockatrice_venom",2, 5, 60*5, 60),
		STUN("aether.effect.stun", 3,5, 1, 5),
		BLEED("aether.effect.bleed", 4,1,10,0),
		FRACTURE("aether.effect.fracture", 5,1, 1, 60*5),
		FUNGAL_ROT("aether.effect.fungal_rot", 6,1, 1, 20);

		final int numericValue;			// identifier for this effect.
		final String name;
		final int reductionRate;	    // amount to reduce by.
		final int timeTillReduction; 	// time between each reduction in seconds.
		final int activeEffectTime;		// how long the active effect lasts in seconds. (0 is instant)

		effectTypes(String name, int numericValue, int reductionRate, int timeTillReduction, int activeEffectTime) {
			this.name = name;
			this.numericValue = numericValue;
			this.reductionRate = reductionRate;
			this.timeTillReduction = timeTillReduction;
			this.activeEffectTime = activeEffectTime;
		}

		public static effectTypes getEffectFromNumericValue(int numericValue)
		{
			switch(numericValue)
			{
				case 0:
					return AMBROSIUM_POISONING;
				case 1:
					return TOXIN;
				case 2:
					return COCKATRICE_VENOM;
				case 3:
					return STUN;
				case 4:
					return BLEED;
				case 5:
					return FRACTURE;
				case 6:
					return FUNGAL_ROT;
				default:
					return null;
			}
		}
	}
}
