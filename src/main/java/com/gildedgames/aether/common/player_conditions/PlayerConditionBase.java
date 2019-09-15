package com.gildedgames.aether.common.player_conditions;

import com.gildedgames.aether.api.player.conditions.IPlayerCondition;
import com.gildedgames.aether.api.player.conditions.types.IPlayerConditionListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class PlayerConditionBase implements IPlayerCondition
{
	private IPlayerConditionListener listener;

	public PlayerConditionBase()
	{

	}

	protected void triggerCondition(final EntityPlayer player)
	{
		this.listener.onTriggered(this, player);
	}

	@Override
	public ResourceLocation getUniqueIdentifier()
	{
		return null;
	}

	@Override
	public void listen(final IPlayerConditionListener listener)
	{
		this.listener = listener;
	}

	@Override
	public void unlisten(final IPlayerConditionListener listener)
	{
		this.listener = null;
	}
}
