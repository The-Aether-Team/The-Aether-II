package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerEffectsModule extends PlayerAetherModule
{
	private final int TICKS_PER_SECOND = 20;
	//private static final UUID STUN_ID = UUID.fromString("5d83914f-188a-4dc0-9b3e-8156d2dbd5a0");

	private static final AttributeModifier STUN_MODIFIER = (new AttributeModifier("aether.effect_stun", -1D, 1)).setSaved(false);
	private static final AttributeModifier GENTLE_SLOW_MODIFIER = (new AttributeModifier("aether.effect_gentle_slow", -0.25D, 1)).setSaved(false);
	private static final AttributeModifier SLOW_MODIFIER = (new AttributeModifier("aether.effect_slow", -0.5D, 1)).setSaved(false);
	private static final AttributeModifier SUPER_SLOW_MODIFIER = (new AttributeModifier("aether.effect_super_slow", -0.75D, 1)).setSaved(false);
	private static final AttributeModifier DECREASE_ARMOR_MODIFIER = (new AttributeModifier("aether.effect_decrease_armor", -0.5D, 1)).setSaved(false);
	private static final AttributeModifier DECREASE_ATTACK_SPEED_MODIFIER = (new AttributeModifier("aether.effect_decrease_attack_speed", -0.5D, 1)).setSaved(false);
	//private static final AttributeModifier DECREASE_MAX_HEALTH = (new AttributeModifier("aether.decrease_max_health", -0.3D, 1)).setSaved(false);

	private HashMap<Integer, Integer> entityAilments;

	public double ambrosiumResistance = 1D;
	public double toxinResistance = 1D;
	public double cockatriceVenomResistance = 1D;
	public double stunResistance = 1D;
	public double bleedResistance = 1D;
	public double fractureResistance = 1D;
	public double fungalRotResistance = 1D;

	private int ambrosiumPoisonTimer = 0;
	private int toxinTimer = 0;
	private int cockatriceVenomTimer = 0;
	private int stunTimer = 0;
	private int bleedTimer = 0;
	private int fractureTimer = 0;
	private int fungalRotTimer = 0;

	public boolean isAmbrosiumPoisoned = false;
	public boolean isToxic = false;
	public boolean isCockatriceVenomed = false;
	public boolean isStunned = false;
	public boolean isBleeding = false;
	public boolean isFractured = false;
	public boolean isFungalRot = false;

	private float prevAbsorptionEffect = 0;

	public PlayerEffectsModule(PlayerAether player)
	{
		super(player);

		this.initMap();
	}

	private void initMap()
	{
		this.entityAilments = new HashMap<>();
		this.entityAilments.put(effectTypes.AMBROSIUM_POISONING.numericValue, 0);
		this.entityAilments.put(effectTypes.TOXIN.numericValue, 0);
		this.entityAilments.put(effectTypes.COCKATRICE_VENOM.numericValue, 0);
		this.entityAilments.put(effectTypes.STUN.numericValue, 0);
		this.entityAilments.put(effectTypes.BLEED.numericValue, 0);
		this.entityAilments.put(effectTypes.FRACTURE.numericValue, 0);
		this.entityAilments.put(effectTypes.FUNGAL_ROT.numericValue, 0);
	}

	private int getAdjustedResistance(effectTypes effect)
	{
		double additionalResistanceMultiplier = 1.0D;

		if (this.isAmbrosiumPoisoned)
		{
			additionalResistanceMultiplier = additionalResistanceMultiplier + 0.5D; // add a -50% resistance to additional multiplier.
		}

		switch(effect)
		{
			case AMBROSIUM_POISONING:
				return MathHelper.ceil(this.ambrosiumResistance * additionalResistanceMultiplier);
			case TOXIN:
				return MathHelper.ceil(this.toxinResistance * additionalResistanceMultiplier);
			case COCKATRICE_VENOM:
				return MathHelper.ceil(this.cockatriceVenomResistance * additionalResistanceMultiplier);
			case STUN:
				return MathHelper.ceil(this.stunResistance * additionalResistanceMultiplier);
			case BLEED:
				return MathHelper.ceil(this.bleedResistance * additionalResistanceMultiplier);
			case FRACTURE:
				return MathHelper.ceil(this.fractureResistance * additionalResistanceMultiplier);
			case FUNGAL_ROT:
				return MathHelper.ceil(this.fungalRotResistance * additionalResistanceMultiplier);
		}

		return 1;
	}

	public void applyAilment(effectTypes effect, int amount)
	{
		int adjustedAmount = amount;

		switch (effect)
		{
			case AMBROSIUM_POISONING:
				this.ambrosiumPoisonTimer = 0;
				break;
			case TOXIN:
				this.toxinTimer = 0;
				break;
			case COCKATRICE_VENOM:
				this.cockatriceVenomTimer = 0;
				break;
			case STUN:
				this.stunTimer = 0;
				break;
			case BLEED:
				this.bleedTimer = 0;
				break;
			case FRACTURE:
				this.fractureTimer = 0;
				break;
			case FUNGAL_ROT:
				this.fungalRotTimer = 0;
				break;
		}

		adjustedAmount = amount * this.getAdjustedResistance(effect);

		if (this.entityAilments.get(effect.numericValue) + adjustedAmount >= 100) {
			this.entityAilments.put(effect.numericValue, 101);
			return;
		}

		if (this.entityAilments.get(effect.numericValue) + adjustedAmount < 0) {
			this.entityAilments.put(effect.numericValue, 0);
			return;
		}

		this.entityAilments.put(effect.numericValue, this.entityAilments.get(effect.numericValue) + adjustedAmount);
	}


	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().isCreative())
		{
			this.resetAll();
			return;
		}

		Iterator it = this.entityAilments.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			if ((int)pair.getValue() == 101)
			{
				effectTypes effect = effectTypes.getEffectFromNumericValue((int)pair.getKey());
				if (effect == null)
				{
					return;
				}
				switch (effect)
				{
					case AMBROSIUM_POISONING:
						this.isAmbrosiumPoisoned = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is Ambrosium Poisoned.");
						pair.setValue(100);
						break;
					case TOXIN:
						this.isToxic = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is Toxic.");
						pair.setValue(100);
						break;
					case COCKATRICE_VENOM:
						this.isCockatriceVenomed = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is effected by Cockatrice Venom.");
						pair.setValue(100);
						break;
					case STUN:
						this.isStunned = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is Stunned.");
						pair.setValue(100);
						break;
					case BLEED:
						this.isBleeding = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is Bleeding.");
						pair.setValue(100);
						break;
					case FRACTURE:
						this.isFractured = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is Fractured.");
						pair.setValue(100);
						break;
					case FUNGAL_ROT:
						this.isFungalRot = true;
						AetherCore.LOGGER.info(this.getEntity().getName() + " is effected by Fungal Rot.");
						pair.setValue(100);
						break;
				}
			}
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
		if (this.getEntity().isCreative())
		{
			return;
		}

		this.applyTickEffects();
		this.manageActiveEffects();
		this.doReductions();

		/* 0 checking */
		if (this.entityAilments.get(effectTypes.AMBROSIUM_POISONING.numericValue) <= 0)
		{
			if (!this.isAmbrosiumPoisoned)
			{
				this.ambrosiumPoisonTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.AMBROSIUM_POISONING.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.AMBROSIUM_POISONING.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.TOXIN.numericValue) <= 0)
		{
			if (!this.isToxic)
			{
				this.toxinTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.TOXIN.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.TOXIN.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.COCKATRICE_VENOM.numericValue) <= 0)
		{
			if (!this.isCockatriceVenomed)
			{
				this.cockatriceVenomTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.COCKATRICE_VENOM.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.COCKATRICE_VENOM.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.STUN.numericValue) <= 0)
		{
			if (!this.isStunned)
			{
				this.stunTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.STUN.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.STUN.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.BLEED.numericValue) <= 0)
		{
			if (!this.isBleeding)
			{
				this.bleedTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.BLEED.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.BLEED.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.FRACTURE.numericValue) <= 0)
		{
			if (!this.isFractured)
			{
				this.fractureTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.FRACTURE.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.FRACTURE.numericValue, 0);
			}
		}

		if (this.entityAilments.get(effectTypes.FUNGAL_ROT.numericValue) <= 0)
		{
			if (!this.isFungalRot)
			{
				this.fungalRotTimer = 0;
			}
			if (this.entityAilments.get(effectTypes.FUNGAL_ROT.numericValue) < 0)
			{
				this.entityAilments.put(effectTypes.FUNGAL_ROT.numericValue, 0);
			}
		}
	}

	private void applyTickEffects()
	{
		if (this.getWorld().isRemote)
		{
			return;
		}

		IAttributeInstance iAttributeInstance;

		/* Apply bleed, since bleeding is instantaneous requires no further logic */
		if (this.isBleeding)
		{
			this.getEntity().attackEntityFrom(DamageSource.MAGIC, 10);
		}

		/* Apply toxic per half second, while player is above 3 health. */
		if (this.isToxic && this.getEntity().getHealth() > 6 && this.toxinTimer % (this.TICKS_PER_SECOND/2) == 0)
		{
			this.getEntity().attackEntityFrom(DamageSource.MAGIC, 1f);
		}

		/* Apply cockatrice venom per second, while player is above half heart. */
		if (this.isCockatriceVenomed && this.getEntity().getHealth() > 1 && this.cockatriceVenomTimer % (this.TICKS_PER_SECOND) == 0)
		{
			this.getEntity().attackEntityFrom(DamageSource.MAGIC, 1f);
		}

		/* Apply movement speed debuffs */
		iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		/* Apply fungal rot damage every 2 seconds. */
		if (this.isFungalRot && this.fungalRotTimer % (this.TICKS_PER_SECOND * 2) == 0)
		{
			this.getEntity().attackEntityFrom(DamageSource.MAGIC, 1f);
			if (!iAttributeInstance.hasModifier(SUPER_SLOW_MODIFIER))
			{
				iAttributeInstance.applyModifier(SUPER_SLOW_MODIFIER);
			}
		}
		else if (!this.isFungalRot)
		{
			if (iAttributeInstance.getModifier(SUPER_SLOW_MODIFIER.getID()) != null)
			{
				iAttributeInstance.removeModifier(SUPER_SLOW_MODIFIER);
			}
		}

		/* Apply Stun */
		if (this.isStunned)
		{
			if (!iAttributeInstance.hasModifier(STUN_MODIFIER))
			{
				iAttributeInstance.applyModifier(STUN_MODIFIER);
			}
		}
		else
		{
			if (iAttributeInstance.getModifier(STUN_MODIFIER.getID()) != null)
			{
				iAttributeInstance.removeModifier(STUN_MODIFIER);
			}
		}

		/* Apply Fracture */
		if (this.isFractured)
		{
			/* Apply slow effect */
			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if (!iAttributeInstance.hasModifier(GENTLE_SLOW_MODIFIER))
			{
				iAttributeInstance.applyModifier(GENTLE_SLOW_MODIFIER);
			}

			/* Decrease Armor */
			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (!iAttributeInstance.hasModifier(DECREASE_ARMOR_MODIFIER))
			{
				iAttributeInstance.applyModifier(DECREASE_ARMOR_MODIFIER);
			}

			/* Decrease Attack Speed */
			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
			if (!iAttributeInstance.hasModifier(DECREASE_ATTACK_SPEED_MODIFIER))
			{
				iAttributeInstance.applyModifier(DECREASE_ATTACK_SPEED_MODIFIER);
			}
		}
		else
		{
			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if (iAttributeInstance.getModifier(GENTLE_SLOW_MODIFIER.getID()) != null)
			{
				iAttributeInstance.removeModifier(GENTLE_SLOW_MODIFIER);
			}

			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.ARMOR);
			if (iAttributeInstance.getModifier(DECREASE_ARMOR_MODIFIER.getID()) != null)
			{
				iAttributeInstance.removeModifier(DECREASE_ARMOR_MODIFIER);
			}

			iAttributeInstance = this.getEntity().getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
			if (iAttributeInstance.getModifier(DECREASE_ATTACK_SPEED_MODIFIER.getID()) != null)
			{
				iAttributeInstance.removeModifier(DECREASE_ATTACK_SPEED_MODIFIER);
			}
		}
	}

	private void doReductions()
	{
		/* Ambrosium Poisoining */
		if (this.entityAilments.get(effectTypes.AMBROSIUM_POISONING.numericValue) > 0 || this.isAmbrosiumPoisoned)
		{
			++ this.ambrosiumPoisonTimer;

			if (!this.isAmbrosiumPoisoned)
			{
				if (this.ambrosiumPoisonTimer % (effectTypes.AMBROSIUM_POISONING.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.AMBROSIUM_POISONING.numericValue,
							this.entityAilments.get(effectTypes.AMBROSIUM_POISONING.numericValue) - effectTypes.AMBROSIUM_POISONING.reductionRate);
				}
			}
		}

		/* Toxin */
		if (this.entityAilments.get(effectTypes.TOXIN.numericValue) > 0 || this.isToxic)
		{
			++ this.toxinTimer;

			if (!this.isToxic)
			{
				if (this.toxinTimer % (effectTypes.TOXIN.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.TOXIN.numericValue,
							this.entityAilments.get(effectTypes.TOXIN.numericValue) - effectTypes.TOXIN.reductionRate);
				}
			}
		}

		/* Cockatrice Venom */
		if (this.entityAilments.get(effectTypes.COCKATRICE_VENOM.numericValue) > 0 || this.isCockatriceVenomed)
		{
			++ this.cockatriceVenomTimer;

			if (!this.isCockatriceVenomed)
			{
				if (this.cockatriceVenomTimer % (effectTypes.COCKATRICE_VENOM.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.COCKATRICE_VENOM.numericValue,
							this.entityAilments.get(effectTypes.COCKATRICE_VENOM.numericValue) - effectTypes.COCKATRICE_VENOM.reductionRate);
				}
			}
		}

		/* Stun */
		if (this.entityAilments.get(effectTypes.STUN.numericValue) > 0 || this.isStunned)
		{
			++ this.stunTimer;

			if (!this.isStunned)
			{
				if (this.stunTimer % (effectTypes.STUN.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.STUN.numericValue,
							this.entityAilments.get(effectTypes.STUN.numericValue) - effectTypes.STUN.reductionRate);
				}
			}
		}

		/* Bleed */
		if (this.entityAilments.get(effectTypes.BLEED.numericValue) > 0 || this.isBleeding)
		{
			++ this.bleedTimer;

			if (!this.isBleeding)
			{
				if (this.bleedTimer % (effectTypes.BLEED.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.BLEED.numericValue,
							this.entityAilments.get(effectTypes.BLEED.numericValue) - effectTypes.BLEED.reductionRate);
				}
			}
		}

		/* Fracture */
		if (this.entityAilments.get(effectTypes.FRACTURE.numericValue) > 0 || this.isFractured)
		{
			++ this.fractureTimer;

			if (!this.isFractured)
			{
				if (this.fractureTimer % (effectTypes.FRACTURE.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.FRACTURE.numericValue,
							this.entityAilments.get(effectTypes.FRACTURE.numericValue) - effectTypes.FRACTURE.reductionRate);
				}
			}
		}

		/* Fungal Rot */
		if (this.entityAilments.get(effectTypes.FUNGAL_ROT.numericValue) > 0 || this.isFungalRot)
		{
			++ this.fungalRotTimer;

			if (!this.isFungalRot)
			{
				if (this.fungalRotTimer % (effectTypes.FUNGAL_ROT.timeTillReduction * this.TICKS_PER_SECOND) == 0)
				{
					this.entityAilments.put(effectTypes.FUNGAL_ROT.numericValue,
							this.entityAilments.get(effectTypes.FUNGAL_ROT.numericValue) - effectTypes.FUNGAL_ROT.reductionRate);
				}
			}
		}
	}

	private void manageActiveEffects()
	{
		if (this.isAmbrosiumPoisoned)
		{
			if (this.ambrosiumPoisonTimer == effectTypes.AMBROSIUM_POISONING.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isAmbrosiumPoisoned = false;
				this.ambrosiumPoisonTimer = 0;
				this.entityAilments.put(effectTypes.AMBROSIUM_POISONING.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer Ambrosium Poisoined.");
			}
		}

		if (this.isToxic)
		{
			if (this.toxinTimer >= effectTypes.TOXIN.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isToxic = false;
				this.toxinTimer = 0;
				this.entityAilments.put(effectTypes.TOXIN.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer Toxic.");
			}
		}

		if (this.isCockatriceVenomed)
		{
			if (this.cockatriceVenomTimer >= effectTypes.COCKATRICE_VENOM.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isCockatriceVenomed = false;
				this.cockatriceVenomTimer = 0;
				this.entityAilments.put(effectTypes.COCKATRICE_VENOM.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer effected by Cockatrice Venom.");
			}
		}

		if (this.isStunned)
		{
			if (this.stunTimer >= effectTypes.STUN.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isStunned = false;
				this.stunTimer = 0;
				this.entityAilments.put(effectTypes.STUN.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer Stunned.");
			}
		}

		if (this.isBleeding)
		{
			if (this.bleedTimer >= effectTypes.BLEED.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isBleeding = false;
				this.bleedTimer = 0;
				this.entityAilments.put(effectTypes.BLEED.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer Bleeding.");
			}
		}

		if (this.isFractured)
		{
			if (this.fractureTimer == effectTypes.FRACTURE.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isFractured = false;
				this.fractureTimer = 0;
				this.entityAilments.put(effectTypes.FRACTURE.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer Fractured.");
			}
		}

		if (this.isFungalRot)
		{
			if (this.fungalRotTimer == effectTypes.FUNGAL_ROT.activeEffectTime * this.TICKS_PER_SECOND)
			{
				this.isFungalRot = false;
				this.fungalRotTimer = 0;
				this.entityAilments.put(effectTypes.FUNGAL_ROT.numericValue, 0);

				AetherCore.LOGGER.info(this.getEntity().getName() + " : No longer effected by Fungal Rot.");
			}
		}
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public void onDeath(final LivingDeathEvent event)
	{
		this.resetAll();
	}

	private void resetAll()
	{
		this.isAmbrosiumPoisoned = false;
		this.isToxic = false;
		this.isCockatriceVenomed = false;
		this.isStunned = false;
		this.isBleeding = false;
		this.isFractured = false;
		this.isFungalRot = false;

		this.ambrosiumPoisonTimer = 0;
		this.toxinTimer = 0;
		this.cockatriceVenomTimer = 0;
		this.stunTimer = 0;
		this.bleedTimer = 0;
		this.fractureTimer = 0;
		this.fungalRotTimer = 0;

		AetherCore.LOGGER.info(this.getEntity().getName() + " : All effects reset.");
	}

	@Override
	public void write(NBTTagCompound tag)
	{

	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}

	public enum effectTypes
	{
		AMBROSIUM_POISONING("aether.effect_ambrosium_poisoning",0, 1, 1, 120),
		TOXIN("aether.effect_toxin",1, 1, 1, 10),
		COCKATRICE_VENOM("aether.effect_cockatrice_venom",2, 5, 60*5, 60),
		STUN("aether.effect_stun", 3,5, 1, 5),
		BLEED("aether.effect_bleed", 4,1,10,0),
		FRACTURE("aether.effect_fracture", 5,1, 1, 60*5),
		FUNGAL_ROT("aether.effect_fungal_rot", 6,1, 1, 20);

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
