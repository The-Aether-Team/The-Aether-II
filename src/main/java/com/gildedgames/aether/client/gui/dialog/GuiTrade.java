package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.client.gui.util.IRemoteClose;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerTradeModule;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.trade.PacketChangeCoinAmount;
import com.gildedgames.aether.common.network.packets.trade.PacketTradeMessage;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.*;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;

public class GuiTrade extends GuiViewer implements IRemoteClose
{

	private static final ResourceLocation INVENTORY = AetherCore.getResource("textures/gui/trade/inventory.png");

	private static final ResourceLocation TRADE_INVENTORY = AetherCore.getResource("textures/gui/trade/trade.png");

	private static final ResourceLocation TRADE_VIEW = AetherCore.getResource("textures/gui/trade/view.png");

	private static final ResourceLocation LEFT_ARROW = AetherCore.getResource("textures/gui/trade/left_button.png");

	private static final ResourceLocation LEFT_ARROW_HOVER = AetherCore.getResource("textures/gui/trade/left_button_hover.png");

	private static final ResourceLocation RIGHT_ARROW = AetherCore.getResource("textures/gui/trade/right_button.png");

	private static final ResourceLocation RIGHT_ARROW_HOVER = AetherCore.getResource("textures/gui/trade/right_button_hover.png");

	private static final ResourceLocation SLOT_BLOCKED = AetherCore.getResource("textures/gui/trade/prevent_tile.png");

	private final NonNullList<ItemStack> offeredContents;

	private final List<TradeChatLine> chatLines = Lists.newArrayList();

	private final PlayerAether playerAether;

	private final PlayerTradeModule module;

	private final String lockinLocalText, unlockLocalText;

	private GuiAbstractButton leftArrow, rightArrow;

	private GuiExpandableCoinTab coinTab, tradeTab, viewTab;

	private GuiTextBox tradePlate, tradeStatus;

	private GuiFlatButton lockStateButton, confirmButton;

	private GuiTextField textField;

	private boolean pressLongEnough, rightArrowHeld, leftArrowHeld, labelUpdated;

	private long lastBuyCountChangeTime;

	private double transferCoins;

	public GuiTrade(GuiViewer prevViewer, EntityPlayer player)
	{
		super(new GuiElement(Dim2D.flush(), false), prevViewer, new ContainerTrade(player.inventory));

		this.playerAether = PlayerAether.getPlayer(player);
		this.module = this.playerAether.getTradingModule();
		this.offeredContents = NonNullList.withSize(16, ItemStack.EMPTY);
		this.lockinLocalText = I18n.format("aether.trade.gui.lock");
		this.unlockLocalText = I18n.format("aether.trade.gui.unlock");

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

		tradeInventory.state().addEvent(new IGuiEvent()
		{
			@Override
			public void onPostDraw(IGuiElement element)
			{
				GlStateManager.pushMatrix();
				GuiTrade.this.mc.getTextureManager().bindTexture(SLOT_BLOCKED);

				for (int i = GuiTrade.this.module.getTradeSlots(); i < 16; i++)
				{
					Gui.drawModalRectWithCustomSizedTexture(GuiTrade.this.guiLeft + 199 + (i % 4) * 18, GuiTrade.this.height - 183 + (i / 4) * 18, 0, 0, 18, 18, 18, 18);
				}

				GlStateManager.popMatrix();
			}
		});

		GuiTexture tradeView = new GuiTexture(Dim2D.build().center(true).width(86).height(110).pos(center).y(this.height).addX(147).addY(-157F).flush(),
				TRADE_VIEW);

		tradeView.state().addEvent(new IGuiEvent()
		{
			@Override
			public void onPostDraw(IGuiElement element)
			{
				GlStateManager.pushMatrix();
				GuiTrade.this.mc.getTextureManager().bindTexture(SLOT_BLOCKED);

				for (int i = GuiTrade.this.module.getOpenSlots(); i < 16; i++)
				{
					Gui.drawModalRectWithCustomSizedTexture(GuiTrade.this.guiLeft + 292 + (i % 4) * 18, GuiTrade.this.height - 183 + (i / 4) * 18, 0, 0, 18, 18, 18, 18);
				}

				GlStateManager.popMatrix();
			}
		});

		this.leftArrow = new GuiAbstractButton(Dim2D.build().width(9).height(15).pos(center).y(this.height).addX(-9).addY(-205).flush(),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), LEFT_ARROW));

		this.rightArrow = new GuiAbstractButton(Dim2D.build().width(9).height(15).pos(center).y(this.height).addX(1).addY(-205).flush(),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(9).height(15).flush(), RIGHT_ARROW));

		this.coinTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).flush(), true, true, false, -15, this.height - 212);
		this.tradeTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).flush(), true, false, false, 17, this.height - 212);
		this.viewTab = new GuiExpandableCoinTab(Dim2D.build().width(9).height(15).flush(), true, true, false, 185, this.height - 212);

		context.addChildren(inventory, tradeInventory, tradeView, this.leftArrow, this.rightArrow, this.coinTab, this.tradeTab, this.viewTab);

		final String name = I18n.format("aether.trade.gui.firsttrade");
		final String nameOther = I18n.format("aether.trade.gui.thirdtrade", "");
		final String status = I18n.format("aether.trade.status.waitingboth");

		final ITextComponent clientTextComp = new TextComponentTranslation(name);
		final ITextComponent traderTextComp = new TextComponentTranslation(nameOther);
		final ITextComponent statusComp = new TextComponentTranslation(status);

		this.tradePlate = new GuiTextBox(0, (int) center.x() + 104, this.height - 229, 86, 15);
		this.tradeStatus = new GuiTextBox(0, this.width - 88, this.height - 60, 76, 40);

		GuiTextBox namePlate = new GuiTextBox(0, (int) center.x() + 10, this.height - 229, 86, 15);

		clientTextComp.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));
		traderTextComp.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));
		statusComp.setStyle(new Style().setColor(TextFormatting.YELLOW));

		this.tradePlate.setText(traderTextComp);
		this.tradePlate.setCentered(true);
		this.tradeStatus.setText(statusComp);
		this.tradeStatus.setCentered(true);

		namePlate.setText(clientTextComp);
		namePlate.setCentered(true);

		this.buttonList.add(this.tradePlate);
		this.buttonList.add(this.tradeStatus);
		this.buttonList.add(namePlate);

		this.lockStateButton = new GuiFlatButton(1, this.width - 88, this.height - 80, 25, 15, this.lockinLocalText);

		final String unlockText = I18n.format("aether.trade.gui.confirm");

		this.confirmButton = new GuiFlatButton(2, this.width - 88, this.height - 17, 37, 15, unlockText);
		this.confirmButton.enabled = false;

		this.textField = new GuiTextField(0, this.fontRenderer, 4, this.height - 12, this.width - 104, 12);
		this.textField.setMaxStringLength(256);
		this.textField.setEnableBackgroundDrawing(false);

		this.buttonList.add(this.lockStateButton);
		this.buttonList.add(this.confirmButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if (keyCode == 1 || !this.textField.textboxKeyTyped(typedChar, keyCode))
		{
			super.keyTyped(typedChar, keyCode);
		}

		if (keyCode == 28 && !this.textField.getText().isEmpty())
		{
			String text = this.textField.getText();

			NetworkingAether.sendPacketToServer(new PacketTradeMessage(text));

			this.sendTradeMessage(this.playerAether.getEntity(), new TextComponentString(this.textField.getText()));
			this.textField.setText("");
		}
	}

	public void sendTradeMessage(EntityPlayer sender, ITextComponent textComponent)
	{
		ITextComponent name = new TextComponentString("<" + sender.getDisplayNameString() + "> ");

		this.sendMessage(name.appendSibling(textComponent));
	}

	public void sendMessage(ITextComponent textComponent)
	{
		List<ITextComponent> split = GuiUtilRenderComponents.splitText(textComponent, this.width - 104, this.fontRenderer, true, true);

		for (ITextComponent text : split)
		{
			this.chatLines.add(new TradeChatLine(text));
		}
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
			this.addTradeCoins(1);
			this.rightArrowHeld = true;
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

		this.textField.mouseClicked(mouseX, mouseY, mouseButton);
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
		if (this.module.isLockedIn())
		{
			return;
		}

		if (this.playerAether != null)
		{
			long currency = this.playerAether.getCurrencyModule().getCurrencyValue();

			this.transferCoins += val;

			if (this.transferCoins > currency)
			{
				this.transferCoins = currency;
			}

			if (this.transferCoins < 0)
			{
				this.transferCoins = 0;
			}
		}

		NetworkingAether.sendPacketToServer(new PacketChangeCoinAmount(this.transferCoins));
	}

	public void setCoinCount(double count)
	{
		this.viewTab.setCurrencyValue(count);
	}

	private void renderToolTip(int x, int y)
	{
		ItemStack stack = null;
		Slot hoveredSlot = this.getSlotUnderMouse();

		if (hoveredSlot  != null && hoveredSlot.getHasStack())
		{
			stack = hoveredSlot.getStack();
		}
		else
		{
			for (int i = 0; i < 16; i++)
			{
				ItemStack offeredStack = this.offeredContents.get(i);

				if (!offeredStack.isEmpty())
				{
					int slotX = this.guiLeft + 293 + (i % 4) * 18;
					int slotY = this.height - 182 + (i / 4) * 18;

					if (x >= slotX && x < slotX + 18 && y >= slotY && y < slotY + 18)
					{
						stack = offeredStack;
					}
				}
			}
		}

		if (this.mc.player.inventory.getItemStack().isEmpty() && stack != null)
		{
			FontRenderer font = stack.getItem().getFontRenderer(stack);
			FontRenderer renderer = (font == null ? this.fontRenderer : font);
			GuiUtils.preItemToolTip(stack);

			List<String> stackText = this.getItemToolTip(stack);

			this.drawHoveringText(stackText, x, y, renderer);
			GuiUtils.postItemToolTip();
		}
	}

	@Override
	public void updateScreen()
	{
		this.textField.updateCursorCounter();
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		if (!this.labelUpdated)
		{
			this.updateTraderLabel();
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

		if (this.inventorySlots instanceof ContainerTrade)
		{
			ContainerTrade container = (ContainerTrade) this.inventorySlots;

			container.updateSlots(this.module.getTradeSlots());
		}

		for (int i = 0; i < 16; i++)
		{
			ItemStack stack = this.offeredContents.get(i);

			this.drawItemStack(stack, this.guiLeft + 293 + (i % 4) * 18, this.height - 182 + (i / 4) * 18);
		}

		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		this.renderToolTip(mouseX, mouseY);
		GlStateManager.popMatrix();

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

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

		if (this.playerAether != null)
		{
			long currency = this.playerAether.getCurrencyModule().getCurrencyValue();

			if (this.coinTab != null)
			{
				this.coinTab.setCurrencyValue(currency - this.transferCoins);
			}

			if (this.tradeTab != null)
			{
				this.tradeTab.setCurrencyValue(this.transferCoins);
			}
		}

		Gui.drawRect(2, this.height - 14, this.width - 102, this.height - 2, Integer.MIN_VALUE);
		Gui.drawRect(2, this.height - 80, this.width - 102, this.height - 16, Integer.MIN_VALUE);

		this.textField.drawTextBox();

		for (int i = 0; i < Math.min(7, this.chatLines.size()); i++)
		{
			TradeChatLine tradeChatLine = this.chatLines.get(this.chatLines.size() - i - 1);
			int y = this.height - 25 - this.fontRenderer.FONT_HEIGHT * i;

			if (tradeChatLine instanceof TriggerChatLine)
			{
				this.drawString(this.fontRenderer, tradeChatLine.text.getUnformattedText(), 4, y - this.fontRenderer.FONT_HEIGHT / 2, 0xFFFFFF);
				i++;
			}
			else
			{
				this.drawString(this.fontRenderer, tradeChatLine.text.getUnformattedText(), 4, y, 0xFFFFFF);
			}
		}
	}

	private void drawItemStack(ItemStack stack, int x, int y)
	{
		GlStateManager.translate(0.0F, 0.0F, 32.0F);

		this.zLevel = 200.0F;
		this.itemRender.zLevel = 200.0F;

		FontRenderer font = stack.getItem().getFontRenderer(stack);

		if (font == null)
		{
			font = this.fontRenderer;
		}

		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, null);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}


	@Override
	public int getConfirmID()
	{
		return AetherGuiHandler.TRADE_ID;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		boolean locked = this.module.isLockedIn();

		if (button.id == 1)
		{
			this.module.setLockedIn(!locked);
			this.updateLockedButton();
		}
		else if (button.id == 2 && locked)
		{
			this.module.setConfirmed(true);
		}
	}

	private void updateLockedButton()
	{
		this.lockStateButton.setText(this.module.isLockedIn() ? this.unlockLocalText : this.lockinLocalText);
		this.lockStateButton.x = this.width - (this.module.isLockedIn() ? 85 : 88);
	}

	public void setTradeOffer(List<Pair<Integer,ItemStack>> changes)
	{
		int count = 0;

		for (final Pair<Integer, ItemStack> pair : changes)
		{
			this.offeredContents.set(pair.getKey(), pair.getValue());
			count++;
		}

		for (int i = count; i < this.offeredContents.size(); i++)
		{
			this.offeredContents.set(i, ItemStack.EMPTY);
		}
	}

	public void setTradeState(int state)
	{
		this.updateLockedButton();

		String append = state < 3 ? "waiting" : "confirm";

		if (state == -1)
		{
			this.lockStateButton.enabled = false;

			append = "sizeerror";
		}
		else
		{
			this.lockStateButton.enabled = true;

			final int modState = state % 3;

			if (modState == 0)
			{
				append += "both";
			}
			else if (modState == 1)
			{
				append += "you";
			}
			else
			{
				append += "them";
			}
		}

		this.confirmButton.enabled = state >= 3 && state < 5;

		final String status = I18n.format("aether.trade.status." + append);
		final ITextComponent statusComp = new TextComponentTranslation(status);

		statusComp.setStyle(new Style().setColor(state == -1 ? TextFormatting.RED : TextFormatting.YELLOW));

		this.tradeStatus.setText(statusComp);
	}

	private void updateTraderLabel()
	{
		PlayerAether target = this.module.getTarget();

		if (target != null)
		{
			final String nameOther = I18n.format("aether.trade.gui.thirdtrade", target.getEntity().getDisplayNameString());
			final ITextComponent traderTextComp = new TextComponentTranslation(nameOther);

			traderTextComp.setStyle(new Style().setColor(TextFormatting.YELLOW).setItalic(true));

			int count = GuiUtilRenderComponents.splitText(traderTextComp, this.tradePlate.width - 10, this.fontRenderer, true, true).size() - 1;

			this.tradePlate.y = this.height - 229 - this.fontRenderer.FONT_HEIGHT * count;
			this.tradePlate.setText(traderTextComp);
			this.labelUpdated = true;
		}
	}

	public String getTrader()
	{
		return this.module.getTarget().getEntity().getDisplayNameString();
	}

	class TradeChatLine
	{
		protected ITextComponent text;

		public TradeChatLine(ITextComponent text)
		{
			this.text = text;
		}
	}

	class TriggerChatLine extends TradeChatLine
	{
		protected int id;

		public TriggerChatLine(int id, ITextComponent text)
		{
			super(text);

			this.id = id;
		}
	}
}

