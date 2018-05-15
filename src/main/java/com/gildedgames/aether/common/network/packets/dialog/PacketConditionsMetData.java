package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis_api.util.io.NBTFunnel;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Map;

public class PacketConditionsMetData implements IMessage
{
	private Map<String, Boolean> conditionsMet;

	private NBTFunnel funnel;

	public PacketConditionsMetData()
	{
	}

	public PacketConditionsMetData(Map<String, Boolean> conditionsMet)
	{
		this.conditionsMet = conditionsMet;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.funnel = new NBTFunnel(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		NBTTagCompound tag = new NBTTagCompound();
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setMap("c", this.conditionsMet, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketConditionsMetData, PacketConditionsMetData>
	{
		@Override
		public PacketConditionsMetData onMessage(final PacketConditionsMetData message, final EntityPlayer player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			Map<String, Boolean> conditionsMet = message.funnel.getMap("c", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);

			if (conditionsMet != null)
			{
				aePlayer.getDialogController().setConditionsMetData(conditionsMet);
			}

			return null;
		}
	}

}
