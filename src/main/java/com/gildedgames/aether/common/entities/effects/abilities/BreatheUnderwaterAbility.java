package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.EntityEffect;


/**
 * Sets player's "air" level to 300, which is one bubble worth of air. This means allows
 * them to breathe under water.
 * @author Brandon Pearce
 */
public class BreatheUnderwaterAbility<S extends EntityLivingBase> implements Ability<S>
{
	
	@Override
	public String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return "ability.breatheUnderwater.name";
	}

	@Override
	public String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.breatheUnderwater.desc" };
	}

	@Override
	public void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		source.setAir(300);
	}

	@Override
	public void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void initAttributes(S source, NBTTagCompound attributes)
	{
		
	}

	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		
	}

}
