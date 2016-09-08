package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.util.io_manager.io.NBT;
import com.gildedgames.util.modules.instances.*;
import net.minecraft.entity.player.EntityPlayerMP;

import static sun.audio.AudioPlayer.player;

public class DungeonModule extends PlayerAetherModule
{

	public DungeonModule(IPlayerAetherCapability playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onRespawn()
	{
		if (this.getPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)this.getPlayer();

			IPlayerInstances playerInstances = InstanceModule.INSTANCE.getPlayer(player);
			NBT nbt = playerInstances.getInstance();

			if (nbt instanceof DungeonInstance)
			{
				DungeonInstance instance = (DungeonInstance)nbt;

				player.connection.setPlayerLocation(instance.getInsideEntrance().getX(), instance.getInsideEntrance().getY(), instance.getInsideEntrance().getZ(), 0, 0);
			}
		}
	}

}
