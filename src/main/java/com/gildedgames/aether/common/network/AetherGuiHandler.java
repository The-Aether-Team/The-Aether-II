package com.gildedgames.aether.common.network;

import com.gildedgames.aether.common.containers.ContainerSkyrootWorkbench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class AetherGuiHandler implements IGuiHandler
{
	public static final int SKYROOT_WORKBENCH_ID = 1;

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case SKYROOT_WORKBENCH_ID:
			return new ContainerSkyrootWorkbench(player.inventory, world, new BlockPos(x, y, z));
		default:
			return null;
		}
	}

	@Override
	public GuiContainer getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case SKYROOT_WORKBENCH_ID:
			return new GuiCrafting(player.inventory, world, new BlockPos(x, y, z));
		default:
			return null;
		}
	}
}
