package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSetBlockDataContainerInHand implements IMessage
{

	private ItemStack stack;

	private IShape shape;

	private NBTFunnel funnel;

	public PacketSetBlockDataContainerInHand()
	{

	}

	public PacketSetBlockDataContainerInHand(final ItemStack stack, final IShape shape)
	{
		this.stack = stack;
		this.shape = shape;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.stack = ByteBufUtils.readItemStack(buf);

		final NBTTagCompound tag = ByteBufUtils.readTag(buf);
		this.funnel = OrbisCore.io().createFunnel(tag);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, this.stack);

		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.set("shape", this.shape);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketSetBlockDataContainerInHand, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketSetBlockDataContainerInHand message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.isCreative())
			{
				message.shape = message.funnel.get(player.world, "shape");

				player.inventory.setInventorySlotContents(player.inventory.currentItem, message.stack);
				final BlockDataContainer container = BlockDataContainer.fromShape(player.world, message.shape);

				ItemBlockDataContainer.setDataContainer(player, message.stack, container);
			}

			return null;
		}
	}
}
