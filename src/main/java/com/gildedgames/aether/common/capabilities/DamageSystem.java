package com.gildedgames.aether.common.capabilities;

import com.gildedgames.aether.api.entity.damage.DamageSystemTables;
import com.gildedgames.aether.api.entity.damage.DamageTypeAttributes;
import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.projectiles.EntityBolt;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.items.weapons.swords.ItemZaniteSword;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
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

				processTotalDamage(event, immediateSource, baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());

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
				basePierceDamageLevel = entitySource.getEntityAttribute(DamageTypeAttributes.PIERCE_DAMAGE_LEVEL).getAttributeValue();

				processTotalDamage(event, entitySource, baseSlashDamageLevel, basePierceDamageLevel, baseImpactDamageLevel, event.getEntityLiving());
			}
		}
	}

	private static void processTotalDamage(LivingHurtEvent event, Entity entitySource, double slashDamageLevel, double pierceDamageLevel,
			double impactDamageLevel, EntityLivingBase receiving)
	{
		if (entitySource instanceof EntityPlayer)
		{
			if (PlayerAether.getPlayer((EntityPlayer) entitySource).getCooldownTracker() < 1.0f)
			{
				event.setAmount(0);
				return;
			}
		}

		double slashDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.SLASH_DEFENSE_LEVEL).getAttributeValue();
		double impactDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.IMPACT_DEFENSE_LEVEL).getAttributeValue();
		double pierceDefenseLevel = receiving.getEntityAttribute(DamageTypeAttributes.PIERCE_DEFENSE_LEVEL).getAttributeValue();

		double bonusSlashDamage = 0.0;
		double bonusImpactDamage = 0.0;
		double bonusPierceDamage = 0.0;

		if (entitySource instanceof EntityLivingBase)
		{
			Item item = ((EntityLivingBase) entitySource).getHeldItemMainhand().getItem();
			if (item instanceof ItemZaniteSword)
			{
				bonusSlashDamage = (int) ((ItemZaniteSword) item).bonusDamage;
			}
		}

		if (entitySource instanceof EntityBolt)
		{
			bonusPierceDamage = (int) ((EntityBolt) entitySource).bonusDamage;
		}

		double resultSlashDamageLevel = Math.max(slashDamageLevel > 0 ? 1 : 0, (slashDamageLevel + bonusSlashDamage) - slashDefenseLevel);
		double resultImpactDamageLevel = Math.max(impactDamageLevel > 0 ? 1 : 0, (impactDamageLevel + bonusImpactDamage) - impactDefenseLevel);
		double resultPierceDamageLevel = Math.max(pierceDamageLevel > 0 ? 1 : 0, (pierceDamageLevel + bonusPierceDamage) - pierceDefenseLevel);

		double slashDamage = DamageSystemTables.getValueFromLevelRange(resultSlashDamageLevel);
		double impactDamage = DamageSystemTables.getValueFromLevelRange(resultImpactDamageLevel);
		double pierceDamage = DamageSystemTables.getValueFromLevelRange(resultPierceDamageLevel);

		double trueSlashDamage = slashDamage / 2;
		double trueImpactDamage = impactDamage / 2;
		double truePierceDamage = pierceDamage / 2;

		float totalDamage = (float) (trueSlashDamage + trueImpactDamage + truePierceDamage);

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
