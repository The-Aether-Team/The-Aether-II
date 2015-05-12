package com.gildedgames.aether.player;

import com.gildedgames.util.player.common.IPlayerHookFactory;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerProfile;

public class PlayerAetherHookFactory implements IPlayerHookFactory<PlayerAether>
{
	@Override
	public PlayerAether create(IPlayerProfile profile, IPlayerHookPool<PlayerAether> pool)
	{
		return new PlayerAether(profile, pool);
	}

}
