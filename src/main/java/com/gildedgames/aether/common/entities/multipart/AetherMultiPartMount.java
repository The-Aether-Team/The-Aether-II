package com.gildedgames.aether.common.entities.multipart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;

public class AetherMultiPartMount extends AetherMultiPartEntity
{

	public AetherMultiPartMount(IEntityMultiPart parent, String partName, float width, float height)
	{
		super(parent, partName, width, height);
	}

	public Entity getEntity()
	{
		return (Entity) this.parent;
	}
}

