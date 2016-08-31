package com.gildedgames.aether.common.entities;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;

import java.util.List;

public interface EntityPropertiesBase
{

	class EntityProperties implements EntityPropertiesBase
	{

		public static class RegistrationEntry
		{

			private Class<? extends Entity> entityClass;

			private ElementalState[] states;

			public RegistrationEntry(Class<? extends Entity> entityClass, ElementalState... states)
			{
				this.entityClass = entityClass;
				this.states = states;
			}

			public Class<? extends Entity> getEntityClass()
			{
				return this.entityClass;
			}

			public ElementalState[] getElementalStates()
			{
				return this.states;
			}

		}

		private static List<RegistrationEntry> registeredEntries = Lists.newArrayList();

		private ElementalState[] states;

		public EntityProperties(ElementalState... states)
		{
			this.states = states;
		}

		@Override
		public ElementalState[] getElementalStates()
		{
			return this.states;
		}

		public static void register(Class<? extends Entity> entityClass, ElementalState... states)
		{
			EntityProperties.registeredEntries.add(new RegistrationEntry(entityClass, states));
		}

	}

	ElementalState[] getElementalStates();

}
