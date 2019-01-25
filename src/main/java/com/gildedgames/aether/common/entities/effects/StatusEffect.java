package com.gildedgames.aether.common.entities.effects;

import com.gildedgames.aether.api.effects_system.EEffectIntensity;
import com.gildedgames.aether.api.effects_system.IAetherStatusEffects;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.effects.PacketStatusEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

public abstract class StatusEffect implements IAetherStatusEffects
{
	protected final String NAME;
	protected final int REDUCTION_RATE;
	protected final int TIME_TILL_REDUCTION;
	protected final int ACTIVE_EFFECT_TIME;

	private final AttributeModifier ATTRIBUTE_MODIFIER;

	protected int effectBuildup;
	protected int effectTimer;
	protected double effectResistance = 1.0D;
	protected IAetherStatusEffects.effectTypes effectType;
	protected boolean isEffectApplied;
	protected double activeEffectTimeModifier = 1.0D;

	private final EntityLivingBase livingEffected;

	public StatusEffect(IAetherStatusEffects.effectTypes effectType, AttributeModifier attributeModifier, EntityLivingBase living)
	{
		this.effectType = effectType;
		this.isEffectApplied = false;

		this.NAME = effectType.name;
		this.REDUCTION_RATE = effectType.reductionRate;
		this.TIME_TILL_REDUCTION = effectType.timeTillReduction;
		this.ACTIVE_EFFECT_TIME = effectType.activeEffectTime;

		this.ATTRIBUTE_MODIFIER = attributeModifier;
		this.livingEffected = living;
	}

	@Override
	public void Tick(EntityLivingBase livingBase)
	{
		/* When buildup reaches 101 we can apply the effect, then immediately lower buildup so it remains at 100, but doesn't continue applying effect */
		if (this.effectBuildup >= 101)
		{
			this.isEffectApplied = true;
			AetherCore.LOGGER.info("Effect Applied : " + this.NAME + " to : " + this.livingEffected.getName());
			this.effectBuildup = 100;
		}

		this.applyEffect(livingBase, this.effectTimer);

		//manageEffect
		if (this.isEffectApplied)
		{
			if (this.effectTimer >= (this.ACTIVE_EFFECT_TIME * TICKS_PER_SECOND) * this.activeEffectTimeModifier)
			{
				this.resetEffect();
			}
		}

		this.reduceBuildup();

		if (this.effectBuildup <= 0)
		{
			if (!this.isEffectApplied)
			{
				this.effectTimer = 0;
			}
			this.effectBuildup = 0;
		}

		if (livingBase instanceof EntityPlayerMP && !livingBase.world.isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketStatusEffect(livingBase), (EntityPlayerMP) livingBase);
		}
	}

	@Override
	abstract public void applyEffect(EntityLivingBase livingBase, int timer);

	@Override
	abstract public void onEffectEnd();

	@Override
	public void addBuildup(int buildup, double additionalResistance)
	{
		if (!this.isEffectApplied)
		{
			this.effectBuildup = this.effectBuildup + MathHelper.ceil(buildup * (this.effectResistance + additionalResistance));

			if (this.effectBuildup >= 100)
			{
				this.effectBuildup = 101;	// buildup is set to 101 for activation.
			}

			if (this.effectBuildup < 0)
			{
				this.effectBuildup = 0;
			}

			this.effectTimer = 0;
		}
	}

	@Override
	public void setBuildup(int buildup)
	{
		this.effectBuildup = buildup;
	}

	@Override
	public void setApplied(boolean isApplied)
	{
		this.isEffectApplied = isApplied;
	}

	@Override
	public void reduceBuildup()
	{
		if (this.effectBuildup > 0)
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
	public void addResistance(double addResistance)
	{
		if (this.effectResistance + addResistance >= 2.0D)
		{
			this.effectResistance = 2.0;
			return;
		}

		if (this.effectResistance + addResistance <= 0.0D)
		{
			this.effectResistance = 0.0;
			return;
		}

		this.effectResistance = this.effectResistance + addResistance;
	}

	@Override
	public void setActiveEffectTimeModifier(double activeEffectTimeModifier)
	{
		this.activeEffectTimeModifier = activeEffectTimeModifier;
	}

	@Override
	public double getResistance()
	{
		return this.effectResistance;
	}

	@Override
	public void resetEffect()
	{
		this.onEffectEnd();

		this.isEffectApplied = false;
		this.effectBuildup = 0;
		this.effectTimer = 0;
		this.activeEffectTimeModifier = 1.0D;

		AetherCore.LOGGER.info("Effect Reset : " + this.NAME + " to : " + this.livingEffected.getName());
	}

	@Override
	public int getBuildup()
	{
		return this.effectBuildup;
	}

	@Override
	public int getTimer()
	{
		return this.getTimer();
	}

	@Override
	public boolean getIsEffectApplied()
	{
		return this.isEffectApplied;
	}

	@Override
	public effectTypes getEffectType()
	{
		return this.effectType;
	}

	@Override
	public String getEffectName()
	{
		return this.NAME;
	}

	@Override
	public AttributeModifier getAttributeModifier()
	{
		return this.ATTRIBUTE_MODIFIER;
	}

	@Override
	public abstract int getBuildupFromIntensity(EEffectIntensity intensity);

	@Override
	@SideOnly(Side.CLIENT)
	public abstract void addInformation(Collection<String> label);

	@Override
	public void write(NBTTagCompound compound)
	{
		compound.setInteger(this.NAME + ".effectBuildup", this.effectBuildup);
		compound.setBoolean(this.NAME + ".effectIsApplied", this.isEffectApplied);
		compound.setInteger(this.NAME + ".effectTimer", this.effectTimer);
		compound.setDouble(this.NAME + ".effectActiveTimeModifier", this.activeEffectTimeModifier);
	}

	@Override
	public void read(NBTTagCompound compound)
	{
		this.effectBuildup = compound.getInteger(this.NAME + ".effectBuildup");
		this.isEffectApplied = compound.getBoolean(this.NAME + ".effectIsApplied");
		this.effectTimer = compound.getInteger(this.NAME + ".effectTimer");
		this.activeEffectTimeModifier =  compound.getInteger(this.NAME + ".effectActiveTimeModifier");
	}
}
