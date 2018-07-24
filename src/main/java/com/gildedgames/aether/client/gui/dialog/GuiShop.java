package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.client.gui.GuiUtils;
import com.gildedgames.aether.client.gui.IExtendedGui;
import com.gildedgames.aether.client.gui.util.GuiItemStack;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.ICurrencyListener;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketShopBack;
import com.gildedgames.aether.common.network.packets.PacketShopBuy;
import com.gildedgames.aether.common.network.packets.PacketShopSell;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GuiShop extends GuiFrame implements ICurrencyListener, IExtendedGui
{
	private static final ResourceLocation INVENTORY = AetherCore.getResource("textures/gui/shop/inventory.png");

	private static final ResourceLocation STOCK = AetherCore.getResource("textures/gui/shop/stock.png");

	private static final ResourceLocation UP_ARROW = AetherCore.getResource("textures/gui/shop/up_button.png");

	private static final ResourceLocation DOWN_ARROW = AetherCore.getResource("textures/gui/shop/down_button.png");

	private static final ResourceLocation UP_ARROW_HOVER = AetherCore.getResource("textures/gui/shop/up_button_hover.png");

	private static final ResourceLocation DOWN_ARROW_HOVER = AetherCore.getResource("textures/gui/shop/down_button_hover.png");

	private static final ResourceLocation LOCK = AetherCore.getResource("textures/gui/shop/lock.png");

	private static final ResourceLocation UNLOCK = AetherCore.getResource("textures/gui/shop/unlock.png");

	private static final ResourceLocation GILT_BAG = AetherCore.getResource("textures/gui/shop/gilt_bag.png");

	private static int buyCount = 1, prevBuyCount = 1;

	private static boolean isCountLocked;

	private int buyCountUnlocked = 1, prevBuyCountUnlocked = 1;

	private IDialogSlide slide;

	private IDialogSlideRenderer renderer;

	private GuiButtonVanilla sell, buy;

	private GuiCoins playerCoins, sellCoins, buyCoins;

	private IShopInstance shopInstance;

	private List<GuiShopBuy> buys = Lists.newArrayList();

	private ContainerShop container;

	private ItemStack lastSellStack;

	private int lastSellStackCount;

	private GuiItemStack stackGui;

	private com.gildedgames.orbis_api.client.gui.util.GuiTextBox buyTitle;

	private GuiTextBox npcDialogue, npcGreeting;

	private int selectedBuy = -1, prevBuy = -1;

	private GuiButtonVanilla back;

	private PlayerAether playerAether;

	private GuiAbstractButton upArrow, downArrow;

	private GuiButtonVanilla lockButton;

	private GuiTexture lock, unlock, giltBag;

	private boolean upArrowHeld, downArrowHeld, pressLongEnough;

	private long lastBuyCountChangeTime;

	private List<String> hoverDescription;

	public GuiShop(GuiFrame prevFrame, EntityPlayer player, IDialogSlide slide, IDialogSlideRenderer renderer, IShopInstance shopInstance)
	{
		super(prevFrame, Dim2D.flush(), new ContainerShop(player.inventory, shopInstance));

		this.container = (ContainerShop) this.inventorySlots;

		this.slide = slide;
		this.renderer = renderer;
		this.shopInstance = shopInstance;

		this.playerAether = PlayerAether.getPlayer(player);

		this.playerAether.getCurrencyModule().listen(this);
	}

	private IShopBuy getSelectedBuy()
	{
		return this.selectedBuy == -1 ? null : this.shopInstance.getStock().get(this.selectedBuy);
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
	public void onGuiClosed()
	{
		super.onGuiClosed();

		this.playerAether.getCurrencyModule().unlisten(this);
	}

	@Override
	public void init()
	{
		this.dim().mod().width(300).height(300).flush();

		Pos2D center = InputHelper.getCenter().clone().addX(17).flush();

		this.sellCoins = new GuiCoins(Dim2D.build().center(true).pos(center).y(this.height).addX(23).addY(-197).flush(), false);
		this.sellCoins.setVisible(false);

		this.buyCoins = new GuiCoins(Dim2D.build().center(true).pos(center).y(this.height).addX(-167).addY(-157).flush(), false);
		this.buyCoins.setVisible(false);

		this.playerCoins = new GuiCoins(Dim2D.build().centerY(true).pos(center).y(this.height).addX(168).addY(-113).flush(), true);
		this.playerCoins.setVisible(true);

		GuiTexture inventory = new GuiTexture(Dim2D.build().center(true).width(176).height(120).pos(center).y(this.height).addX(75).addY(-157F).flush(),
				INVENTORY);
		GuiTexture stock = new GuiTexture(Dim2D.build().center(true).width(176).height(80).pos(center).y(this.height).addX(-115).addY(-137).flush(), STOCK);

		this.sell = new GuiButtonVanilla(Dim2D.build().width(72).height(20).pos(center).y(this.height).addX(84).addY(-207).flush());

		this.sell.getInner().displayString = I18n.format("aether.shop.sell");
		this.sell.getInner().enabled = false;

		this.addChildren(stock, inventory, this.sell);

		this.stackGui = new GuiItemStack(Dim2D.build().pos(center).y(this.height).addX(-132).addY(-166).scale(1.0F).flush());
		this.buy = new GuiButtonVanilla(Dim2D.build().width(44).height(20).pos(center).y(this.height).addX(-106).addY(-167).flush());

		this.buy.getInner().displayString = I18n.format("aether.shop.buy", isCountLocked ? buyCount : this.buyCountUnlocked);
		this.buy.setEnabled(false);

		this.back = new GuiButtonVanilla(Dim2D.build().width(20).height(20).pos(center).y(this.height).addX(-236).addY(-125).flush());

		this.back.getInner().displayString = "<";

		this.addChildren(this.stackGui, this.buy, this.back);

		int baseBoxSize = 350;
		final boolean resize = this.width - 40 > baseBoxSize;

		this.npcDialogue = new GuiTextBox(0, resize ? (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

		int greetingBoxSize = 350;
		final boolean greetingResize = this.width - 40 > greetingBoxSize;

		this.npcGreeting = new GuiTextBox(1, greetingResize ? (this.width / 2) - (greetingBoxSize / 2) : 20, this.height - 85, greetingBoxSize, 70);

		String greeting = MathUtil.getRandomElement(this.shopInstance.getUnlocalizedGreetings(), new Random());

		this.npcGreeting.setText(new TextComponentTranslation(greeting));

		this.buyTitle = new com.gildedgames.orbis_api.client.gui.util.GuiTextBox(
				Dim2D.build().centerX(true).pos(center).width(60).height(50).y(this.height).addX(-178).addY(-40).flush(), true);

		this.buttonList.add(this.npcDialogue);
		this.buttonList.add(this.npcGreeting);

		//this.addChildren(this.buyTitle);

		for (int i = 0; i < this.shopInstance.getStock().size(); i++)
		{
			GuiShopBuy gui = new GuiShopBuy(
					Dim2D.build().width(18).height(18).pos(center).y(this.height).addX(-196 + (i * 18) - ((i / 9) * 162)).addY(-140F + ((i / 9) * 18)).flush(),
					i,
					this.shopInstance);
			this.buys.add(gui);

			this.addChildren(gui);
		}

		this.upArrow = new GuiAbstractButton(Dim2D.build().width(15).height(10).pos(center).y(this.height).addX(-49).addY(-167).flush(),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW));

		this.downArrow = new GuiAbstractButton(Dim2D.build().width(15).height(10).pos(center).y(this.height).addX(-49).addY(-157).flush(),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW));

		this.lockButton = new GuiButtonVanilla(Dim2D.build().width(14).height(20).pos(center).y(this.height).addX(-63).addY(-167).flush());

		this.lock = new GuiTexture(Dim2D.build().center(true).height(16).width(16).pos(center).y(this.height).addX(-65 + 9).addY(-167 + 10).flush(), LOCK);
		this.unlock = new GuiTexture(Dim2D.build().center(true).height(16).width(16).pos(center).y(this.height).addX(-65 + 9).addY(-167 + 10).flush(), UNLOCK);

		this.addChildren(this.playerCoins);

		this.playerCoins.setCurrencyValue(this.playerAether.getCurrencyModule().getCurrencyValue());

		this.giltBag = new GuiTexture(
				Dim2D.build().center(true).height(16).width(16).pos(center).y(this.height)
						.x(this.playerCoins.dim().x() + (this.playerCoins.dim().width() / 2) - 1)
						.addY(-138).scale(1.5F).flush(),
				GILT_BAG);

		this.addChildren(this.upArrow, this.downArrow, this.sellCoins, this.buyCoins, this.lockButton, this.lock, this.unlock, this.giltBag);
	}

	public void addBuyCount(int buyCount)
	{
		if (isCountLocked)
		{
			GuiShop.buyCount = MathHelper.clamp(GuiShop.buyCount + buyCount, 1, 64);

			if (this.getSelectedBuy() != null)
			{
				int count = (int) Math
						.min(this.getSelectedBuy().getStock(), Math.min(GuiShop.buyCount, Math.min(this.getSelectedBuy().getItemStack().getMaxStackSize(),
								this.playerAether.getCurrencyModule().getCurrencyValue() / this.getSelectedBuy().getPrice())));

				if (count <= 0)
				{
					count = 1;
				}

				if (this.stackGui.getItemStack() != null)
				{
					this.stackGui.getItemStack().setCount(count);
				}

				int value = this.getSelectedBuy().getPrice() * count;

				this.buyCoins.setCurrencyValue(value);
				this.buyCoins.setVisible(true);
			}
		}
		else
		{
			if (this.getSelectedBuy() != null)
			{
				int max = (int) Math.min(this.getSelectedBuy().getStock(), Math.min(this.getSelectedBuy().getItemStack().getMaxStackSize(),
						this.playerAether.getCurrencyModule().getCurrencyValue() / this.getSelectedBuy().getPrice()));

				if (max <= 0)
				{
					max = 1;
				}

				this.buyCountUnlocked = MathHelper.clamp(this.buyCountUnlocked + buyCount, 1, max);

				if (this.stackGui.getItemStack() != null)
				{
					this.stackGui.getItemStack().setCount(this.buyCountUnlocked);
				}

				int value = this.getSelectedBuy().getPrice() * this.buyCountUnlocked;

				this.buyCoins.setCurrencyValue(value);
				this.buyCoins.setVisible(true);
			}
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		if (InputHelper.isHovered(this.back))
		{
			this.setHoveredDescription(Lists.newArrayList(I18n.format("aether.shop.back")));
		}

		this.stackGui.setDrawCount(isCountLocked);

		this.lock.setVisible(isCountLocked);
		this.unlock.setVisible(!isCountLocked);

		if ((this.upArrowHeld || this.downArrowHeld) && !this.pressLongEnough && System.currentTimeMillis() - this.lastBuyCountChangeTime >= 300L)
		{
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			this.pressLongEnough = true;
		}

		if (this.pressLongEnough)
		{
			if (System.currentTimeMillis() - this.lastBuyCountChangeTime >= 50L)
			{
				this.lastBuyCountChangeTime = System.currentTimeMillis();

				if (this.upArrowHeld)
				{
					this.addBuyCount(1);
				}
				else if (this.downArrowHeld)
				{
					this.addBuyCount(-1);
				}
			}
		}

		this.drawWorldBackground(0);
		MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.BackgroundDrawnEvent(this));

		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		GlStateManager.translate(0, 0, 100F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (this.slide != null && this.renderer != null)
		{
			GlStateManager.pushMatrix();

			GlStateManager.translate(-100F, 0F, 0F);

			this.renderer.draw(this.slide, this.width, this.height, mouseX, mouseY, partialTicks);
			GlStateManager.popMatrix();
		}

		GlStateManager.translate(0, 0, 100F);

		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.enableDepth();

		GlStateManager.popMatrix();

		if (this.hoverDescription != null && this.hoverDescription.size() > 0)
		{
			GuiUtils.drawHoveringText(this.hoverDescription, mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
		}

		if (InputHelper.isHovered(this.lockButton))
		{
			net.minecraftforge.fml.client.config.GuiUtils
					.drawHoveringText(Lists.newArrayList(I18n.format("aether.shop.lockTooltip")), mouseX, mouseY, this.width, this.height, 120,
							Minecraft.getMinecraft().fontRenderer);
		}

		this.hoverDescription = null;

		this.renderHoveredToolTip(mouseX, mouseY);

		ItemStack stack = this.container.getSlot(0).getStack();

		if (stack != this.lastSellStack || stack.getCount() != this.lastSellStackCount)
		{
			int hash = ItemHelper.getHashForItemStack(stack);
			IShopBuy shopBuy = null;

			for (IShopBuy buy : this.shopInstance.getStock())
			{
				int buyHash = ItemHelper.getHashForItemStack(buy.getItemStack());

				if (buyHash == hash)
				{
					shopBuy = buy;
					break;
				}
			}

			double value;

			if (shopBuy != null)
			{
				value = shopBuy.getSellingPrice() * (double) stack.getCount();
			}
			else
			{
				value = AetherAPI.content().currency().getValue(stack);
			}

			this.sellCoins.setCurrencyValue(value);

			this.lastSellStack = stack;
			this.lastSellStackCount = stack.getCount();

			this.sell.setEnabled(value >= 1);
			this.sellCoins.setVisible(true);
		}

		if (this.getSelectedBuy() != null)
		{
			this.npcGreeting.visible = false;

			int maxAllowedWithHeldStack = this.getSelectedBuy().getItemStack().getMaxStackSize() - this.mc.player.inventory.getItemStack().getCount();

			int amount = Math.min(maxAllowedWithHeldStack, Math.min(buyCount, this.getSelectedBuy().getStock()));

			boolean canAfford = this.playerAether.getCurrencyModule().getCurrencyValue() >= this.getSelectedBuy().getPrice();
			boolean isHandFree = this.mc.player.inventory.getItemStack().isEmpty();
			boolean isBuyItem = ItemHelper.getHashForItemStack(this.mc.player.inventory.getItemStack()) == ItemHelper
					.getHashForItemStack(this.getSelectedBuy().getItemStack());
			boolean canStack = this.mc.player.inventory.getItemStack().isStackable();
			boolean isAtStackLimit = this.mc.player.inventory.getItemStack().getCount() >= this.mc.player.inventory.getItemStack().getMaxStackSize();
			boolean hasStock = this.getSelectedBuy().getStock() > 0 && amount > 0;

			this.buy.setEnabled(hasStock && !isAtStackLimit && canStack && canAfford && (isHandFree || isBuyItem) && this.getSelectedBuy().getStock() > 0);
		}
		else
		{
			this.npcGreeting.visible = true;
		}

		if (this.getSelectedBuy() != null && this.prevBuy != this.selectedBuy)
		{
			this.stackGui.setItemStack(this.getSelectedBuy().getItemStack());
			//this.buyTitle.setText(new Text(new TextComponentTranslation(this.getSelectedBuy().getItemStack().getDisplayName()), 1.0F));
			//this.buyTitle.init();

			String chosenDesc = MathUtil.getRandomElement(this.getSelectedBuy().getUnlocalizedDescriptions(), new Random());

			this.npcDialogue.setText(new TextComponentTranslation(chosenDesc));

			this.prevBuy = this.selectedBuy;

			int count = (int) Math
					.min(isCountLocked ? GuiShop.buyCount : this.buyCountUnlocked, Math.min(this.getSelectedBuy().getItemStack().getMaxStackSize(),
							this.playerAether.getCurrencyModule().getCurrencyValue() / this.getSelectedBuy().getPrice()));

			if (count <= 0)
			{
				count = 1;
			}

			int value = this.getSelectedBuy().getPrice() * count;

			this.buyCoins.setCurrencyValue(value);
			this.buyCoins.setVisible(true);
		}

		if (buyCount != prevBuyCount && isCountLocked)
		{
			prevBuyCount = buyCount;

			this.buy.getInner().displayString = I18n.format("aether.shop.buy", buyCount);
		}

		if (this.buyCountUnlocked != this.prevBuyCountUnlocked && !isCountLocked)
		{
			this.prevBuyCountUnlocked = this.buyCountUnlocked;

			this.buy.getInner().displayString = I18n.format("aether.shop.buy", this.buyCountUnlocked);
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{

	}

	@Override
	public void drawDefaultBackground()
	{

	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		super.mouseReleased(mouseX, mouseY, state);

		this.upArrowHeld = false;
		this.downArrowHeld = false;
		this.lastBuyCountChangeTime = 0;
		this.pressLongEnough = false;
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.lockButton))
		{
			isCountLocked = !isCountLocked;

			if (isCountLocked)
			{
				this.buy.getInner().displayString = I18n.format("aether.shop.buy", buyCount);

				if (this.getSelectedBuy() != null)
				{
					int count = (int) Math
							.min(this.getSelectedBuy().getStock(), Math.min(GuiShop.buyCount, Math.min(this.getSelectedBuy().getItemStack().getMaxStackSize(),
									this.playerAether.getCurrencyModule().getCurrencyValue() / this.getSelectedBuy().getPrice())));

					if (this.stackGui.getItemStack() != null)
					{
						this.stackGui.getItemStack().setCount(count);
					}

					int value = this.getSelectedBuy().getPrice() * count;

					this.buyCoins.setCurrencyValue(value);
					this.buyCoins.setVisible(true);
				}

				this.addBuyCount(0);
			}
			else
			{
				this.buy.getInner().displayString = I18n.format("aether.shop.buy", this.buyCountUnlocked);

				if (this.stackGui.getItemStack() != null)
				{
					this.stackGui.getItemStack().setCount(this.buyCountUnlocked);
				}

				if (this.getSelectedBuy() != null)
				{
					int value = this.getSelectedBuy().getPrice() * this.buyCountUnlocked;

					this.buyCoins.setCurrencyValue(value);
					this.buyCoins.setVisible(true);
				}
			}
		}

		if (InputHelper.isHovered(this.upArrow))
		{
			this.upArrowHeld = true;
			this.addBuyCount(1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addBuyCount(64);
			}
		}

		if (InputHelper.isHovered(this.downArrow))
		{
			this.downArrowHeld = true;
			this.addBuyCount(-1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addBuyCount(-64);
			}
		}

		if (InputHelper.isHovered(this.back))
		{
			NetworkingAether.sendPacketToServer(new PacketShopBack());
		}

		if (InputHelper.isHovered(this.buy) && this.buy.isEnabled())
		{
			int index = 0;

			for (int i = 0; i < this.shopInstance.getStock().size(); i++)
			{
				IShopBuy shopBuy = this.shopInstance.getStock().get(i);

				if (shopBuy == this.getSelectedBuy())
				{
					index = i;
					break;
				}
			}

			NetworkingAether.sendPacketToServer(new PacketShopBuy(index, isCountLocked ? buyCount : this.buyCountUnlocked));

			int maxAllowedWithHeldStack = this.getSelectedBuy().getItemStack().getMaxStackSize() - this.mc.player.inventory.getItemStack().getCount();

			int amount = Math.min(maxAllowedWithHeldStack, Math.min(isCountLocked ? buyCount : this.buyCountUnlocked, this.getSelectedBuy().getStock()));

			boolean isHandFree = this.mc.player.inventory.getItemStack().isEmpty();
			boolean isBuyItem = ItemHelper.getHashForItemStack(this.mc.player.inventory.getItemStack()) == ItemHelper
					.getHashForItemStack(this.getSelectedBuy().getItemStack());

			if (isHandFree)
			{
				this.getSelectedBuy().addStock(-amount);

				ItemStack stack = this.getSelectedBuy().getItemStack().copy();
				stack.setCount(amount);

				this.mc.player.inventory.setItemStack(stack);
			}
			else if (isBuyItem)
			{
				this.getSelectedBuy().addStock(-amount);

				this.mc.player.inventory.getItemStack().setCount(this.mc.player.inventory.getItemStack().getCount() + amount);
			}

			this.addBuyCount(0);
		}

		if (InputHelper.isHovered(this.sell) && this.sell.isEnabled())
		{
			ItemStack stack = this.container.getSlot(0).getStack();
			double singleValue = AetherAPI.content().currency().getSingleValue(stack);

			if (singleValue < 1)
			{
				double wholeValue = AetherAPI.content().currency().getValue(stack);
				double decimals = wholeValue - MathHelper.floor(wholeValue);

				double howManyTimesDivInto = decimals / singleValue;

				int leftover = MathHelper.floor(howManyTimesDivInto);

				this.container.getSlot(0).getStack().setCount(leftover);
			}
			else
			{
				this.container.getSlot(0).putStack(ItemStack.EMPTY);
			}

			NetworkingAether.sendPacketToServer(new PacketShopSell());
		}

		for (GuiShopBuy b : this.buys)
		{
			if (InputHelper.isHovered(b))
			{
				this.buyCountUnlocked = 1;
				this.selectedBuy = b.getBuyIndex();

				break;
			}
		}
	}

	@Override
	public void onCurrencyChange(long prevCurrency, long newCurrency)
	{
		this.playerCoins.setCurrencyValue(newCurrency);

		this.giltBag.dim().mod().x(this.playerCoins.dim().x() + (this.playerCoins.dim().width() / 2) - 1).flush();

		this.addBuyCount(0);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int scroll = MathHelper.clamp(Mouse.getEventDWheel(), -1, 1);

		if (scroll != 0 && (InputHelper.isHovered(this.buy) || InputHelper.isHovered(this.upArrow) || InputHelper.isHovered(this.downArrow) || InputHelper
				.isHovered(this.lockButton)))
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				this.addBuyCount(scroll * 64);
			}
			else
			{
				this.addBuyCount(scroll);
			}
		}
	}

	@Override
	public List<String> getHoveredDescription()
	{
		return this.hoverDescription;
	}

	@Override
	public void setHoveredDescription(List<String> desc)
	{
		this.hoverDescription = desc;
	}
}
