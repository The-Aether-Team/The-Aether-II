package com.gildedgames.aether.common.entities.multipart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;

public class AetherMultiPartEntity extends MultiPartEntityPart
{

	public AetherMultiPartEntity(IEntityMultiPart parent, String partName, float width, float height)
	{
		super(parent, partName, width, height);
	}

	public void updateSize(float width, float height)
	{
		this.setSize(width, height);
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand)
	{
		if (this.parent instanceof AnimalEntity)
		{
			return ((AnimalEntity) this.parent).processInitialInteract(player, hand);
		}

		return false;
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target)
	{
		if (this.parent instanceof Entity)
		{
			return ((Entity) this.parent).getPickedResult(target);
		}

		return super.getPickedResult(target);
	}
}
