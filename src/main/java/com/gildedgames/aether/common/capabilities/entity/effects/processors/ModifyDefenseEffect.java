package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ModifyDefenseEffect extends AbstractEffectProcessor<ModifyDefenseEffect.Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		private ElementalState elementalState;

		public Instance(double defenseMod, EntityEffectRule... rules)
		{
			this(ElementalState.BIOLOGICAL, defenseMod, rules);
		}

		public Instance(ElementalState elementalState, double defenseMod, EntityEffectRule... rules)
		{
			super(rules);

			this.getAttributes().setDouble("defenseMod", defenseMod);
			this.elementalState = elementalState;
		}

		public ElementalState getElementalState()
		{
			return this.elementalState;
		}

		public double getDefense()
		{
			return this.getAttributes().getDouble("defenseMod");
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.elementalState, this.getDefense(), this.getRules());
		}

	}

	public ModifyDefenseEffect()
	{
		super("ability.defenseMod.name", "ability.defenseMod.desc");
	}

	@Override
	public String[] getFormatParameters(Entity source, Instance instance)
	{
		double defense = instance.getDefense();

		String prefix = defense > 0 ? (instance.getElementalState().getNameFormatting() + "+") : (TextFormatting.RED + "");

		String par = prefix + (defense == (int) Math.floor(defense) ? String.valueOf((int) Math.floor(defense)) : String.valueOf(defense));

		String elementName = I18n.format(instance.getElementalState().getUnlocalizedName()) + " " + I18n.format("ability.defenseMod.desc2");

		if (instance.getElementalState() == ElementalState.BIOLOGICAL)
		{
			elementName = I18n.format("ability.defenseMod.desc1");
		}

		return new String[] { par, elementName };
	}

	@Override
	public void onHurt(LivingHurtEvent event, Entity source, List<Instance> all)
	{
		if (!(source instanceof EntityLivingBase) || event.getSource().getSourceOfDamage() == null)
		{
			return;
		}

		Entity attacker = event.getSource().getEntity();

		IEntityPropertiesCapability attackerProps = EntityProperties.get(attacker);

		float allDefense = 0.0F;

		for (Instance instance : all)
		{
			if (instance.getElementalState() == ElementalState.BIOLOGICAL)
			{
				allDefense += instance.getDefense();
				continue;
			}

			double overallDamage = 0.0D;

			for (ElementalDamageSource damageSource : attackerProps.getElementalDamageSources())
			{
				if (damageSource.getElementalState() == instance.getElementalState())
				{
					overallDamage += damageSource.getDamage();
				}
			}

			allDefense += Math.min(overallDamage, instance.getDefense());
		}

		float defense = Math.min(event.getAmount(), allDefense * 2);

		defense = Math.max(1.0F, defense - 1.0F);

		EntityLivingBase living = (EntityLivingBase) source;

		living.setHealth(living.getHealth() + defense);
	}

}
