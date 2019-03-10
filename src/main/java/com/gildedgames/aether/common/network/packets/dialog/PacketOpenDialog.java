package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.Map;

public class PacketOpenDialog implements IMessage
{
	private ResourceLocation name;

	private String startingNodeId;

	private Map<String, Boolean> conditionsMet;

	private NBTFunnel funnel;

	public PacketOpenDialog()
	{
	}

	public PacketOpenDialog(final ResourceLocation res, String startingNodeId, Map<String, Boolean> conditionsMet)
	{
		this.name = res;
		this.startingNodeId = startingNodeId;
		this.conditionsMet = conditionsMet;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.name = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
		this.startingNodeId = ByteBufUtils.readUTF8String(buf);
		this.funnel = new NBTFunnel(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.name.toString());
		ByteBufUtils.writeUTF8String(buf, this.startingNodeId);

		NBTTagCompound tag = new NBTTagCompound();
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setMap("c", this.conditionsMet, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOpenDialog, PacketOpenDialog>
	{
		@Override
		public PacketOpenDialog onMessage(final PacketOpenDialog message, final EntityPlayer player)
		{
			Map<String, Boolean> conditionsMet = message.funnel.getMap("c", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);

			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			aePlayer.getDialogController().setConditionsMetData(conditionsMet);
			aePlayer.getDialogController().openScene(message.name, message.startingNodeId);

			return null;
		}
	}
}
