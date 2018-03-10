package com.gildedgames.aether.common.entities.living.npc;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityNecromancer extends EntityNPC
{
	private BlockPos spawned;

	public EntityNecromancer(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 2.5F);

		this.rotationYaw = 0.3F;
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 50.0F, 1.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeEntityToNBT(final NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setTag("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(final NBTTagCompound compound)
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
		this.setHealth(this.getMaxHealth());
		this.isDead = false;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
		}

		super.onUpdate();
	}

	@Override
	public boolean processInteract(final EntityPlayer player, final EnumHand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote)
			{
				final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
				aePlayer.getDialogController().openScene(AetherCore.getResource("necromancer/start"));
			}
		}

		return true;
	}

}
