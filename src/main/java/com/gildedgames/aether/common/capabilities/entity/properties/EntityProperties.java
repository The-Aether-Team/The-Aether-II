package com.gildedgames.aether.common.capabilities.entity.properties;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalDamageSource;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EntityProperties implements IEntityPropertiesCapability
{

	private Entity entity;

	private List<ElementalDamageSource> damageSources = Lists.newArrayList();

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

	public static IEntityPropertiesCapability get(Entity entity)
	{
		return entity.getCapability(AetherCapabilities.ENTITY_PROPERTIES, null);
	}

	@SubscribeEvent
	public static void onLivingEntityHurt(LivingHurtEvent event)
	{
		Entity source = event.getSource().getSourceOfDamage();
		Entity victim = event.getEntityLiving();

		IEntityPropertiesCapability victimProperties = EntityProperties.get(victim);
		IEntityPropertiesCapability sourceProperties = EntityProperties.get(source);

		double overallDamage = 0.0D;

		for (ElementalDamageSource damageSource : sourceProperties.getElementalDamageSources())
		{
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
