package com.gildedgames.aether.common.entities.living.companions;

import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class EntityPinkBabySwet extends EntityCompanion
{

	public float squishAmount;

	public float squishFactor;

	public float prevSquishFactor;

	private boolean wasOnGround;

	public EntityPinkBabySwet(World worldIn)
	{
		super(worldIn);

		HoppingMoveHelper hoppingMoveHelper = new HoppingMoveHelper(this, SoundEvents.ENTITY_SMALL_SLIME_JUMP);

		this.moveHelper = hoppingMoveHelper;

		this.setSize(0.75F, 0.75F);
	}

	@Override
	public void onUpdate()
	{
		if (this.getOwner() != null)
		{
			this.faceEntity(this.getOwner(), 10.0F, 10.0F);
		}

		((HoppingMoveHelper) this.moveHelper).setDirection(this.rotationYaw);

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;

		super.onUpdate();

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

		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	@Override
	public void tickEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void addEffects(PlayerAether aePlayer)
	{

	}

	@Override
	public void removeEffects(PlayerAether aePlayer)
	{

	}

}
