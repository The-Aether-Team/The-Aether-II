package com.gildedgames.aether.common.entities.companions;

import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class EntityPinkBabySwet extends EntityCompanion
{

	public float squishAmount;

	public float squishFactor;

	public float prevSquishFactor;

	private boolean wasOnGround;

	public EntityPinkBabySwet(EntityType<? extends CreatureEntity> type, World worldIn)
	{
		super(type, worldIn);

	}

	@Override
	public void registerGoals()
	{
		this.moveController = new HoppingMoveHelper(this, () -> SoundEvents.ENTITY_SLIME_JUMP_SMALL);
	}

	@Override
	public void tick()
	{
		if (this.getOwner() != null)
		{
			this.faceEntity(this.getOwner(), 10.0F, 10.0F);
		}

		((HoppingMoveHelper) this.moveController).setDirection(this.rotationYaw);

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;

		super.tick();

		if (this.onGround && !this.wasOnGround)
		{
			this.squishAmount = -0.5F;
		}
		else if (!this.onGround && this.wasOnGround)
		{
			this.squishAmount = 1.0F;
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	protected void alterSquishAmount()
	{
		this.squishAmount *= 0.6F;
	}

	@Override
	protected void jump()
	{
		if (this.getMoveHelper().getSpeed() <= 0)
		{
			return;
		}

		this.setMotion(this.getMotion().getX(), 0.42D, this.getMotion().getZ());

		this.isAirBorne = true;
	}

	@Override
	public int getVerticalFaceSpeed()
	{
		return 0;
	}

}
