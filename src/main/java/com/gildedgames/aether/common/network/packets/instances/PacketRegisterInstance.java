package com.gildedgames.aether.common.network.packets.instances;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.world.instances.IInstance;
import com.gildedgames.aether.api.world.instances.IPlayerInstances;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.api.util.mc.NBTHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketRegisterInstance implements IMessage
{

	private IInstance instance;

	public PacketRegisterInstance()
	{

	}

	public PacketRegisterInstance(final IInstance instance)
	{
		this.instance = instance;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);

		if (tag.getBoolean("isNull"))
		{
			this.instance = null;
		}
		else
		{
			this.instance = NBTHelper.read(tag.getCompoundTag("instances"));
		}
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		tag.setBoolean("isNull", false);

		if (this.instance == null)
		{
			tag.setBoolean("isNull", true);
		}
		else
		{
			tag.setTag("instances", NBTHelper.write(this.instance));
		}

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class Handler extends MessageHandlerClient<PacketRegisterInstance, PacketRegisterInstance>
	{
		@Override
		public PacketRegisterInstance onMessage(final PacketRegisterInstance message, final EntityPlayer player)
		{
			final IPlayerInstances instances = AetherAPI.instances().getPlayer(player);

			instances.setInstance(message.instance);

			return null;
		}
	}

}
