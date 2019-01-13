package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.damage_system.DamageSystemTables;
import com.gildedgames.aether.api.damage_system.DamageTypeAttributes;
import com.gildedgames.aether.api.damage_system.IDamageLevelsHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DamageSystem
{
	public static boolean usesDamageSystem(EntityLivingBase living)
	{
		return living.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue() > 0;
	}

	public static boolean hasDefenseLevels(EntityLivingBase living)
	{
		return living.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() > 0;
	}

	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) event.getObject();

			living.getAttributeMap().registerAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL);
			living.getAttributeMap().registerAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL);
			living.getAttributeMap().registerAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL);

			living.getAttributeMap().registerAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL);
			living.getAttributeMap().registerAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL);
			living.getAttributeMap().registerAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL);
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		if (event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			Entity immediateSource = event.getSource().getImmediateSource();

			double baseSlashDamageLevel;
			double baseImpactDamageLevel;
			double basePierceDamageLevel;

			if (immediateSource instanceof IDamageLevelsHolder)
			{
				IDamageLevelsHolder levelsHolder = (IDamageLevelsHolder) immediateSource;

				baseSlashDamageLevel = levelsHolder.getSlashDamageLevel();
				baseImpactDamageLevel = levelsHolder.getImpactDamageLevel();
				basePierceDamageLevel = levelsHolder.getPierceDamageLevel();

				float totalDamage = calculateTotalDamage(baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());

				event.setAmount(totalDamage);

				return;
			}
			else if (immediateSource != null)
			{
				if (!(immediateSource instanceof EntityLivingBase) || !usesDamageSystem((EntityLivingBase) immediateSource))
				{
					if (hasDefenseLevels(event.getEntityLiving()))
					{
						event.setAmount(1);
					}

					return;
				}

				EntityLivingBase entitySource = (EntityLivingBase) event.getSource().getImmediateSource();

				baseSlashDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue();
				baseImpactDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue();
				basePierceDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();

				float totalDamage = calculateTotalDamage(baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());

				event.setAmount(totalDamage);
			}
		}
	}

	private static float calculateTotalDamage(double slashDamageLevel, double pierceDamageLevel, double impactDamageLevel, EntityLivingBase receiving)
	{
		double slashDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
		double impactDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();
		double pierceDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();

		double resultSlashDamageLevel = Math.max(slashDamageLevel > 0 ? 1 : 0, slashDamageLevel - slashDefenseLevel);
		double resultImpactDamageLevel = Math.max(impactDamageLevel > 0 ? 1 : 0, impactDamageLevel - impactDefenseLevel);
		double resultPierceDamageLevel = Math.max(pierceDamageLevel > 0 ? 1 : 0, pierceDamageLevel - pierceDefenseLevel);

		double slashDamage = DamageSystemTables.getValueFromLevelRange(resultSlashDamageLevel);
		double impactDamage = DamageSystemTables.getValueFromLevelRange(resultImpactDamageLevel);
		double pierceDamage = DamageSystemTables.getValueFromLevelRange(resultPierceDamageLevel);

		return (float) (slashDamage + impactDamage + pierceDamage);
	}
}
