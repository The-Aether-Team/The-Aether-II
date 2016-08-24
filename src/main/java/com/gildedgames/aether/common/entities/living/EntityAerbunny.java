package com.gildedgames.aether.common.entities.living;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.SoundsAether;
import com.gildedgames.aether.common.blocks.BlocksAether;
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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static sun.audio.AudioPlayer.player;

public class EntityAerbunny extends EntityAetherAnimal
{
	@SideOnly(Side.CLIENT)
	private double prevMotionY;

	@SideOnly(Side.CLIENT)
	private int puffiness;

	public EntityAerbunny(World world)
	{
		super(world);

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 12.0F, 1.2F, 1.8F));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));

		this.jumpHelper = new AerbunnyJumpHelper(this);

		this.spawnableBlock = BlocksAether.aether_grass;

		this.setSize(0.65F, 0.65F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5);
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

		if (this.isRiding())
		{
			Entity entity = this.getRidingEntity();

			if (entity.isSneaking() && entity.onGround)
			{
				this.dismountRidingEntity();
			}

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
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand)
	{
		this.worldObj.playSound(player, player.getPosition(), SoundsAether.aerbunny_lift, SoundCategory.NEUTRAL, 1.0F, 0.8F + (this.rand.nextFloat() * 0.5F));

		if (!this.isRiding())
		{
			this.startRiding(player, true);
			AetherCore.PROXY.displayDismountMessage();

			return EnumActionResult.SUCCESS;
		}

		return super.applyPlayerInteraction(player, vec, stack, hand);
	}

	@Override
	public double getYOffset()
	{
		return this.getRidingEntity() != null ? 0.45D : 0.0D;
	}

	@Override
	public Item getDropItem()
	{
		return Items.STRING;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.aerbunny_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundsAether.aerbunny_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.aerbunny_death;
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new AerbunnyNavigator(this, worldIn);
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

	private class AerbunnyNavigator extends PathNavigateGround
	{
		public AerbunnyNavigator(EntityLiving entity, World world)
		{
			super(entity, world);
		}

		@Override
		protected boolean canNavigate()
		{
			return !this.theEntity.isRiding();
		}
	}

	@SideOnly(Side.CLIENT)
	public int getPuffiness()
	{
		return this.puffiness;
	}

	@Override
	public boolean canRiderInteract()
	{
		return true;
	}

}
