package com.gildedgames.aether.common.entities.effects.abilities;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import com.gildedgames.aether.common.entities.effects.Ability;
import com.gildedgames.aether.common.entities.effects.AbilityRule;
import com.gildedgames.aether.common.entities.effects.EntityEffect;
import com.gildedgames.aether.common.entities.effects.EntityEffectBuilder;


/**
 * @author Brandon Pearce
 */
public class ExtraDamageAbility<S extends EntityLivingBase> implements Ability<S>
{
	
	private ExtraDamageAbility()
	{
		
	}
	
	@SafeVarargs
	public static <S extends EntityLivingBase> EntityEffect<S> build(Class<S> cls, float damage, AbilityRule<S>... rules)
	{
		EntityEffect<S> effect = new EntityEffectBuilder<S>().abilities(new ExtraDamageAbility<S>()).flush(rules);
		
		effect.getAttributes().setFloat("damage", damage);
		
		return effect;
	}
	
	@Override
	public String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return "ability.extraDamage.name";
	}

	@Override
	public String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		return new String[] { "ability.extraDamage.desc" };
	}
	
	@Override
	public void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes)
	{
		localizedDesc.set(0, String.format(localizedDesc.get(0), (int)attributes.getFloat("damage")));
	}
	
	@Override
	public void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes)
	{
		if (source.swingProgressInt == -1)
		{
			source.swingProgressInt = 0;

			EntityLivingBase entity = event.entityLiving;
			
			if (entity != null)
			{
				float result = Math.max(0, entity.getHealth() - (attributes.getInteger("modifier") * attributes.getFloat("damage")));
				
				entity.setHealth(result);
			}
		}
	}
	
	@Override
	public void initAttributes(S source, NBTTagCompound attributes) {}
	
	@Override
	public void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

	@Override
	public void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes) {}

}
