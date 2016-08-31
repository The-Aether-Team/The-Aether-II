package com.gildedgames.aether.common.entities.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ModifyDefenseEffect implements EntityEffectProcessor<ModifyDefenseEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(float defenseMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setFloat("defenseMod", defenseMod);
		}

		public float getDefense()
		{
			return this.getAttributes().getFloat("defenseMod");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getAttributes().getFloat("defenseMod"), this.getRules());
		}

	}

	public ModifyDefenseEffect()
	{

	}

	@Override
	public String getUnlocalizedName(Entity source, Instance instance)
	{
		return "ability.defenseMod.localizedName";
	}

	@Override
	public String[] getUnlocalizedDesc(Entity source, Instance instance)
	{
		return new String[] { "ability.defenseMod.desc" };
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		float defense = instance.getDefense();

		String prefix = defense > 0 ? (TextFormatting.BLUE + "+") : (TextFormatting.RED + "");

		String par = prefix + (defense == (int) Math.floor(defense) ? String.valueOf((int) Math.floor(defense)) : String.valueOf(defense));

		return new String[] { par };
	}

	@Override
	public void apply(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{

	}

	@Override
	public void cancel(Entity source, Instance instance, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}
	}

	@Override
	public void onKill(LivingDropsEvent event, Entity source, List<Instance> all)
	{

	}

	@Override
	public void onHurt(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase))
		{
			return;
		}

		float allDefense = 0.0F;

		for (Instance instance : all)
		{
			allDefense += instance.getDefense();
		}

		float defense = Math.min(event.getAmount(), allDefense * 2);

		defense = Math.max(1.0F, defense - 1.0F);

		EntityLivingBase living = (EntityLivingBase) source;

		living.setHealth(living.getHealth() + defense);
	}

	@Override
	public void onAttacked(LivingAttackEvent event, Entity source, List<Instance> all)
	{

	}

}
