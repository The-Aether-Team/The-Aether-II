package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityFlyingCow extends EntityFlyingAnimal
{
	public EntityFlyingCow(World world)
	{
		super(world);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));

		this.setSize(0.9F, 1.3F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected int getItemQuantityDropped()
	{
		return this.rand.nextInt(3);
	}

	@Override
	protected Item getDropItem()
	{
		return this.isBurning() ? Items.COOKED_BEEF : Items.BEEF;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
	{
		EnumActionResult result = super.applyPlayerInteraction(player, vec, stack, hand);

		if (result == EnumActionResult.SUCCESS)
		{
			return result;
		}

		if (!this.isChild() && stack != null)
		{
			ItemStack fillStack = null;

			if (stack.getItem() == Items.BUCKET)
			{
				fillStack = new ItemStack(Items.MILK_BUCKET);
			}
			else if (stack.getItem() == ItemsAether.skyroot_bucket)
			{
				fillStack = new ItemStack(ItemsAether.skyroot_milk_bucket);
			}

			if (fillStack != null)
			{
				PlayerUtil.fillBucketInHand(player, fillStack);
			}

			return EnumActionResult.SUCCESS;
		}

		return result;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new EntityFlyingCow(this.worldObj);
	}
}
