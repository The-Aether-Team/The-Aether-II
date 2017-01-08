package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.registry.content.DimensionsAether;
import com.gildedgames.aether.common.util.TickTimer;
import com.gildedgames.aether.common.world.dungeon.instance.DungeonInstance;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent;

public class DungeonModule extends PlayerAetherModule
{

	private TickTimer chatTimer = new TickTimer();

	public DungeonModule(PlayerAether playerAether)
	{
		super(playerAether);

		this.chatTimer.setTicksPassed(160);
	}

	public void onPlaceBlock(BlockEvent.PlaceEvent event)
	{
		if (!event.getPlayer().capabilities.isCreativeMode)
		{
			if (event.getPlayer().getEntityWorld().provider.getDimensionType() == DimensionsAether.SLIDER_LABYRINTH)
			{
				if (!(event.getPlacedBlock().getBlock() instanceof BlockTorch))
				{
					event.setCanceled(true);

					if (this.chatTimer.getTicksPassed() >= 160)
					{
						event.getPlayer().addChatComponentMessage(new TextComponentString("Hmm... Some strange force is preventing me from placing blocks."));

						this.chatTimer.reset();
					}
				}
			}
		}
	}

	@Override
	public void onUpdate()
	{
		this.chatTimer.tick();
	}

	@Override
	public void write(NBTTagCompound compound)
	{

	}

	@Override
	public void read(NBTTagCompound compound)
	{

	}

	public void onRespawn()
	{
		if (this.getEntity() instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) this.getEntity();

			IPlayerInstances playerInstances = AetherAPI.instances().getPlayer(player);
			NBT nbt = playerInstances.getInstance();

			if (nbt instanceof DungeonInstance)
			{
				DungeonInstance instance = (DungeonInstance) nbt;

				player.connection.setPlayerLocation(instance.getInsideEntrance().getX(), instance.getInsideEntrance().getY(), instance.getInsideEntrance().getZ(), 0, 0);
			}
		}
	}

}
