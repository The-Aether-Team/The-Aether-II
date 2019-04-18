package com.gildedgames.aether.api.effects_system;

import com.gildedgames.aether.api.AetherCapabilities;
import com.gildedgames.orbis.lib.util.mc.NBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;
import java.util.Collection;

public interface IAetherStatusEffects extends NBT
{
	int TICKS_PER_SECOND = 20;

	/**
	 * Called every tick.
	 * Handle's applying and managing effects and reducing buildup.
	 * @param livingBase EntityLivingBase being updated.
	 */
	void tick(EntityLivingBase livingBase);

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
	 * @param buildup The amount to increment buildup, excludes negatives since reductions of buildup should be done in #setBuildup or #reduceBuildup
	 * @param additionalResistance The amount of temporary additional resistance to modify the buildup amount.
	 */
	void addBuildup(@Nonnegative int buildup, double additionalResistance);

	/**
	 * Assign the buildup of this effect to a certain value. Primarily used for curing effects, resetting, and network sync.
	 * @param buildup the buildup to be assigned to effect.
	 */
	void setBuildup(@Nonnegative int buildup);

	/**
	 * Set whether effect is applied or not.
	 * Note applied references whether effect is currently effecting an entity, not whether buildup is applied.
	 * @param isApplied boolean representing what state effect should be in.
	 */
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
	 * Reset's the resistance to this effect back to default (1.0)
	 */
	void resetResistance();

	/**
	 * Calculates all resistances, including temporary ones (like from Ambrosium Poisoning effect),
	 * and ensures they are in the correct value ranges, and returns result.
	 * @return calculated resistance, including temporary resistance.
	 */
	double calculateResistances();

	/**
	 * The active effect time varies between different effects, this modifier multiples to the active effect time.
	 * 0.0 Modifier will reduce active effect time to 0.
	 * 2.0 Modifier will increase active effect time by 2.
	 * @param activeEffectTimeModifier The modifier to active effect time.
	 */
	void setActiveEffectTimeModifier(double activeEffectTimeModifier);

	/**
	 * Add information about the effect (it's name) from lang file, to provided collection.
	 * @param label Collection to add information too.
	 */
	@SideOnly(Side.CLIENT)
	void addInformation(Collection<String> label);

	/**
	 * Retrieves the amount of buildup to use from a specified intensity (minor, ordinary, and major).
	 * @param intensity intensity to get buildup for.
	 * @return buildup according to instensity.
	 */
	int getBuildupFromIntensity(EEffectIntensity intensity);

	/**
	 * Check if living has appropriate capabilities, and apply buildup for status effect.
	 * @param livingBase living to apply effect buildup to.
	 * @param effectType effect to apply buildup for.
	 * @param buildup amount of buildup to add.
	 * @return boolean if successful.
	 */
	static boolean applyStatusEffect(EntityLivingBase livingBase, effectTypes effectType, @Nonnegative int buildup)
	{
		if (livingBase.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
		{
			livingBase.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).applyStatusEffect(effectType, buildup);
			return true;
		}

		return false;
	}

	static boolean isEffectApplying(EntityLivingBase livingBase, effectTypes effectType)
	{
		if (livingBase.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
		{
			return livingBase.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).isEffectApplied(effectType);
		}

		return false;
	}

	static boolean doesEffectHaveBuildup(EntityLivingBase livingBase, effectTypes effectType)
	{
		if (livingBase.hasCapability(AetherCapabilities.STATUS_EFFECT_POOL, null))
		{
			return (livingBase.getCapability(AetherCapabilities.STATUS_EFFECT_POOL, null).getBuildupFromEffect(effectType) > 0);
		}

		return false;
	}

	boolean isDirty();
	void markDirty();
	void markClean();

	int getBuildup();
	double getResistance();
	int getTimer();
	boolean getIsEffectApplied();
	effectTypes getEffectType();

	/**
	 * @return the effect's name in code, does not refer to how the effect should read on a GUI.
	 * @see #addInformation(Collection) to get the effect's readable name.
	 */
	String getEffectName();

	@Nullable
	AttributeModifier getAttributeModifier();

	int NUMBER_OF_EFFECTS = 7;

	enum effectTypes
	{
		AMBROSIUM_POISONING("aether.effect.ambrosium_poisoning",0, 1, 1, 120,10),
		TOXIN("aether.effect.toxin",1, 1, 1, 10,10),
		COCKATRICE_VENOM("aether.effect.cockatrice_venom",2, 5, 60, 60,10),
		STUN("aether.effect.stun", 3,5, 1, 5,10),
		BLEED("aether.effect.bleed", 4,1,10,0,10),
		FRACTURE("aether.effect.fracture", 5,1, 1, 60*5,10),
		FUNGAL_ROT("aether.effect.fungal_rot", 6,1, 10, 20,10);

		public final int numericValue;			// identifier for this effect.
		public final String name;
		public final int reductionRate;	    	// amount to reduce by.
		public final int timeTillReduction; 	// time between each reduction in seconds.
		public final int activeEffectTime;		// how long the active effect lasts in seconds. (0 is instant)
		public final int buildupSpeed;

		effectTypes(String name, int numericValue, int reductionRate, int timeTillReduction, int activeEffectTime, int buildupSpeed) {
			this.name = name;
			this.numericValue = numericValue;
			this.reductionRate = reductionRate;
			this.timeTillReduction = timeTillReduction;
			this.activeEffectTime = activeEffectTime;
			this.buildupSpeed = buildupSpeed;
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
