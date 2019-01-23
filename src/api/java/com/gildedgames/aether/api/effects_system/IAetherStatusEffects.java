package com.gildedgames.aether.api.effects_system;

import com.gildedgames.orbis_api.util.mc.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public interface IAetherStatusEffects extends NBT
{
	int TICKS_PER_SECOND = 20;

	/**
	 * Called every Tick.
	 * Handle's applying and managing effects and reducing buildup.
	 * @param livingBase EntityLivingBase being updated.
	 */
	void Tick(EntityLivingBase livingBase);

	/**
	 * Apply the status effect.
	 * @param livingBase EntityLivingBase to apply effect to.
	 * @param timer Current state of the timer.
	 */
	void applyEffect(EntityLivingBase livingBase, int timer);

	/**
	 * Called when the effect ends/wears off.
	 */
	void onEffectEnd();

	/**
	 * Adds buildup to this effect and temporary additional resistances.
	 * Should to calculations on buildup and stored resistance for 0 and MAX checking, as well as applying the modifier additonalResistance.
	 * Resistances to effects are multiplied towards buildup, given this they have limits of 0.0 and 2.0.
	 * The lower the resistance, the higher the actual entity's resistance to buildup is.
	 * The higher the resistance, the lower the actual entity's resistance to buildup is.
	 * @see IAetherStatusEffectPool#addResistanceToEffect(effectTypes, double) for more information.
	 * @param buildup The amount to increment buildup.
	 * @param additionalResistance The amount of temporary additional resistance to modify the buildup amount.
	 */
	void addBuildup(int buildup, double additionalResistance);

	void setBuildup(int buildup);

	void setApplied(boolean isApplied);

	/**
	 * Reduce the buildup by the effect's specified amount of reduction.
	 * Handle's timer incrementing, since the timer should only be active if effect is applied or buildup is greater than 0.
	 */
	void reduceBuildup();

	/**
	 * Reset's all data corresponding to effects.
	 */
	void resetEffect();

	/**
	 * Adds stored resistance's to effects, this is different then the temporary addResistance in addBuildup, but the same rules apply.
	 * @param addResistance The resistance to add.
	 */
	void addResistance(double addResistance);

	/**
	 * The active effect time varies between different effects, this modifier multiples to the active effect time.
	 * 0.0 Modifier will reduce active effect time to 0.
	 * 2.0 Modifier will increase active effect time by 2.
	 * @param activeEffectTimeModifier The modifier to active effect time.
	 */
	void setActiveEffectTimeModifier(double activeEffectTimeModifier);

	int getBuildup();
	double getResistance();
	int getTimer();
	boolean getIsEffectApplied();
	effectTypes getEffectType();
	String getEffectName();
	AttributeModifier getAttributeModifier();

	int NUMBER_OF_EFFECTS = 7;

	enum effectTypes
	{
		AMBROSIUM_POISONING("aether.effect.ambrosium_poisoning",0, 1, 1, 120),
		TOXIN("aether.effect.toxin",1, 1, 1, 10),
		COCKATRICE_VENOM("aether.effect.cockatrice_venom",2, 5, 60, 60),
		STUN("aether.effect.stun", 3,5, 1, 5),
		BLEED("aether.effect.bleed", 4,1,10,0),
		FRACTURE("aether.effect.fracture", 5,1, 1, 60*5),
		FUNGAL_ROT("aether.effect.fungal_rot", 6,1, 10, 20);

		public final int numericValue;			// identifier for this effect.
		public final String name;
		public final int reductionRate;	    	// amount to reduce by.
		public final int timeTillReduction; 	// time between each reduction in seconds.
		public final int activeEffectTime;		// how long the active effect lasts in seconds. (0 is instant)

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
