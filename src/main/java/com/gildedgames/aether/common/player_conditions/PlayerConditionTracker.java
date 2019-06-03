package com.gildedgames.aether.common.player_conditions;

import com.gildedgames.aether.api.player.IPlayerConditionModule;
import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.player.conditions.IPlayerConditionTracker;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionListener;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.google.common.collect.Sets;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.HashSet;

public class PlayerConditionTracker implements IPlayerConditionTracker, IPlayerConditionListener
{
	private final HashSet<IPlayerCondition> conditions = Sets.newHashSet();

	@Override
	public void trackConditions(final Collection<IPlayerCondition> conditions)
	{
		for (final IPlayerCondition condition : conditions)
		{
			if (!this.conditions.contains(condition))
			{
				condition.listen(this);
				this.conditions.add(condition);
				condition.onTracked();
			}
		}
	}

	@Override
	public void unload()
	{
		for (final IPlayerCondition condition : this.conditions)
		{
			condition.unlisten(this);
			condition.onUntracked();
		}

		this.conditions.clear();
	}

	@Override
	public void onTriggered(final IPlayerCondition condition, final EntityPlayer player)
	{
		final PlayerAether playerAether = PlayerAether.getPlayer(player);
		final IPlayerConditionModule module = playerAether.getPlayerConditionModule();

		module.flagCondition(condition.getUniqueIdentifier());
	}
}
