package com.gildedgames.aether.common.entities.living.mounts;

import com.gildedgames.aether.api.entity.IMount;
import com.gildedgames.aether.api.entity.IMountProcessor;
import com.gildedgames.aether.common.entities.util.flying.EntityFlying;
import com.gildedgames.aether.common.entities.util.mounts.FlyingMount;
import com.gildedgames.aether.common.entities.util.mounts.IFlyingMountData;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Set;

public class EntityFlyingCow extends EntityFlyingAnimal
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(new Item[] {Items.WHEAT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry});

	public EntityFlyingCow(World world)
	{
		super(world);

		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
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
		return 0;
	}

	@Override
	protected Item getDropItem()
	{
		return null;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
	{
		EnumActionResult result = super.applyPlayerInteraction(player, vec, stack, hand);

		if (player.capabilities.isCreativeMode || result == EnumActionResult.SUCCESS)
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
				PlayerUtil.fillBucketInHand(player, stack, fillStack);
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

	@Override
	public double getMountedYOffset()
	{
		return 1.15D;
	}

	@Override
	public float maxAirborneTime()
	{
		return 5.0F;
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int looting)
	{
		super.dropFewItems(p_70628_1_, looting);

		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);
		int k;

		for (k = 0; k < j; ++k)
		{
			this.dropItem(Items.LEATHER, 1);
		}

		j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + looting);

		for (k = 0; k < j; ++k)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.COOKED_BEEF, 1);
			}
			else
			{
				this.dropItem(Items.BEEF, 1);
			}
		}
	}

	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack)
	{
		return stack != null && TEMPTATION_ITEMS.contains(stack.getItem());
	}

}
