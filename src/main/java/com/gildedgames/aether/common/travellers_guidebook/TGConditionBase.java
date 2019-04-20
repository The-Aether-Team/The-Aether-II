package com.gildedgames.aether.common.travellers_guidebook;

import com.gildedgames.aether.api.travellers_guidebook.ITGCondition;
import com.gildedgames.aether.api.travellers_guidebook.ITGConditionListener;
import net.minecraft.entity.player.EntityPlayer;

public abstract class TGConditionBase implements ITGCondition
{
	private ITGConditionListener listener;

	public TGConditionBase()
	{

	}

	protected void triggerCondition(final EntityPlayer player)
	{
		this.listener.onTriggered(this, player);
	}

	@Override
	public void subscribe(final ITGConditionListener listener)
	{
		this.listener = listener;
	}
}
