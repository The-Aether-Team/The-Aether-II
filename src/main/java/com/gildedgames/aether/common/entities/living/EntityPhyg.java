package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.entities.living.mounts.EntityFlyingAnimal;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPhyg extends EntityFlyingAnimal
{
	public EntityPhyg(World world)
	{
		super(world);

		((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		this.tasks.addTask(2, /*this.aiControlledByPlayer = */new EntityAIControlledByPlayer(this, 0.3F));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.setSize(0.9F, 1.3F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.pig.say";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound("mob.pig.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return this.isBurning() ? Items.cooked_porkchop : Items.porkchop;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new EntityPhyg(this.worldObj);
	}
}
