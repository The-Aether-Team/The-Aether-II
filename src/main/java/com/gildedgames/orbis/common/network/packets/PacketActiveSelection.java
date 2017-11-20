package com.gildedgames.orbis.common.network.packets;

import com.gildedgames.aether.api.io.NBTFunnel;
import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.aether.api.orbis_core.OrbisCore;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;
import com.gildedgames.orbis.common.player.godmode.selectors.IShapeSelector;
import com.gildedgames.orbis.common.world_objects.WorldShape;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketActiveSelection implements IMessage
{
	private IShape shape;

	private NBTFunnel funnel;

	public PacketActiveSelection()
	{

	}

	public PacketActiveSelection(final IShape shape)
	{
		this.shape = shape;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = ByteBufUtils.readTag(buf);

		this.funnel = OrbisCore.io().createFunnel(tag);
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		final NBTFunnel funnel = OrbisCore.io().createFunnel(tag);

		funnel.set("shape", this.shape);

		ByteBufUtils.writeTag(buf, tag);
	}

	public static class HandlerServer extends MessageHandlerServer<PacketActiveSelection, IMessage>
	{
		@Override
		public IMessage onMessage(final PacketActiveSelection message, final EntityPlayer player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			final IShape shape = message.funnel.get(player.world, "shape");

			final PlayerOrbisModule module = PlayerOrbisModule.get(player);
			final ISelectionInput selectionInput = module.selectionInputs().getCurrentSelectionInput();

			selectionInput.setActiveSelection(new WorldShape(shape, player.getEntityWorld()));

			IShapeSelector selector = module.powers().getCurrentPower().getShapeSelector();

			final ItemStack held = player.getHeldItemMainhand();

			if (held.getItem() instanceof IShapeSelector)
			{
				selector = (IShapeSelector) held.getItem();
			}

			selector.onSelect(module, shape, player.world);

			return null;
		}
	}
}
