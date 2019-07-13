package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.damage.DamageSystemTables;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DamageSystem
{
	public static boolean usesDamageSystem(LivingEntity living)
	{
		return living.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue() > 0;
	}

	public static boolean hasDefenseLevels(LivingEntity living)
	{
		return living.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue() > 0
				|| living.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue() > 0;
	}

	@SubscribeEvent
	public static void onEntityLoad(final AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof LivingEntity)
		{
			LivingEntity living = (LivingEntity) event.getObject();

			living.getAttributes().registerAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL);
			living.getAttributes().registerAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL);
			living.getAttributes().registerAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL);

			living.getAttributes().registerAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL);
			living.getAttributes().registerAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL);
			living.getAttributes().registerAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL);
		}
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		if (event.getSource().getTrueSource() instanceof LivingEntity)
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

				processTotalDamage(event, immediateSource, baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());

				return;
			}
			else if (immediateSource != null)
			{
				if (!(immediateSource instanceof LivingEntity) || !usesDamageSystem((LivingEntity) immediateSource))
				{
					if (hasDefenseLevels(event.getEntityLiving()))
					{
						event.setAmount(1);
					}

					return;
				}

				LivingEntity entitySource = (LivingEntity) event.getSource().getImmediateSource();

				baseSlashDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.SLASH_DAMAGE_LEVEL).getAttributeValue();
				baseImpactDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.IMPACT_DAMAGE_LEVEL).getAttributeValue();
				basePierceDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();

				processTotalDamage(event, entitySource, baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());
			}
		}
	}

	private static void processTotalDamage(LivingHurtEvent event, Entity entitySource, double slashDamageLevel, double pierceDamageLevel,
			double impactDamageLevel, LivingEntity receiving)
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

		float totalDamage = (float) (slashDamage + impactDamage + pierceDamage);

		event.setAmount(totalDamage);

		double offsetX = (double) (-MathHelper.sin(entitySource.rotationYaw * 0.017453292F));
		double offsetZ = (double) MathHelper.cos(entitySource.rotationYaw * 0.017453292F);

		double x = entitySource.posX + offsetX;
		double y = entitySource.posY + (double) entitySource.height * 0.5D;
		double z = entitySource.posZ + offsetZ;

		if (slashDamageLevel > 0)
		{
			NetworkingAether.sendPacketToDimension(new PacketParticles(ParticlesAether.SLASH, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}

		if (pierceDamageLevel > 0)
		{
			NetworkingAether
					.sendPacketToDimension(new PacketParticles(ParticlesAether.PIERCE, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}

		if (impactDamageLevel > 0)
		{
			NetworkingAether
					.sendPacketToDimension(new PacketParticles(ParticlesAether.IMPACT, x, y, z, offsetX, 0.0D, offsetZ), entitySource.dimension);
		}
	}
}
