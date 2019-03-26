package com.gildedgames.aether.common.entities.util.multipart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
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
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
	{
		if (this.parent instanceof EntityAnimal)
		{
			return ((EntityAnimal) this.parent).processInitialInteract(player, hand);
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
