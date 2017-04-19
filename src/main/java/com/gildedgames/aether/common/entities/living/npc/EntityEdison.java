package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.api.capabilites.entity.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.util.io.NBTHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityEdison extends EntityNPC
{
	private BlockPos spawned;

	public EntityEdison(World worldIn)
	{
		super(worldIn);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAIWander(this, 0.3, 10));
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(2, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.spawned = NBTHelper.readBlockPos(compound.getCompoundTag("spawned"));

		if (this.spawned != null)
		{
			this.setHomePosAndDistance(this.spawned, 3);
		}
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
	}

	@Override
	public void onUpdate()
	{
		if (this.spawned == null)
		{
			this.spawned = this.getPosition();

			this.setHomePosAndDistance(this.spawned, 16);
		}

		// Attempt once every 5 seconds
		if (this.ticksExisted % 100 == 0 && !this.isWithinHomeDistanceCurrentPosition())
		{
			this.attemptTeleport(this.getHomePosition().getX(), this.getHomePosition().getY(), this.getHomePosition().getZ());
		}

		super.onUpdate();
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote)
			{
				IPlayerAether aePlayer = PlayerAether.getPlayer(player);
				aePlayer.getDialogController().openScene(AetherCore.getResource("edison/outpost_greet"));
			}
		}

		return true;
	}

}
