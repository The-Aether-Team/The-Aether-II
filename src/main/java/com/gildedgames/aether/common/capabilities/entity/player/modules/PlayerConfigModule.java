package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;

public class PlayerConfigModule extends PlayerAetherModule
{
	private boolean skipIntro;

	public PlayerConfigModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	public boolean skipIntro()
	{
		return this.skipIntro;
	}

	public void setSkipIntro(boolean flag)
	{
		this.skipIntro = flag;
	}
}
