package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.processors.ModifyDamageEffect.Instance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Brandon Pearce
 */
public class ModifyDamageEffect extends AbstractEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(float damage, EntityEffectRule... rules)
		{
			this(damage, damage, false, rules);
		}

		public Instance(int min, int max, EntityEffectRule... rules)
		{
			this(min, max, false, rules);
		}

		public Instance(float min, float max, boolean floatRanges, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("min", min);
			this.getAttributes().setFloat("max", max);

			this.getAttributes().setBoolean("floatRanges", floatRanges);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("min"), this.getAttributes().getFloat("max"), this.getAttributes().getBoolean("floatRanges"), this.getRules());
		}

	}

	public ModifyDamageEffect()
	{
		super("ability.extraDamage.localizedName", "ability.extraDamage.desc");
	}

	private String displayValue(float value)
	{
		return value == (int) Math.floor(value) ? String.valueOf((int) Math.floor(value)) : String.valueOf(value);
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float min = instance.getAttributes().getFloat("min");
		float max = instance.getAttributes().getFloat("max");

		String prefix = min >= 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par;

		if (min == max)
		{
			par = prefix + this.displayValue(min);
		}
		else
		{
			par = prefix + this.displayValue(min) + "-" + this.displayValue(max);
		}

		return new String[] { par };
	}

	@Override
	public void onAttack(LivingAttackEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		EntityLivingBase living = (EntityLivingBase) source;

		if (living.swingProgressInt == 0)
		{
			Entity entity = event.getEntityLiving();

			if (entity != null)
			{
				float allDamage = 0.0F;

				for (Instance instance : all)
				{
					float min = instance.getAttributes().getFloat("min");
					float max = instance.getAttributes().getFloat("max");

					float rangeResult = 0.0F;

					if (instance.getAttributes().getBoolean("floatRanges"))
					{
						rangeResult = min + (max - min) * living.getRNG().nextFloat();
					}
					else
					{
						rangeResult = ThreadLocalRandom.current().nextInt((int) min, (int) max + 1);
					}

					allDamage += rangeResult;
				}

				float result = Math.max(0, event.getEntityLiving().getHealth() - (allDamage * 2));

				/*if (allDamage < 0)
				{
					System.out.println(event.getEntityLiving().getHealth());
					System.out.println(event.getEntityLiving().getHealth() + (event.getAmount() - 1.0F));

					result = Math.max(0, event.getEntityLiving().gethea + (event.getAmount() - 1.0F));
				}*/

				event.getEntityLiving().setHealth(result);
			}
		}
	}

}
