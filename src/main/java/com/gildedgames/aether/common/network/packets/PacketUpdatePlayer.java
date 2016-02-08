package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.player.PlayerAether;
import com.gildedgames.util.core.io.ByteBufHelper;
import com.gildedgames.util.core.io.CustomPacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketUpdatePlayer extends CustomPacket<PacketUpdatePlayer>
{
	private ItemStack[] accessories;

	public PacketUpdatePlayer() { }

	public PacketUpdatePlayer(PlayerAether aePlayer)
	{
		this.accessories = aePlayer.getInventoryAccessories().getInventory();
	}

	@Override
	public void handleClientSide(PacketUpdatePlayer message, EntityPlayer player)
	{
		PlayerAether aePlayer = PlayerAether.get(player);

		if (aePlayer == null)
		{
			aePlayer = new PlayerAether();

			player.registerExtendedProperties(AetherCore.MOD_ID, aePlayer);
		}

		for (int i = 0; i < message.accessories.length; i++)
		{
			aePlayer.getInventoryAccessories().setInventorySlotContents(i, message.accessories[i]);
		}
	}

	@Override
	public void handleServerSide(PacketUpdatePlayer message, EntityPlayer player)
	{
		// TODO
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.accessories = ByteBufHelper.readItemStacks(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufHelper.writeItemStacks(buf, this.accessories);
	}
}
