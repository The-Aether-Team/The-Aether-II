package com.gildedgames.orbis.client.gui;

import com.gildedgames.aether.api.orbis.IWorldObject;
import com.gildedgames.aether.api.orbis.shapes.IShape;
import com.gildedgames.aether.common.capabilities.world.WorldObjectManager;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.orbis.client.gui.data.DropdownElement;
import com.gildedgames.orbis.client.gui.util.GuiDropdownList;
import com.gildedgames.orbis.common.block.BlockDataContainer;
import com.gildedgames.orbis.common.block.BlockFilter;
import com.gildedgames.orbis.common.items.ItemBlockDataContainer;
import com.gildedgames.orbis.common.items.ItemsOrbis;
import com.gildedgames.orbis.common.network.packets.PacketFilterShape;
import com.gildedgames.orbis.common.network.packets.PacketSetItemStackInHand;
import com.gildedgames.orbis.common.network.packets.PacketWorldObjectRemove;
import com.gildedgames.orbis.common.util.BlockFilterHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

public class GuiRightClickElements
{
	public static DropdownElement remove(final IWorldObject region)
	{
		return new DropdownElement(new TextComponentString("Remove"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				final WorldObjectManager manager = WorldObjectManager.get(player.world);
				NetworkingAether.sendPacketToServer(new PacketWorldObjectRemove(region.getWorld(), manager.getGroup(0), region));
			}
		};
	}

	public static DropdownElement copy(final IShape region)
	{
		return new DropdownElement(new TextComponentString("Copy"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				final BlockDataContainer container = BlockDataContainer.fromShape(player.world, region);
				final ItemStack item = new ItemStack(ItemsOrbis.blockdata);
				ItemBlockDataContainer.setDatacontainer(item, container);

				final Minecraft mc = Minecraft.getMinecraft();

				NetworkingAether.sendPacketToServer(new PacketSetItemStackInHand(item));
				mc.player.inventory.setInventorySlotContents(mc.player.inventory.currentItem, item);
			}
		};
	}

	public static DropdownElement fillWithVoid(final IShape shape)
	{
		return new DropdownElement(new TextComponentString("Fill With Void"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				final BlockFilter filter = new BlockFilter(BlockFilterHelper.getNewVoidLayer());

				NetworkingAether.sendPacketToServer(new PacketFilterShape(shape, filter));
			}
		};
	}

	public static DropdownElement delete(final IShape shape)
	{
		return new DropdownElement(new TextComponentString("Delete"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
				// TODO: DESIGN DECISION: It deletes according to your current main hand. Might be confusing.
				final BlockFilter filter = new BlockFilter(BlockFilterHelper.getNewDeleteLayer(player.getHeldItemMainhand()));

				NetworkingAether.sendPacketToServer(new PacketFilterShape(shape, filter));
			}
		};
	}

	public static DropdownElement close()
	{
		return new DropdownElement(new TextComponentString("Close"))
		{
			@Override
			public void onClick(final GuiDropdownList list, final EntityPlayer player)
			{
			}
		};
	}

}
