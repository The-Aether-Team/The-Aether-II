package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.aether.api.capabilites.instances.Instance;
import com.gildedgames.aether.common.util.io.NBTHelper;
import com.gildedgames.aether.api.capabilites.instances.IPlayerInstances;
import com.gildedgames.aether.common.capabilities.instances.InstanceRegistryImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRegisterInstance implements IMessage
{

	private Instance instance;

	public PacketRegisterInstance()
	{

	}

	public PacketRegisterInstance(Instance instance)
	{
		this.instance = instance;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		NBTTagCompound tag = ByteBufUtils.readTag(buf);

		if (tag.getBoolean("isNull"))
		{
			this.instance = null;
		}
		else
		{
			this.instance = NBTHelper.fullyDeserialize("instances", tag, this.instance);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setBoolean("isNull", false);

		if (this.instance == null)
		{
			tag.setBoolean("isNull", true);
		}
		else
		{
			NBTHelper.fullySerialize("instances", this.instance, tag);
		}

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler extends MessageHandlerClient<PacketRegisterInstance, PacketRegisterInstance>
	{
		@Override
		public PacketRegisterInstance onMessage(PacketRegisterInstance message, EntityPlayer player)
		{
			IPlayerInstances instances = AetherAPI.instances().getPlayer(player);

			instances.setInstance(message.instance);

			return null;
		}
	}

}
