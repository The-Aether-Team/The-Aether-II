package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.entities.util.EntityBodyHelperNoRotation;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class EntityTivalier extends EntityCharacter
{
	private BlockPos spawned;

	public EntityTivalier(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.6F, 1.6F);
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		return null;
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
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
	public void writeEntityToNBT(final CompoundNBT compound)
	{
		super.writeEntityToNBT(compound);

		compound.put("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(final CompoundNBT compound)
	{
		super.readEntityFromNBT(compound);

		this.spawned = NBTHelper.readBlockPos(compound.getCompound("spawned"));

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
	@OnlyIn(Dist.CLIENT)
	public void turn(final float yaw, final float pitch)
	{

	}

	@Override
	public void livingTick()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.livingTick();

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
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
