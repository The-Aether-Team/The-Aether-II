package com.gildedgames.aether.common.entities.ai.swet;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerSwetTrackerModule;
import com.gildedgames.aether.common.entities.ai.EntityAI;
import com.gildedgames.aether.common.entities.ai.hopping.HoppingMoveHelper;
import com.gildedgames.aether.common.entities.monsters.EntitySwet;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketLatchSwet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;

import java.util.EnumSet;

public class AILatchOn extends EntityAI<EntitySwet>
{

	private final HoppingMoveHelper hoppingMoveHelper;

	public AILatchOn(final EntitySwet entity, final HoppingMoveHelper hoppingMoveHelper)
	{
		super(entity);

		this.hoppingMoveHelper = hoppingMoveHelper;

		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	@Override
	public void startExecuting()
	{

	}

	@Override
	public boolean shouldExecute()
	{
		LivingEntity target = this.entity().getAttackTarget();

		if (target instanceof PlayerEntity)
		{
			EntitySwet self = this.entity();

			if (self.canEntityBeSeen(target) && self.getDistance(target) <= 1.5D)
			{
				return EntitySwet.canLatch(self, (PlayerEntity) target);
			}
		}

		return false;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.entity().isAlive() && this.entity().getHealth() > 0 && this.shouldExecute();
	}

	@Override
	public boolean isPreemptible()
	{
		return false;
	}

	@Override
	public void tick()
	{
		LivingEntity target = this.entity().getAttackTarget();

		if (target instanceof PlayerEntity)
		{
			final PlayerEntity player = (PlayerEntity) target;

			EntitySwet swet = this.entity();
			swet.faceEntity(target, 10.0F, 10.0F);
			swet.setSucking(0);

			NetworkingAether.sendPacketToWatching(new PacketLatchSwet(swet.getSwetType(), player.getEntityId()), player, true);
			PlayerAether.getPlayer(player).getModule(PlayerSwetTrackerModule.class).latchSwet(this.entity());

			swet.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F,
					(swet.getRNG().nextFloat() - swet.getRNG().nextFloat()) * 0.2F + 1.0F);
		}
	}

}
