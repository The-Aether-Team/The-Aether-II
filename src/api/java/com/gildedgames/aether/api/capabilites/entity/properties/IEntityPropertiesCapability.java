package com.gildedgames.aether.api.capabilites.entity.properties;

import java.util.List;

public interface IEntityPropertiesCapability
{

	ElementalState getElementalState();

	List<ElementalDamageSource> getElementalDamageSources();

	boolean hasElementalDamageSource(ElementalDamageSource source);

	void addElementalDamageSource(ElementalDamageSource source);

	void removeElementalDamageSource(ElementalDamageSource source);

	void setAttackElementOverride(ElementalState override);

	ElementalState getAttackElementOverride();

}
