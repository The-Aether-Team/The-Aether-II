package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketCurrencyModule implements IMessage
{
	private PlayerCurrencyModule module;

	private CompoundNBT tag;

	public PacketCurrencyModule(PlayerCurrencyModule module)
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

	public static class HandlerClient extends MessageHandlerClient<PacketCurrencyModule>
	{
		@Override
		protected void onMessage(PacketCurrencyModule message, ClientPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			playerAether.getModule(PlayerCurrencyModule.class).read(message.tag);
		}
	}
}
