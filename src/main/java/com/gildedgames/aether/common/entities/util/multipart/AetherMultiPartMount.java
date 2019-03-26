package com.gildedgames.aether.common.entities.util.multipart;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

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

