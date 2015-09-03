package com.gildedgames.aether.common;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.PlayerAetherHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

public class AetherServices
{
	
	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private final Side side;

	public AetherServices(Side side)
	{
		this.side = side;

		this.playerHookPool = new PlayerHookPool<PlayerAether>(AetherCore.MOD_ID, new PlayerAetherHookFactory(), side);
	}

	public IPlayerHookPool<PlayerAether> getPool()
	{
		return this.playerHookPool;
	}
	
}
