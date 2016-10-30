package com.gildedgames.aether.common.capabilities.entity.effects.rules;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectRule;
import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityPropertiesCapability;
import com.gildedgames.aether.common.capabilities.entity.properties.EntityProperties;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamagingElementRule implements EntityEffectRule
{

	private ElementalState elementalState;

	public DamagingElementRule(ElementalState elementalState)
	{
		this.elementalState = elementalState;
	}

	@Override
	public boolean isMet(Entity source)
	{
		return true;
	}

	@Override
	public boolean blockLivingAttack(Entity source, float amount, Entity target)
	{
		IEntityPropertiesCapability properties = EntityProperties.get(target);

		return !(properties != null && properties.getElementalState() == this.elementalState);
	}

	@Override
	public boolean blockLivingHurt(Entity source, LivingHurtEvent event)
	{
		IEntityPropertiesCapability properties = EntityProperties.get(event.getEntityLiving());

		return !(properties != null && properties.getElementalState() == this.elementalState);
	}

	@Override
	public String[] getUnlocalizedDesc()
	{
		return new String[] { TextFormatting.GRAY + "" + TextFormatting.ITALIC + "Against " + this.elementalState.getNameFormatting() + TextFormatting.ITALIC  + "" + I18n.format(this.elementalState.getUnlocalizedName()) };
	}

}
