package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.EntityEffect;

public class RegenerateHealthAbility<S extends EntityLivingBase> implements Ability<S>
{

	@Override
	public String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return "ability.regenerateHealth.name";
	}

	@Override
	public String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.regenerateHealth.desc" };
	}
	
	@Override
	public void initAttributes(S source, NBTTagCompound attributes)
	{
		attributes.setInteger("minimumTime", 160);
	}

	@Override
	public void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		
	}
	
	@Override
	public void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		/*EntityEffects<S> effects = EntityEffects.get(source);

		effects.ticksSinceAttacked++;

		int inbetween = 20 - (attributes.getInteger("modifier") * 4);

		if (aePlayer.ticksSinceAttacked > attributes.getInteger("minimumTime") && aePlayer.ticksSinceAttacked % inbetween == 0)
		{
			source.heal(0.5F);
		}*/
	}

	@Override
	public void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}
	
	@Override
	public void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes) {}
	
}
