package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.AetherCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAerbunny extends EntityAetherAnimal
{
	@SideOnly(Side.CLIENT)
	private double prevMotionY;

	@SideOnly(Side.CLIENT)
	private int puffiness;

	public EntityAerbunny(World world)
	{
		super(world);

		((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, 1.2F, 1.8F));
		this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

		this.jumpHelper = new AerbunnyJumpHelper(this);

		this.setSize(0.45F, 0.45F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			if (this.puffiness > 0)
			{
				this.puffiness--;
			}

			if (this.prevMotionY <= 0 && this.motionY > 0)
			{
				BlockPos pos = this.getPosition();

				// Make sure we only spawn particles when it's jumping off a block
				if (this.worldObj.isBlockFullCube(pos.down()))
				{
					AetherCore.PROXY.spawnJumpParticles(this.worldObj, this.posX, pos.getY() + 0.1D, this.posZ, 0.6D, 6);
				}

				this.puffiness = 10;
			}

			this.prevMotionY = this.motionY;
		}

		if (this.ridingEntity != null)
		{
			Entity entity = this.ridingEntity;

			if (entity.motionY < 0)
			{
				entity.motionY *= entity.isSneaking() ? 0.9D : 0.7D;

				entity.fallDistance = 0;
			}

			this.setRotation(entity.rotationYaw, entity.rotationPitch);
		}

		if (this.motionY < -0.1D)
		{
			this.motionY = -0.1D;
		}

		this.fallDistance = 0.0F;
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
		{
			this.worldObj.playSoundAtEntity(this, AetherCore.getResourcePath("aemob.aerbunny.lift"), 1.0F, 0.8F + (this.rand.nextFloat() * 0.5F));

			if (this.ridingEntity != null)
			{
				if (this.ridingEntity == player)
				{
					this.mountEntity(null);

					return true;
				}
			}
			else
			{
				this.mountEntity(player);

				return true;
			}
		}

		return super.interact(player);
	}

	@Override
	public double getYOffset()
	{
		return this.ridingEntity != null ? 0.45D : 0.0D;
	}

	@Override
	protected String getHurtSound()
	{
		return "aemob.aerbunny.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "aemob.aerbunny.die";
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new EntityAerbunny(this.worldObj);
	}

	private class AerbunnyJumpHelper extends EntityJumpHelper
	{
		private EntityLiving entity;

		public AerbunnyJumpHelper(EntityAerbunny entity)
		{
			super(entity);

			this.entity = entity;
		}

		public void doJump()
		{
			this.entity.setJumping(true);
		}
	}

	@SideOnly(Side.CLIENT)
	public int getPuffiness()
	{
		return this.puffiness;
	}
}
