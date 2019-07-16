package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCampfiresModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import com.gildedgames.orbis.lib.util.mc.BlockPosDimension;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

import java.util.Set;

public class PacketCampfires implements IMessage
{

	private Set<BlockPosDimension> campfires;

	private NBTFunnel funnel;

	public PacketCampfires(Set<BlockPosDimension> campfires)
	{
		this.campfires = campfires;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.funnel = new NBTFunnel(buf.readCompoundTag());
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		NBTFunnel funnel = new NBTFunnel(new CompoundNBT());
		funnel.setSet("c", this.campfires);

		buf.writeCompoundTag(funnel.getTag());
	}

	public static class HandlerClient extends MessageHandlerClient<PacketCampfires>
	{
		@Override
		protected void onMessage(PacketCampfires message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			Set<BlockPosDimension> campfires = message.funnel.getSet("c");

			final PlayerAether aePlayer = PlayerAether.getPlayer(player);
			aePlayer.getModule(PlayerCampfiresModule.class).setCampfiresActivated(campfires);
		}
	}
}
