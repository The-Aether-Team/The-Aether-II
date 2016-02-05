package com.gildedgames.aether.common;

import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.aether.common.items.accessories.BreatheInWaterEffect;
import com.gildedgames.aether.common.items.accessories.DaggerfrostEffect;
import com.gildedgames.aether.common.items.accessories.FreezeBlocksEffect;
import com.gildedgames.aether.common.items.accessories.PauseHungerEffect;
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

		registry.registerClass(BreatheInWaterEffect.class, 0);
		registry.registerClass(FreezeBlocksEffect.class, 1);
		registry.registerClass(DaggerfrostEffect.class, 2);
		registry.registerClass(PauseHungerEffect.class, 3);
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
