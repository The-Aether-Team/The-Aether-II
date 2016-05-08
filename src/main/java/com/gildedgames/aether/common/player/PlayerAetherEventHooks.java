package com.gildedgames.aether.common.player;

import com.gildedgames.aether.common.world.chunk.AetherPlaceFlagChunkHook;
import com.gildedgames.util.modules.chunk.ChunkModule;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerAetherEventHooks
{
	@SubscribeEvent
	public void onPlaceBlockEvent(BlockEvent.PlaceEvent event)
	{
		AetherPlaceFlagChunkHook data = ChunkModule.api().getHook(event.world, event.pos, AetherPlaceFlagChunkHook.class);

		int x = event.pos.getX(), y = event.pos.getY(), z = event.pos.getZ();

		if (data != null)
		{
			data.setExtendedBlockState(x, y, z, data.getExtendedBlockState(x, y, z).withProperty(AetherPlaceFlagChunkHook.PROPERTY_BLOCK_PLACED, true));
		}
		else
		{
			/*
			 * TODO: FIX THIS SHIT FUCK
			 */
			//System.out.println("Chunk hook is null, something is going wrong!");
		}
	}
}
