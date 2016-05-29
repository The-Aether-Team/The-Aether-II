package com.gildedgames.aether.common.entities.effects.processors;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;

import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectRule;
import com.gildedgames.aether.common.entities.effects.AbstractEffectProcessor;
import com.gildedgames.aether.common.entities.effects.processors.FrozenInSchematicEffect.Instance;

public class FrozenInSchematicEffect extends AbstractEffectProcessor<Instance>
{

	public static class Instance extends EntityEffectInstance
	{

		public Instance(EntityEffectRule... rules)
		{
			super(rules);
		}

		@Override
		public EntityEffectInstance cloneInstance()
		{
			return new Instance(this.getRules());
		}

	}

	public FrozenInSchematicEffect()
	{
		super("ability.frozenInSchematic.name", "ability.frozenInSchematic.desc");
	}

	@Override
	public void tick(Entity source, List<Instance> all)
	{
		source.ticksExisted = source.ticksExisted;
		
		source.velocityChanged = false;
		
		source.posX = source.prevPosX;
		source.posY = source.prevPosY;
		source.posZ = source.prevPosZ;
		
		source.motionX = 0;
		source.motionY = 0;
		source.motionZ = 0;
		
		source.rotationPitch = source.prevRotationPitch;
		source.rotationYaw = source.prevRotationYaw;
		
		source.setSilent(true);
		source.setInWeb();
		source.setOutsideBorder(true);
		
		source.width = 0;
		source.height = 0;
		
		source.extinguish();
		source.setAir(0);
		
		source.fireResistance = 0;
	}

}