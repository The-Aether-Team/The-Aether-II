package com.gildedgames.aether.api.player.conditions.types;

import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;

public interface IPlayerConditionEntity extends IPlayerCondition
{
	ResourceLocation getEntityId();

	EntityEntry getEntityEntry();
}
