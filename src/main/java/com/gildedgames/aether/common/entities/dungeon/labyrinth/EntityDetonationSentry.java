package com.gildedgames.aether.common.entities.dungeon.labyrinth;

import com.gildedgames.aether.common.entities.ai.dungeon.labyrinth.AIDetonateClose;
import com.gildedgames.aether.common.entities.ai.hopping.*;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDetonationSentry extends EntityCreature
{

	public EntityDetonationSentry(World worldIn)
	{
		super(worldIn);

		HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this);

		this.moveHelper = hoppingMoveHelper;

		this.tasks.addTask(0, new AIDetonateClose(this, EntityPlayer.class, 0.5D));
		this.tasks.addTask(1, new AIHopFloat(this, hoppingMoveHelper));
		this.tasks.addTask(2, new AIHopFollowAttackTarget(this, hoppingMoveHelper));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		this.setSize(1.0F, 1.0F);
	}

	@Override
	protected void jump()
	{
		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16);
	}

}
