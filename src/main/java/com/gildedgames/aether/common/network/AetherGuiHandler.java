package com.gildedgames.aether.common.network;

import com.gildedgames.aether.client.gui.container.GuiAccessories;
import com.gildedgames.aether.common.containers.ContainerAccessories;
import com.gildedgames.aether.common.containers.ContainerSkyrootWorkbench;
import com.gildedgames.aether.common.player.PlayerAether;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler
{
	public static final int SKYROOT_WORKBENCH_ID = 1;

	public static final int INVENTORY_ACCESSORIES_ID = 2;

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case SKYROOT_WORKBENCH_ID:
			return new ContainerSkyrootWorkbench(player.inventory, world, new BlockPos(x, y, z));
		case INVENTORY_ACCESSORIES_ID:
			return new ContainerAccessories(PlayerAether.get(player));
		default:
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (id)
		{
		case SKYROOT_WORKBENCH_ID:
			return new GuiCrafting(player.inventory, world, new BlockPos(x, y, z));
		case INVENTORY_ACCESSORIES_ID:
			return new GuiAccessories(PlayerAether.get(player));
		default:
			return null;
		}
	}
}
