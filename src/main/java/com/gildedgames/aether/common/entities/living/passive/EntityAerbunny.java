package com.gildedgames.aether.common.entities.living.passive;

import java.util.Set;

import javax.annotation.Nullable;

import com.gildedgames.aether.api.capabilites.entity.properties.ElementalState;
import com.gildedgames.aether.api.capabilites.entity.properties.IEntityProperties;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.LootTablesAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAerbunny extends EntityAetherAnimal implements IEntityProperties
{

	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.CARROT, Items.POTATO, Items.BEETROOT, ItemsAether.blueberries, ItemsAether.orange, ItemsAether.enchanted_blueberry, ItemsAether.enchanted_wyndberry, ItemsAether.wyndberry);

	@SideOnly(Side.CLIENT)
	private double prevMotionY;

	@SideOnly(Side.CLIENT)
	private int puffiness;

	public EntityAerbunny(final World world)
	{
		super(world);

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
	}

	@Override
	public void onUpdate()
	{
		if (this.motionX != 0 || this.motionZ != 0)
		{
			this.setJumping(true);
		}

		super.onUpdate();

		if (this.worldObj.isRemote)
		{
			if (this.puffiness > 0)
			{
				this.puffiness--;
			}

			if (this.prevMotionY <= 0 && this.motionY > 0)
			{
				final BlockPos pos = this.getPosition();

				// Make sure we only spawn particles when it's jumping off a block
				if (this.worldObj.isBlockFullCube(pos.down()))
				{
					AetherCore.PROXY.spawnJumpParticles(this.worldObj, this.posX, pos.getY(), this.posZ, 0.6D, 6);
				}

				this.puffiness = 10;
			}

			this.prevMotionY = this.motionY;
		}

		if (this.isRiding())
		{
			final Entity entity = this.getRidingEntity();

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
	public boolean processInteract(final EntityPlayer player, final EnumHand hand, @Nullable final ItemStack stack)
	{
		if (!super.processInteract(player, hand, stack) && !this.isBreedingItem(stack))
		{
			if (!this.isRiding() && player.getPassengers().size() <= 0)
			{
				this.worldObj.playSound(player, player.getPosition(), SoundsAether.aerbunny_lift, SoundCategory.NEUTRAL, 1.0F, 0.8F + (this.rand.nextFloat() * 0.5F));

				this.startRiding(player, true);
				AetherCore.PROXY.displayDismountMessage(player);

				return true;
			}
			else
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public double getYOffset()
	{
		return this.getRidingEntity() != null ? 0.45D : 0.0D;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTablesAether.ENTITY_AERBUNNY;
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
	protected PathNavigate getNewNavigator(final World worldIn)
	{
		return new AerbunnyNavigator(this, worldIn);
	}

	@Override
	public EntityAgeable createChild(final EntityAgeable ageable)
	{
		return new EntityAerbunny(this.worldObj);
	}

	private class AerbunnyJumpHelper extends EntityJumpHelper
	{
		private final EntityLiving entity;

		public AerbunnyJumpHelper(final EntityAerbunny entity)
		{
			super(entity);

			this.entity = entity;
		}

		public void doJump()
		{
			this.entity.setJumping(true);

			if (this.entity.motionX == 0 && this.entity.motionZ == 0)
			{
				this.isJumping = false;
				this.entity.setJumping(false);
			}
		}
	}

	private class AerbunnyNavigator extends PathNavigateGround
	{
		public AerbunnyNavigator(final EntityLiving entity, final World world)
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

	@Override
	public ElementalState getElementalState()
	{
		return ElementalState.AIR;
	}

	@Override
	public ElementalState getAttackElement()
	{
		return ElementalState.AIR;
	}

	@Override
	public boolean isBreedingItem(@Nullable final ItemStack stack)
	{
		final boolean flag = stack != null && TEMPTATION_ITEMS.contains(stack.getItem());

		return flag;
	}

	@Override
	public boolean isEntityInsideOpaqueBlock()
	{
		return !this.isRiding() && super.isEntityInsideOpaqueBlock();
	}

}
