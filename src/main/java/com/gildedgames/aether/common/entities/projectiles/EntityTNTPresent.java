package com.gildedgames.aether.common.entities.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityTNTPresent extends Entity
{

	public int fuse;

	public EntityTNTPresent(World world)
	{
		super(world);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
	}

	public EntityTNTPresent(World world, double d, double d1, double d2)
	{
		this(world);
		this.setPosition(d, d1, d2);
		float f = (float) (Math.random() * 3.1415927410125732D * 2D);
		this.motionX = -MathHelper.sin((f * 3.141593F) / 180F) * 0.02F;
		this.motionY = 0.20000000298023224D;
		this.motionZ = -MathHelper.cos((f * 3.141593F) / 180F) * 0.02F;
		this.fuse = 80;
		this.prevPosX = d;
		this.prevPosY = d1;
		this.prevPosZ = d2;
	}

	@Override
	public double getYOffset()
	{
		return this.height / 2.0F;
	}

	@Override
	protected void registerData()
	{
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	@Override
	public void livingTick()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.039999999105930328D;
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.98000001907348633D;
		this.motionY *= 0.98000001907348633D;
		this.motionZ *= 0.98000001907348633D;

		if (this.onGround)
		{
			this.motionX *= 0.69999998807907104D;
			this.motionZ *= 0.69999998807907104D;
			this.motionY *= -0.5D;
		}

		if (this.fuse-- <= 0)
		{
			if (!this.world.isRemote)
			{
				this.remove();
				this.explode();
			}
		}
		else
		{
			this.world.spawnParticle(ParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode()
	{
		float f = 0.5F;
		this.world.createExplosion(null, this.posX, this.posY, this.posZ, f, false);
	}

	@Override
	protected void writeEntityToNBT(CompoundNBT nbttagcompound)
	{
		nbttagcompound.putByte("Fuse", (byte) this.fuse);
	}

	@Override
	protected void readEntityFromNBT(CompoundNBT nbttagcompound)
	{
		this.fuse = nbttagcompound.getByte("Fuse");
	}

}
