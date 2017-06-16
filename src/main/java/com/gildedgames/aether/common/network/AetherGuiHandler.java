package com.gildedgames.aether.common.network;

import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.client.gui.container.GuiIcestoneCooler;
import com.gildedgames.aether.client.gui.container.GuiIncubator;
import com.gildedgames.aether.client.gui.container.GuiSkyrootWorkbench;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.ContainerEquipment;
import com.gildedgames.aether.common.containers.ContainerSkyrootWorkbench;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler
{

	public static final int AETHER_WORKBENCH_ID = 1;

	public static final int INVENTORY_EQUIPMENT_ID = 2;

	public static final int FROSTPINE_COOLER_ID = 3;

	public static final int INCUBATOR_ID = 4;

	public static final int MASONRY_BENCH_ID = 5;

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		switch (id)
		{
			case AETHER_WORKBENCH_ID:
				return new ContainerSkyrootWorkbench(player.inventory, world, pos);
			case INVENTORY_EQUIPMENT_ID:
				return new ContainerEquipment(PlayerAether.getPlayer(player));
			case FROSTPINE_COOLER_ID:
				return new ContainerIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new ContainerIncubator(player.inventory, (IInventory) world.getTileEntity(pos));
//			case MASONRY_BENCH_ID:
//				return new ContainerMasonryBench(player, new BlockPos(x, y, z));
			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		switch (id)
		{
			case AETHER_WORKBENCH_ID:
				return new GuiSkyrootWorkbench(player.inventory, world, pos);
			case INVENTORY_EQUIPMENT_ID:
				return new GuiEquipment(PlayerAether.getPlayer(player));
			case FROSTPINE_COOLER_ID:
				return new GuiIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new GuiIncubator(player.inventory, (IInventory) world.getTileEntity(pos), pos);
//			case MASONRY_BENCH_ID:
//				return new GuiMasonryBench(player, new BlockPos(x, y, z));
			default:
				return null;
		}
	}
}
