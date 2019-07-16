package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public class PacketProgressModule implements IMessage
{
	private PlayerProgressModule module;

	private CompoundNBT tag;

	public PacketProgressModule(PlayerProgressModule module)
	{
		this.module = module;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.tag = buf.readCompoundTag();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		CompoundNBT tag = new CompoundNBT();

		this.module.write(tag);

		buf.writeCompoundTag(tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketProgressModule>
	{
		@Override
		protected void onMessage(PacketProgressModule message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerProgressModule.class).read(message.tag);

		}
	}
}
