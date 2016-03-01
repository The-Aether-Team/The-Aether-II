package com.gildedgames.aether.common;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.aether.common.entities.effects.abilities.BreatheUnderwaterAbility;
import com.gildedgames.aether.common.entities.effects.abilities.FreezeBlocksAbility;
import com.gildedgames.aether.common.entities.effects.abilities.PauseHungerAbility;
import com.gildedgames.aether.common.entities.effects.abilities.player.DaggerfrostAbility;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;

public class AetherServices
{

	private IOManager io;

	private static final String MANAGER_NAME = "Aether";

	public AetherServices(Side side)
	{
		
	}

	private void startIOManager()
	{
		this.io = new IOManagerDefault(MANAGER_NAME);

		IORegistry registry = this.io.getRegistry();

		registry.registerClass(BreatheUnderwaterAbility.class, 0);
		registry.registerClass(FreezeBlocksAbility.class, 1);
		registry.registerClass(DaggerfrostAbility.class, 2);
		registry.registerClass(PauseHungerAbility.class, 3);
	}

	public IOManager getIOManager()
	{
		if (this.io == null)
		{
			this.startIOManager();
		}

		return this.io;
	}

}
