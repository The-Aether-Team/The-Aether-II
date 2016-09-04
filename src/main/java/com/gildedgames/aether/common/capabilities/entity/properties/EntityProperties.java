package com.gildedgames.aether.common.capabilities.entity.properties;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

import static net.minecraft.realms.Tezzelator.t;

public class EntityProperties implements IEntityPropertiesCapability
{

	private Entity entity;

	private List<ElementalDamageSource> damageSources = Lists.newArrayList();

	private ElementalState override;

	public EntityProperties(Entity entity)
	{
		this.entity = entity;
	}

	@Override
	public ElementalState getElementalState()
	{
		if (this.entity instanceof IEntityProperties)
		{
			IEntityProperties properties = (IEntityProperties)this.entity;

			return properties.getElementalState();
		}

		return ElementalState.BIOLOGICAL;
	}

	@Override
	public List<ElementalDamageSource> getElementalDamageSources()
	{
		return this.damageSources;
	}

	@Override
	public boolean hasElementalDamageSource(ElementalDamageSource source)
	{
		return this.getElementalDamageSources().contains(source);
	}

	@Override
	public void addElementalDamageSource(ElementalDamageSource source)
	{
		this.getElementalDamageSources().add(source);
	}

	@Override
	public void removeElementalDamageSource(ElementalDamageSource source)
	{
		this.getElementalDamageSources().remove(source);
	}

	@Override
	public void setElementalStateOverride(ElementalState override)
	{
		this.override = override;
	}

	@Override
	public ElementalState getElementalStateOverride()
	{
		return this.override;
	}

	public static IEntityPropertiesCapability get(Entity entity)
	{
		return entity.getCapability(AetherCapabilities.ENTITY_PROPERTIES, null);
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		Entity source = event.getSource().getSourceOfDamage();
		Entity victim = event.getEntityLiving();

		if (source == null)
		{
			return;
		}

		IEntityPropertiesCapability victimProperties = EntityProperties.get(victim);
		IEntityPropertiesCapability sourceProperties = EntityProperties.get(source);

		if (sourceProperties.getElementalStateOverride() != null)
		{
			event.setAmount(event.getAmount() * sourceProperties.getElementalStateOverride().getModifierAgainst(victimProperties.getElementalState()));

			return;
		}

		List<ElementalState> elements = Lists.newArrayList();
		double overallDamage = 0.0D;

		for (ElementalDamageSource damageSource : sourceProperties.getElementalDamageSources())
		{
			if (!elements.contains(damageSource.getElementalState()))
			{
				elements.add(damageSource.getElementalState());
			}

			overallDamage += damageSource.getDamage();
		}

		double minusDamageSources = event.getAmount() - overallDamage;

		double withModifiers = 0.0F;

		for (ElementalDamageSource damageSource : sourceProperties.getElementalDamageSources())
		{
			withModifiers += damageSource.getElementalState().getModifierAgainst(victimProperties.getElementalState()) * damageSource.getDamage();
		}

		double finalResult = minusDamageSources + withModifiers;

		event.setAmount((float) finalResult);

		World world = victim.getEntityWorld();

		if (world instanceof WorldServer)
		{
			WorldServer worldServer = (WorldServer)world;

			if (elements.contains(ElementalState.WATER))
			{
				for (int i = 0; i < 4; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.WATER_SPLASH, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D));
				}
			}

			if (elements.contains(ElementalState.FROST))
			{
				for (int i = 0; i < 4; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.BLOCK_DUST, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D), Block.getStateId(Blocks.ICE.getDefaultState()));
				}
			}

			if (elements.contains(ElementalState.EARTH))
			{
				for (int i = 0; i < 4; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.BLOCK_DUST, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D), Block.getStateId(Blocks.DIRT.getDefaultState()));
				}
			}

			if (elements.contains(ElementalState.AIR))
			{
				for (int i = 0; i < 3; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.CLOUD, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D));
				}
			}

			if (elements.contains(ElementalState.FIRE))
			{
				for (int i = 0; i < 3; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.FLAME, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D));
				}
			}

			if (elements.contains(ElementalState.BLIGHT))
			{
				for (int i = 0; i < 3; ++i)
				{
					double motionX = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionY = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();
					double motionZ = (world.rand.nextBoolean() ? 0.25D : -0.25D) * world.rand.nextFloat();

					worldServer.spawnParticle(EnumParticleTypes.SPELL_WITCH, victim.posX + motionX, victim.posY + (victim.height / 2) + motionY, victim.posZ + motionZ, 1, motionX, motionY, motionZ, (world.rand.nextBoolean() ? 0.05D : -0.05D));
				}
			}
		}
	}

	public static class Storage implements Capability.IStorage<IEntityPropertiesCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IEntityPropertiesCapability> capability, IEntityPropertiesCapability instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<IEntityPropertiesCapability> capability, IEntityPropertiesCapability instance, EnumFacing side, NBTBase nbt) { }
	}

}
