package com.gildedgames.aether.common.network;

import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.client.gui.container.GuiEquipment;
import com.gildedgames.aether.client.gui.container.GuiIcestoneCooler;
import com.gildedgames.aether.client.gui.container.GuiIncubator;
import com.gildedgames.aether.client.gui.container.simple_crafting.GuiMasonryBench;
import com.gildedgames.aether.client.gui.dialog.GuiDialogViewer;
import com.gildedgames.aether.client.gui.dialog.GuiShop;
import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.client.gui.misc.GuiAetherLoading;
import com.gildedgames.aether.client.gui.misc.GuiAetherTeleporterNotice;
import com.gildedgames.aether.client.gui.misc.GuiPatronRewards;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.*;
import com.gildedgames.aether.common.containers.tiles.ContainerIcestoneCooler;
import com.gildedgames.aether.common.containers.tiles.ContainerIncubator;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.dialog.DialogUtil;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiViewer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler
{

	public static final int CUSTOM_WORKBENCH_ID = 1;

	public static final int INVENTORY_EQUIPMENT_ID = 2;

	public static final int FROSTPINE_COOLER_ID = 3;

	public static final int INCUBATOR_ID = 4;

	public static final int MASONRY_BENCH_ID = 5;

	public static final int AETHER_LOADING_ID = 6;

	public static final int PATRON_REWARDS_ID = 7;

	public static final int TELEPORTER_NOTICE_ID = 8;

	public static final int DIALOG_SHOP_ID = 9;

	public static final int DIALOG_VIEWER_ID = 10;

	public static final int TRADE_ID = 11;

	@Override
	public Container getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final BlockPos pos = new BlockPos(x, y, z);
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		switch (id)
		{
			case CUSTOM_WORKBENCH_ID:
				return new ContainerCustomWorkbench(player.inventory, world, pos);
			case INVENTORY_EQUIPMENT_ID:
				return new ContainerEquipment(playerAether);
			case FROSTPINE_COOLER_ID:
				return new ContainerIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new ContainerIncubator(player.inventory, (IInventory) world.getTileEntity(pos));
			case MASONRY_BENCH_ID:
				return new ContainerMasonryBench(player, new BlockPos(x, y, z));
			case AETHER_LOADING_ID:
				return new ContainerLoadingScreen();
			case DIALOG_SHOP_ID:
			{
				if (playerAether.getDialogController().getTalkingNPC() == null)
				{
					return null;
				}

				IShopInstanceGroup group = playerAether.getDialogController().getTalkingNPC().getShopInstanceGroup();

				if (group == null)
				{
					return null;
				}

				int shopIndex = pos.getX();

				IShopInstance shopInstance = group.getShopInstance(shopIndex);

				if (shopInstance == null)
				{
					return null;
				}

				return new ContainerShop(player.inventory, shopInstance);
			}
			case DIALOG_VIEWER_ID:
				return new ContainerDialogController(player);
			case TRADE_ID:
				return new ContainerTrade(player.inventory);
			default:
				return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		final BlockPos pos = new BlockPos(x, y, z);
		PlayerAether playerAether = PlayerAether.getPlayer(player);

		switch (id)
		{
			case CUSTOM_WORKBENCH_ID:
				return new GuiCrafting(player.inventory, world, pos);
			case INVENTORY_EQUIPMENT_ID:
				return new GuiEquipment(PlayerAether.getPlayer(player));
			case FROSTPINE_COOLER_ID:
				return new GuiIcestoneCooler(player.inventory, (IInventory) world.getTileEntity(pos));
			case INCUBATOR_ID:
				return new GuiIncubator(player.inventory, (IInventory) world.getTileEntity(pos), pos);
			case MASONRY_BENCH_ID:
				return new GuiMasonryBench(player, new BlockPos(x, y, z));
			case AETHER_LOADING_ID:
				return new GuiAetherLoading();
			case PATRON_REWARDS_ID:
				return new GuiPatronRewards();
			case TELEPORTER_NOTICE_ID:
				return new GuiAetherTeleporterNotice();
			case DIALOG_SHOP_ID:
			{
				if (playerAether.getDialogController().getTalkingNPC() == null)
				{
					return null;
				}

				IDialogSlide slide = DialogUtil.getSlide(playerAether.getDialogController());

				IShopInstanceGroup group = playerAether.getDialogController().getTalkingNPC().getShopInstanceGroup();

				if (group == null)
				{
					return null;
				}

				int shopIndex = pos.getX();

				IShopInstance shopInstance = group.getShopInstance(shopIndex);

				if (shopInstance == null || slide == null)
				{
					return null;
				}

				GuiViewer prevViewer = null;

				if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiViewer)
				{
					prevViewer = (GuiViewer) FMLClientHandler.instance().getClient().currentScreen;
				}

				GuiDialogViewer.preventDialogControllerClose = true;

				if (slide.getRenderer().isPresent())
				{
					IDialogSlideRenderer renderer = DialogUtil.getRenderer(slide);

					return new GuiShop(prevViewer, player, slide, renderer, shopInstance, shopIndex);
				}
				else
				{
					return new GuiShop(prevViewer, player, slide, null, shopInstance, shopIndex);
				}
			}
			case DIALOG_VIEWER_ID:
				return new GuiDialogViewer(player, playerAether.getDialogController(), playerAether.getDialogController().getCurrentSceneInstance());
			case TRADE_ID:
				GuiViewer prevViewerA = null;

				if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiViewer)
				{
					prevViewerA = (GuiViewer) FMLClientHandler.instance().getClient().currentScreen;
				}

				return new GuiTrade(prevViewerA, player);
			default:
				return null;
		}
	}
}
