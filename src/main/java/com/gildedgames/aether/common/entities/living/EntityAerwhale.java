package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityFlying
{

	public EntityAerwhale(World world)
	{
		super(world);

		this.setSize(5.0F, 1.0F);
	}

}
