package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IRegion;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.api.orbis_core.api.CreationData;
import com.gildedgames.aether.api.orbis_core.block.BlockDataContainer;
import com.gildedgames.aether.api.orbis_core.processing.DataPrimer;
import com.gildedgames.aether.api.orbis_core.util.RotationHelp;
import com.gildedgames.aether.api.util.BlockAccessExtendedWrapper;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCreateItemBlockDataContainer implements IMessage
{

	private BlockPos pos;

	private ItemStack stack;

	private NBTFunnel funnel;

	public PacketCreateItemBlockDataContainer()
	{

	}

	public PacketCreateItemBlockDataContainer(final ItemStack stack, final BlockPos pos)
	{
		this.stack = stack;
		this.pos = pos;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.funnel = OrbisCore.io().createFunnel(ByteBufUtils.readTag(buf));
		this.stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.setPos("p", this.pos);

		ByteBufUtils.writeTag(buf, tag);
		ByteBufUtils.writeItemStack(buf, this.stack);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketCreateItemBlockDataContainer, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketCreateItemBlockDataContainer message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			message.pos = message.funnel.getPos("p");

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);

			if (module.inDeveloperMode())
			{
				final BlockDataContainer container = ItemBlockDataContainer.getDataContainer(message.stack);

				final Rotation rotation = Rotation.NONE;

				final IRegion region = RotationHelp.regionFromCenter(message.pos, container, rotation);

				final DataPrimer primer = new DataPrimer(new BlockAccessExtendedWrapper(player.world));
				primer.create(container, new CreationData(player.world, player).set(region.getMin()).set(rotation).set(false));
			}

			return null;
		}
	}
}
