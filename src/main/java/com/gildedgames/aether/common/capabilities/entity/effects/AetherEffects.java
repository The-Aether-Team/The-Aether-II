package com.gildedgames.aether.common.capabilities.entity.effects;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

abstract public class AetherEffects implements IAetherEffects
{

	private boolean isEffectApplied;
	private IAetherEffects.effectTypes effectType;

	private final String NAME;
	private final int REDUCTION_RATE;
	private final int TIME_TILL_REDUCTION;
	private final int ACTIVE_EFFECT_TIME;

	protected int effectTimer;
	protected int effectBuildup;

	private double effectResistance = 1.0D;

	private final AttributeModifier EFFECT_MODIFIER;

	public AetherEffects(IAetherEffects.effectTypes effectType, AttributeModifier effectModifier)
	{
		this.effectType = effectType;
		this.isEffectApplied = false;

		this.NAME = effectType.name;
		this.REDUCTION_RATE = effectType.reductionRate;
		this.TIME_TILL_REDUCTION = effectType.timeTillReduction;
		this.ACTIVE_EFFECT_TIME = effectType.activeEffectTime;

		this.EFFECT_MODIFIER = effectModifier;
	}

	@Override
	public boolean isEffectApplied()
	{
		return this.isEffectApplied;
	}

	@Override
	public int getActiveEffectTime()
	{
		return this.ACTIVE_EFFECT_TIME;
	}

	@Override
	public effectTypes getEffectType()
	{
		return this.effectType;
	}

	@Override
	public AttributeModifier getAttributeModifier()
	{
		return this.EFFECT_MODIFIER;
	}

	public void setEffectState(boolean newEffectState)
	{
		this.isEffectApplied = newEffectState;
	}

	@Override
	public void addBuildup(int buildup, double addResistance)
	{
		this.effectBuildup = this.effectBuildup + MathHelper.ceil(buildup * (this.effectResistance + addResistance));

		if (this.effectBuildup >= 100)
		{
			this.effectBuildup = 101;
		}
		if (this.effectBuildup < 0)
		{
			this.effectBuildup = 0;
		}

		this.effectTimer = 0;		// reset timer since effect is applied again.
	}

	@Override
	public void reduceBuildup()
	{
		if (this.effectBuildup > 0 || this.isEffectApplied)
		{
			++ this.effectTimer;

			if (!this.isEffectApplied)
			{
				if (this.effectTimer % (this.TIME_TILL_REDUCTION * TICKS_PER_SECOND) == 0)
				{
					this.effectBuildup = this.effectBuildup - this.REDUCTION_RATE;
				}
			}
		}
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (this.effectBuildup >= 101)
		{
			this.isEffectApplied = true;
			AetherCore.LOGGER.info("Effect Applied : " + this.getEffectName());
			this.effectBuildup = 100;
		}
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public void tickEnd(EntityLivingBase entity)
	{
		this.applyEffect(entity);
		this.manageActiveEffect();
		this.reduceBuildup();

		if (this.effectBuildup <= 0)
		{
			if (!this.isEffectApplied)
			{
				this.effectTimer = 0;
			}
			this.effectBuildup = 0;
		}
	}

	@Override
	abstract public void applyEffect(EntityLivingBase entity);


	@Override
	public void resetEffect()
	{
		this.isEffectApplied = false;
		this.effectBuildup = 0;
		this.effectTimer = 0;

		AetherCore.LOGGER.info("Effect Reset : " + this.NAME);
	}

	public String getEffectName()
	{
		return this.NAME;
	}

	public int getAdjustedTimer()
	{
		return this.effectTimer * TICKS_PER_SECOND;
	}

	public int getEffectBuildup()
	{
		return this.effectBuildup;
	}

	public int getReductionRate()
	{
		return this.REDUCTION_RATE;
	}

	public int getTimeTillReduction()
	{
		return this.TIME_TILL_REDUCTION;
	}

	private void manageActiveEffect()
	{
		if (this.isEffectApplied)
		{
			if (this.effectTimer >= this.ACTIVE_EFFECT_TIME * TICKS_PER_SECOND)
			{
				this.resetEffect();
			}
		}
	}
}
