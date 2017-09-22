package com.gildedgames.aether.common.entities.ai.swet;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.living.mobs.EntitySwet;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketLatchSwet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;

public class AILatchOn extends EntityAI<EntitySwet>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	public AILatchOn(final EntitySwet entity, final HoppingMoveHelper hoppingMoveHelper)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexBits(2);
	}

	@Override
	public void startExecuting()
	{

	}

	@Override
	public boolean shouldExecute()
	{
		return this.entity().getAttackTarget() instanceof EntityPlayer && this.entity().getAttackTarget() != null && this.entity()
				.canEntityBeSeen(this.entity().getAttackTarget())
				&& this.entity().getDistanceToEntity(this.entity().getAttackTarget()) <= 1.5D && EntitySwet
				.canLatch(this.entity(), (EntityPlayer) this.entity().getAttackTarget());
	}

	@Override
	public boolean continueExecuting()
	{
		return !this.entity().isDead && this.entity().getHealth() > 0 && this.shouldExecute();
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public void updateTask()
	{
		this.entity().faceEntity(this.entity().getAttackTarget(), 10.0F, 10.0F);

		if (this.entity().getAttackTarget() instanceof EntityPlayer)
		{
			final EntityPlayer player = (EntityPlayer) this.entity().getAttackTarget();

			this.entity().setSucking(0);

			NetworkingAether.sendPacketToPlayer(new PacketLatchSwet(this.entity().getType()), (EntityPlayerMP) player);
			PlayerAether.getPlayer(player).getSwetTracker().latchSwet(this.entity());

			this.entity().playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F,
					(this.entity().getRNG().nextFloat() - this.entity().getRNG().nextFloat()) * 0.2F + 1.0F);
		}
	}

}
