package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.entities.util.EntityBodyHelperNoRotation;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityTivalier extends EntityCharacter
{
	private BlockPos spawned;

	public EntityTivalier(EntityType<? extends EntityCharacter> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		return null;
	}

	@Override
	protected BodyController createBodyController()
	{
		return new EntityBodyHelperNoRotation(this);
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10.0F));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		super.writeAdditional(nbt);

		nbt.put("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		super.readAdditional(nbt);

		this.spawned = NBTHelper.readBlockPos(nbt.getCompound("spawned"));

		if (this.spawned != null)
		{
			this.setHomePosAndDistance(this.spawned, 3);
		}
	}

	@Override
	public void registerData()
	{
		super.registerData();
	}

	@Override
	protected void setRotation(final float yaw, final float pitch)
	{

	}

	@Override
	public void tick()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.tick();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final PlayerEntity player, final Hand hand)
	{
		return super.processInteract(player, hand);
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final BlockState blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
