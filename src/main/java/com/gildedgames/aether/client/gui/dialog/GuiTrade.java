package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiViewer;
import com.gildedgames.orbis_api.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiTrade extends GuiViewer
{

	private static final ResourceLocation INVENTORY = AetherCore.getResource("textures/gui/trade/inventory.png");

	private static final ResourceLocation TRADE_INVENTORY = AetherCore.getResource("textures/gui/trade/trade.png");

	private static final ResourceLocation TRADE_VIEW = AetherCore.getResource("textures/gui/trade/view.png");

	private static final ResourceLocation LEFT_ARROW = AetherCore.getResource("textures/gui/trade/left_button.png");

	private static final ResourceLocation LEFT_ARROW_HOVER = AetherCore.getResource("textures/gui/trade/left_button_hover.png");

	private static final ResourceLocation RIGHT_ARROW = AetherCore.getResource("textures/gui/trade/right_button.png");

	private static final ResourceLocation RIGHT_ARROW_HOVER = AetherCore.getResource("textures/gui/trade/right_button_hover.png");

	private GuiAbstractButton leftArrow, rightArrow;

	private final EntityPlayer player;

	private final PlayerAether playerAether;

	private double transferCoins;

	private GuiExpandableCoinTab coinTab, tradeTab, viewTab;

	private boolean pressLongEnough, rightArrowHeld, leftArrowHeld;

	private long lastBuyCountChangeTime;

	public GuiTrade(GuiViewer prevViewer, EntityPlayer player)
	{
		super(new GuiElement(Dim2D.flush(), false), prevViewer, new ContainerTrade(player.inventory));

		this.player = player;
		this.playerAether = PlayerAether.getPlayer(player);
	}

	@Override
	public void initContainerSize()
	{
		Pos2D center = InputHelper.getCenter().clone().addX(15).flush();

		this.guiLeft = (int) center.x() - 189 - 7;
		this.guiTop = this.height - 198 - 14;

		this.xSize = 385;
		this.ySize = 180;
	}

	@Override
	public void build(IGuiContext context)
	{
		this.getViewing().dim().mod().width(300).height(300).flush();

		Pos2D center = InputHelper.getCenter().clone().flush();

		GuiTexture inventory = new GuiTexture(Dim2D.build().center(true).width(176).height(110).pos(center).y(this.height).addX(-98).addY(-157F).flush(),
				INVENTORY);

		GuiTexture tradeInventory = new GuiTexture(Dim2D.build().center(true).width(86).height(110).pos(center).y(this.height).addX(54).addY(-157F).flush(),
				TRADE_INVENTORY);

		GuiTexture tradeView = new GuiTexture(Dim2D.build().center(true).width(86).height(110).pos(center).y(this.height).addX(147).addY(-157F).flush(),
				TRADE_VIEW);

		this.leftArrow = new GuiAbstractButton(Dim2D.build().width(9).height(15).pos(center).y(this.height).addX(-9).addY(-205).flush(),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW));

		this.rightArrow = new GuiAbstractButton(Dim2D.build().width(9).height(15).pos(center).y(this.height).addX(1).addY(-205).flush(),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW));

		this.coinTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).pos(center).addY(-33).flush(), true, true, false, -15);
		this.tradeTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).pos(center).addY(-33).flush(), true, false, false, 17);
		this.viewTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).pos(center).flush(), true, true, false, 185);

		context.addChildren(inventory, tradeInventory, tradeView, leftArrow, rightArrow, coinTab, tradeTab, viewTab);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		this.rightArrowHeld = false;
		this.leftArrowHeld = false;
		this.lastBuyCountChangeTime = 0;
		this.pressLongEnough = false;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.rightArrow))
		{
			this.rightArrowHeld = true;
			this.addTradeCoins(1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addTradeCoins(64);
			}
		}

		if (InputHelper.isHovered(this.leftArrow))
		{
			this.leftArrowHeld = true;
			this.addTradeCoins(-1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addTradeCoins(-64);
			}
		}
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int scroll = MathHelper.clamp(Mouse.getEventDWheel(), -1, 1);

		if (scroll != 0 && (InputHelper.isHovered(this.rightArrow) || InputHelper.isHovered(this.leftArrow)))
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addTradeCoins(scroll * 64);
			}
			else
			{
				this.addTradeCoins(scroll);
			}
		}
	}

	private void addTradeCoins(double val)
	{
		if (playerAether != null)
		{
			long currency = playerAether.getCurrencyModule().getCurrencyValue();

			transferCoins += val;

			if (transferCoins > currency)
			{
				transferCoins = currency;
			}

			if (transferCoins < 0)
			{
				transferCoins = 0;
			}
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		super.drawScreen(mouseX, mouseY, partialTicks);

		this.renderHoveredToolTip(mouseX, mouseY);

		if ((this.rightArrowHeld || this.leftArrowHeld) && !this.pressLongEnough && System.currentTimeMillis() - this.lastBuyCountChangeTime >= 300L)
		{
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			this.pressLongEnough = true;
		}

		if (this.pressLongEnough)
		{
			if (System.currentTimeMillis() - this.lastBuyCountChangeTime >= 50L)
			{
				this.lastBuyCountChangeTime = System.currentTimeMillis();

				if (this.rightArrowHeld)
				{
					this.addTradeCoins(1);
				}
				else if (this.leftArrowHeld)
				{
					this.addTradeCoins(-1);
				}
			}
		}

		if (playerAether != null)
		{
			long currency = playerAether.getCurrencyModule().getCurrencyValue();

			if (coinTab != null)
			{
				coinTab.setCurrencyValue(currency - transferCoins);
			}

			if (tradeTab != null)
			{
				tradeTab.setCurrencyValue(transferCoins);
			}

			if (viewTab != null)
			{
				viewTab.setCurrencyValue(33);
			}
		}
	}
}
