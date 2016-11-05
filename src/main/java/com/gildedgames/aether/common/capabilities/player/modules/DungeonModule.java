package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.registry.minecraft.DimensionsAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import com.gildedgames.aether.api.util.NBT;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;

public class DungeonModule extends PlayerAetherModule
{

	private TickTimer chatTimer = new TickTimer();

	public DungeonModule(PlayerAetherImpl playerAether)
	{
		super(playerAether);

		this.chatTimer.setTicksPassed(160);
	}

	@Override
	public void onPlaceBlock(BlockEvent.PlaceEvent event)
	{
		if (this.getPlayer() instanceof EntityPlayerMP && !this.getPlayer().capabilities.isCreativeMode)
		{
			EntityPlayerMP player = (EntityPlayerMP) this.getPlayer();

			if (player.getServerWorld().provider.getDimensionType() == DimensionsAether.SLIDER_LABYRINTH)
			{
				if (!(event.getPlacedBlock().getBlock() instanceof BlockTorch))
				{
					event.setCanceled(true);

					if (this.chatTimer.getTicksPassed() >= 160)
					{
						player.addChatComponentMessage(new TextComponentString("Hmm... Some strange force is preventing me from placing blocks."));

						this.chatTimer.reset();
					}
				}
			}
		}
	}

	@Override
	public void onPlaceBlockMulti(BlockEvent.MultiPlaceEvent event)
	{

	}

	@Override
	public void onUpdate(LivingEvent.LivingUpdateEvent event)
	{
		this.chatTimer.tick();
	}

	@Override
	public void onRespawn()
	{
		if (this.getPlayer() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)this.getPlayer();

			IPlayerInstances playerInstances = AetherAPI.instances().getPlayer(player);
			NBT nbt = playerInstances.getInstance();

			if (nbt instanceof DungeonInstance)
			{
				DungeonInstance instance = (DungeonInstance)nbt;

				player.connection.setPlayerLocation(instance.getInsideEntrance().getX(), instance.getInsideEntrance().getY(), instance.getInsideEntrance().getZ(), 0, 0);
			}
		}
	}

}
