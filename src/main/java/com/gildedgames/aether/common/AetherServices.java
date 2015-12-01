package com.gildedgames.aether.common;

import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.aether.common.player.PlayerAetherHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.PlayerHookPool;

import net.minecraftforge.fml.relauncher.Side;

public class AetherServices
{

	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private final Side side;

	public AetherServices(Side side)
	{
		this.side = side;

		this.playerHookPool = new PlayerHookPool<>(AetherCore.MOD_ID, new PlayerAetherHookFactory(), side);
	}

	public IPlayerHookPool<PlayerAether> getPool()
	{
		return this.playerHookPool;
	}

}
