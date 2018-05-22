package com.gildedgames.aether.client.gui.dialog;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.IDialogSlide;
import com.gildedgames.aether.api.dialog.IDialogSlideRenderer;
import com.gildedgames.aether.api.shop.IShopBuy;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.client.gui.util.GuiItemStack;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.ICurrencyListener;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.containers.ContainerShop;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketShopBack;
import com.gildedgames.aether.common.network.packets.PacketShopBuy;
import com.gildedgames.aether.common.network.packets.PacketShopSell;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import com.gildedgames.aether.common.util.helpers.MathUtil;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.client.rect.RectModifier;
import com.gildedgames.orbis_api.client.rect.helpers.RectCollection;
import com.gildedgames.orbis_api.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class GuiShop extends GuiFrame implements ICurrencyListener
{
	private static final ResourceLocation INVENTORY = AetherCore.getResource("textures/gui/shop/inventory.png");

	private static final ResourceLocation STOCK = AetherCore.getResource("textures/gui/shop/stock.png");

	private static final ResourceLocation SELL_BAR = AetherCore.getResource("textures/gui/shop/sell_bar.png");

	private static final ResourceLocation GILT = AetherCore.getResource("textures/gui/shop/gilt.png");

	private static final ResourceLocation GILTAE = AetherCore.getResource("textures/gui/shop/giltae.png");

	private static final ResourceLocation GILTAEN = AetherCore.getResource("textures/gui/shop/giltaen.png");

	private static final ResourceLocation GILTAENI = AetherCore.getResource("textures/gui/shop/giltaeni.png");

	private static final ResourceLocation UP_ARROW = AetherCore.getResource("textures/gui/shop/up_button.png");

	private static final ResourceLocation DOWN_ARROW = AetherCore.getResource("textures/gui/shop/down_button.png");

	private static final ResourceLocation UP_ARROW_HOVER = AetherCore.getResource("textures/gui/shop/up_button_hover.png");

	private static final ResourceLocation DOWN_ARROW_HOVER = AetherCore.getResource("textures/gui/shop/down_button_hover.png");

	private static int buyCount = 1, prevBuyCount = 1;

	private IDialogSlide slide;

	private IDialogSlideRenderer renderer;

	private GuiButtonVanilla sell, buy;

	private GuiTexture gilt, giltae, giltaen, giltaeni;

	private GuiText giltCount, giltaeCount, giltaenCount, giltaeniCount;

	private GuiText giltSellCount, giltaeSellCount, giltaenSellCount, giltaeniSellCount;

	private GuiTexture giltSell, giltaeSell, giltaenSell, giltaeniSell;

	private GuiText giltBuyCount, giltaeBuyCount, giltaenBuyCount, giltaeniBuyCount;

	private GuiTexture giltBuy, giltaeBuy, giltaenBuy, giltaeniBuy;

	private IShopInstance shopInstance;

	private List<GuiShopBuy> buys = Lists.newArrayList();

	private ContainerShop container;

	private ItemStack lastSellStack;

	private int lastSellStackCount;

	private RectCollection coinOffset = RectCollection.flush(Dim2D.build().y(7).x(13).flush());

	private GuiItemStack stackGui;

	private com.gildedgames.orbis_api.client.gui.util.GuiTextBox buyTitle;

	private GuiTextBox npcDialogue, npcGreeting;

	private int selectedBuy = -1, prevBuy = -1;

	private GuiButtonVanilla back;

	private PlayerAether playerAether;

	private GuiAbstractButton upArrow, downArrow;

	private boolean upArrowHeld, downArrowHeld, pressLongEnough;

	private long lastBuyCountChangeTime;

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
		this.guiTop = this.height - 198 - 7;

		this.xSize = 345;
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

		GuiTexture inventory = new GuiTexture(Dim2D.build().center(true).width(176).height(115).pos(center).y(this.height).addX(75).addY(-154.5F).flush(),
				INVENTORY);
		GuiTexture stock = new GuiTexture(Dim2D.build().center(true).width(176).height(70).pos(center).y(this.height).addX(-115).addY(-132).flush(), STOCK);

		GuiTexture sell_bar = new GuiTexture(Dim2D.build().center(true).width(176).height(40).pos(center).y(this.height).addX(-115).addY(-190).flush(),
				SELL_BAR);

		this.sell = new GuiButtonVanilla(Dim2D.build().width(70).height(20).pos(center).y(this.height).addX(-165).addY(-200).flush());

		this.sell.getInner().displayString = I18n.format("aether.shop.sell");
		this.sell.getInner().enabled = false;

		this.gilt = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(0).addY(-199 + 4).flush(), GILT);
		this.giltae = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(40).addY(-199 + 4).flush(), GILTAE);
		this.giltaen = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(80).addY(-199 + 4).flush(), GILTAEN);
		this.giltaeni = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(120).addY(-199 + 4).flush(),
				GILTAENI);

		this.giltSell = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-82).addY(-198).flush(), GILT);
		this.giltaeSell = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-55).addY(-198).flush(), GILTAE);
		this.giltaenSell = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-82).addY(-183).flush(), GILTAEN);
		this.giltaeniSell = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-55).addY(-183).flush(),
				GILTAENI);

		this.giltBuy = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-197).addY(-25 + 5).flush(), GILT);
		this.giltaeBuy = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-170).addY(-25 + 5).flush(), GILTAE);
		this.giltaenBuy = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-197).addY(-10 + 5).flush(), GILTAEN);
		this.giltaeniBuy = new GuiTexture(Dim2D.build().center(true).width(7).height(7).pos(center).y(this.height).addX(-170).addY(-10 + 5).flush(),
				GILTAENI);

		this.giltBuy.setVisible(false);
		this.giltaeBuy.setVisible(false);
		this.giltaenBuy.setVisible(false);
		this.giltaeniBuy.setVisible(false);

		PlayerCurrencyModule module = this.playerAether.getCurrencyModule();

		this.giltCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(6).addY(-203 + 4).flush(),
				new Text(new TextComponentString(String.valueOf(module.getGilt())), 1.0F));
		this.giltaeCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(46).addY(-203 + 4).flush(),
				new Text(new TextComponentString(String.valueOf(module.getGiltae())), 1.0F));
		this.giltaenCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(86).addY(-203 + 4).flush(),
				new Text(new TextComponentString(String.valueOf(module.getGiltaen())), 1.0F));
		this.giltaeniCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(126).addY(-203 + 4).flush(),
				new Text(new TextComponentString(String.valueOf(module.getGiltaeni())), 1.0F));

		this.giltSellCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-76).addY(-202).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaeSellCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-49).addY(-202).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaenSellCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-76).addY(-187).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaeniSellCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-49).addY(-187).flush(),
				new Text(new TextComponentString("0"), 1.0F));

		this.giltBuyCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-191).addY(-29 + 5).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaeBuyCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-164).addY(-29 + 5).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaenBuyCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-191).addY(-14 + 5).flush(),
				new Text(new TextComponentString("0"), 1.0F));
		this.giltaeniBuyCount = new GuiText(Dim2D.build().pos(center).y(this.height).addX(-164).addY(-14 + 5).flush(),
				new Text(new TextComponentString("0"), 1.0F));

		this.giltBuyCount.setVisible(false);
		this.giltaeBuyCount.setVisible(false);
		this.giltaenBuyCount.setVisible(false);
		this.giltaeniBuyCount.setVisible(false);

		this.addChildren(stock, inventory, sell_bar, this.sell);

		this.giltaeni.setVisible(module.getGiltaeni() > 0);
		this.giltaeniCount.setVisible(module.getGiltaeni() > 0);

		this.giltaen.setVisible(module.getGiltaen() > 0);
		this.giltaenCount.setVisible(module.getGiltaen() > 0);

		this.giltae.setVisible(module.getGiltae() > 0);
		this.giltaeCount.setVisible(module.getGiltae() > 0);

		this.gilt.setVisible(module.getGilt() > 0);
		this.giltCount.setVisible(module.getGilt() > 0);

		this.addChildren(this.gilt, this.giltae, this.giltaen, this.giltaeni, this.giltCount, this.giltaeCount, this.giltaenCount, this.giltaeniCount);
		this.addChildren(this.giltSell, this.giltaeSell, this.giltaenSell, this.giltaeniSell, this.giltSellCount, this.giltaeSellCount, this.giltaenSellCount,
				this.giltaeniSellCount);
		this.addChildren(this.giltBuy, this.giltaeBuy, this.giltaenBuy, this.giltaeniBuy, this.giltBuyCount, this.giltaeBuyCount, this.giltaenBuyCount,
				this.giltaeniBuyCount);

		this.giltSell.setVisible(false);
		this.giltaeSell.setVisible(false);
		this.giltaenSell.setVisible(false);
		this.giltaeniSell.setVisible(false);

		this.giltSellCount.setVisible(false);
		this.giltaeSellCount.setVisible(false);
		this.giltaenSellCount.setVisible(false);
		this.giltaeniSellCount.setVisible(false);

		this.stackGui = new GuiItemStack(Dim2D.build().pos(center).y(this.height).addX(-201).addY(-88).scale(2.5F).flush());
		this.buy = new GuiButtonVanilla(Dim2D.build().width(64).height(20).pos(center).y(this.height).addX(-113).addY(-122).flush());

		this.buy.getInner().displayString = I18n.format("aether.shop.buy", buyCount);
		this.buy.setEnabled(false);

		this.back = new GuiButtonVanilla(Dim2D.build().width(79).height(20).pos(center).y(this.height).addX(-196).addY(-122).flush());

		this.back.getInner().displayString = I18n.format("aether.shop.back");

		this.addChildren(this.stackGui, this.buy, this.back);

		int baseBoxSize = 310;
		final boolean resize = this.width - 40 > baseBoxSize;

		this.npcDialogue = new GuiTextBox(0, resize ? 30 + (this.width / 2) - (baseBoxSize / 2) : 20, this.height - 85, baseBoxSize, 70);

		int greetingBoxSize = 350;
		final boolean greetingResize = this.width - 40 > greetingBoxSize;

		this.npcGreeting = new GuiTextBox(1, greetingResize ? (this.width / 2) - (greetingBoxSize / 2) : 20, this.height - 85, greetingBoxSize, 70);

		String greeting = MathUtil.getRandomElement(this.shopInstance.getUnlocalizedGreetings(), new Random());

		this.npcGreeting.setText(new TextComponentTranslation(greeting));

		this.buyTitle = new com.gildedgames.orbis_api.client.gui.util.GuiTextBox(
				Dim2D.build().centerX(true).pos(center).width(60).height(50).y(this.height).addX(-178).addY(-40).flush(), true);

		this.buttonList.add(this.npcDialogue);
		this.buttonList.add(this.npcGreeting);

		this.addChildren(this.buyTitle);

		for (int i = 0; i < this.shopInstance.getStock().size(); i++)
		{
			GuiShopBuy gui = new GuiShopBuy(
					Dim2D.build().width(18).height(18).pos(center).y(this.height).addX(-196 + (i * 18) - ((i / 9) * 162)).addY(-160F + ((i / 9) * 18)).flush(),
					i,
					this.shopInstance);
			this.buys.add(gui);

			this.addChildren(gui);
		}

		this.upArrow = new GuiAbstractButton(Dim2D.build().width(15).height(10).pos(center).y(this.height).addX(-49).addY(-122).flush(),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), UP_ARROW));

		this.downArrow = new GuiAbstractButton(Dim2D.build().width(15).height(10).pos(center).y(this.height).addX(-49).addY(-112).flush(),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW_HOVER),
				new GuiTexture(Dim2D.build().width(15).height(10).flush(), DOWN_ARROW));

		this.addChildren(this.upArrow, this.downArrow);
	}

	private void refreshGilt()
	{
		PlayerCurrencyModule module = this.playerAether.getCurrencyModule();

		this.giltCount.setText(new Text(new TextComponentString(String.valueOf(module.getGilt())), 1.0F));
		this.giltaeCount.setText(new Text(new TextComponentString(String.valueOf(module.getGiltae())), 1.0F));
		this.giltaenCount.setText(new Text(new TextComponentString(String.valueOf(module.getGiltaen())), 1.0F));
		this.giltaeniCount.setText(new Text(new TextComponentString(String.valueOf(module.getGiltaeni())), 1.0F));

		this.giltaeni.setVisible(module.getGiltaeni() > 0);
		this.giltaeniCount.setVisible(module.getGiltaeni() > 0);

		this.giltaen.setVisible(module.getGiltaen() > 0 || module.getGiltaeni() > 0);
		this.giltaenCount.setVisible(module.getGiltaen() > 0 || module.getGiltaeni() > 0);

		this.giltae.setVisible(module.getGiltae() > 0 || module.getGiltaen() > 0 || module.getGiltaeni() > 0);
		this.giltaeCount.setVisible(module.getGiltae() > 0 || module.getGiltaen() > 0 || module.getGiltaeni() > 0);

		this.gilt.setVisible(module.getGilt() >= 0 || module.getGiltae() > 0 || module.getGiltaen() > 0 || module.getGiltaeni() > 0);
		this.giltCount.setVisible(module.getGilt() >= 0 || module.getGiltae() > 0 || module.getGiltaen() > 0 || module.getGiltaeni() > 0);
	}

	public void addBuyCount(int buyCount)
	{
		GuiShop.buyCount = MathHelper.clamp(GuiShop.buyCount + buyCount, 1, 64);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
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
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this));

		GlStateManager.pushMatrix();

		GlStateManager.disableDepth();

		GlStateManager.translate(0, 0, 100F);
		GlStateManager.color(1.0F, 1.0F, 1.0F);

		if (this.slide != null && this.renderer != null)
		{
			GlStateManager.pushMatrix();

			GlStateManager.translate(-210F, 0F, 0F);

			this.renderer.draw(this.slide, this.width, this.height, mouseX, mouseY, partialTicks);
			GlStateManager.popMatrix();
		}

		GlStateManager.translate(0, 0, 100F);

		Gui.drawRect(0, this.height - 90, this.width, this.height, Integer.MIN_VALUE);

		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.enableDepth();

		GlStateManager.popMatrix();

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

			double giltaeni = 0;
			double giltaen = 0;
			double giltae = 0;
			double gilt;

			if (value >= 1)
			{
				int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);

				giltaeni = brokenUp[0];
				giltaen = brokenUp[1];
				giltae = brokenUp[2];
				gilt = brokenUp[3];
			}
			else
			{
				gilt = value;
			}

			this.giltSellCount.setText(
					new Text(new TextComponentString(value < 1 ? TextFormatting.GRAY + String.format("%.2f", gilt) : String.valueOf((int) gilt)), 1.0F));
			this.giltaeSellCount.setText(new Text(new TextComponentString(String.valueOf((int) giltae)), 1.0F));
			this.giltaenSellCount.setText(new Text(new TextComponentString(String.valueOf((int) giltaen)), 1.0F));
			this.giltaeniSellCount.setText(new Text(new TextComponentString(String.valueOf((int) giltaeni)), 1.0F));

			this.giltaeniSell.setVisible(giltaeni > 0);
			this.giltaeniSellCount.setVisible(giltaeni > 0);

			this.giltaenSell.setVisible(giltaen > 0 || giltaeni > 0);
			this.giltaenSellCount.setVisible(giltaen > 0 || giltaeni > 0);

			this.giltaeSell.setVisible(giltae > 0 || giltaen > 0 || giltaeni > 0);
			this.giltaeSellCount.setVisible(giltae > 0 || giltaen > 0 || giltaeni > 0);

			boolean hasValue = AetherAPI.content().currency().hasValue(stack);

			this.giltSell.setVisible(hasValue);
			this.giltSellCount.setVisible(hasValue);

			if (!this.giltaeSell.isVisible())
			{
				this.giltSellCount.dim().add(this.coinOffset, RectModifier.ModifierType.POS);
				this.giltSell.dim().add(this.coinOffset, RectModifier.ModifierType.POS);
			}
			else
			{
				this.giltSellCount.dim().remove(this.coinOffset, RectModifier.ModifierType.POS);
				this.giltSell.dim().remove(this.coinOffset, RectModifier.ModifierType.POS);
			}

			if (!this.giltaenSell.isVisible())
			{
				this.giltSellCount.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltSell.dim().add(this.coinOffset, RectModifier.ModifierType.Y);

				this.giltaeSellCount.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltaeSell.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
			}
			else
			{
				this.giltSellCount.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltSell.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);

				this.giltaeSellCount.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltaeSell.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
			}

			this.lastSellStack = stack;
			this.lastSellStackCount = stack.getCount();

			this.sell.setEnabled(value >= 1);
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
			this.buyTitle.setText(new Text(new TextComponentTranslation(this.getSelectedBuy().getItemStack().getDisplayName()), 1.0F));
			this.buyTitle.init();

			String chosenDesc = MathUtil.getRandomElement(this.getSelectedBuy().getUnlocalizedDescriptions(), new Random());

			this.npcDialogue.setText(new TextComponentTranslation(chosenDesc));

			this.prevBuy = this.selectedBuy;

			int value = this.getSelectedBuy().getPrice();
			int[] brokenUp = PlayerCurrencyModule.breakUpCurrency((long) value);

			int giltaeni = brokenUp[0];
			int giltaen = brokenUp[1];
			int giltae = brokenUp[2];
			int gilt = brokenUp[3];

			this.giltBuyCount.setText(new Text(new TextComponentString(String.valueOf(gilt)), 1.0F));
			this.giltaeBuyCount.setText(new Text(new TextComponentString(String.valueOf(giltae)), 1.0F));
			this.giltaenBuyCount.setText(new Text(new TextComponentString(String.valueOf(giltaen)), 1.0F));
			this.giltaeniBuyCount.setText(new Text(new TextComponentString(String.valueOf(giltaeni)), 1.0F));

			this.giltaeniBuy.setVisible(giltaeni > 0);
			this.giltaeniBuyCount.setVisible(giltaeni > 0);

			this.giltaenBuy.setVisible(giltaen > 0 || giltaeni > 0);
			this.giltaenBuyCount.setVisible(giltaen > 0 || giltaeni > 0);

			this.giltaeBuy.setVisible(giltae > 0 || giltaen > 0 || giltaeni > 0);
			this.giltaeBuyCount.setVisible(giltae > 0 || giltaen > 0 || giltaeni > 0);

			this.giltBuy.setVisible(gilt > 0 || giltae > 0 || giltaen > 0 || giltaeni > 0);
			this.giltBuyCount.setVisible(gilt > 0 || giltae > 0 || giltaen > 0 || giltaeni > 0);

			if (!this.giltaeBuy.isVisible())
			{
				this.giltBuyCount.dim().add(this.coinOffset, RectModifier.ModifierType.POS);
				this.giltBuy.dim().add(this.coinOffset, RectModifier.ModifierType.POS);
			}
			else
			{
				this.giltBuyCount.dim().remove(this.coinOffset, RectModifier.ModifierType.POS);
				this.giltBuy.dim().remove(this.coinOffset, RectModifier.ModifierType.POS);
			}

			if (!this.giltaenBuy.isVisible())
			{
				this.giltBuyCount.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltBuy.dim().add(this.coinOffset, RectModifier.ModifierType.Y);

				this.giltaeBuyCount.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltaeBuy.dim().add(this.coinOffset, RectModifier.ModifierType.Y);
			}
			else
			{
				this.giltBuyCount.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltBuy.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);

				this.giltaeBuyCount.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
				this.giltaeBuy.dim().remove(this.coinOffset, RectModifier.ModifierType.Y);
			}
		}

		if (buyCount != prevBuyCount)
		{
			prevBuyCount = buyCount;

			this.buy.getInner().displayString = I18n.format("aether.shop.buy", buyCount);
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

		if (InputHelper.isHovered(this.upArrow))
		{
			this.upArrowHeld = true;
			this.addBuyCount(1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();
		}

		if (InputHelper.isHovered(this.downArrow))
		{
			this.downArrowHeld = true;
			this.addBuyCount(-1);
			this.lastBuyCountChangeTime = System.currentTimeMillis();
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

			NetworkingAether.sendPacketToServer(new PacketShopBuy(index, buyCount));

			int maxAllowedWithHeldStack = this.getSelectedBuy().getItemStack().getMaxStackSize() - this.mc.player.inventory.getItemStack().getCount();

			int amount = Math.min(maxAllowedWithHeldStack, Math.min(buyCount, this.getSelectedBuy().getStock()));

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

			//this.playerAether.getCurrencyModule().add(-this.getSelectedBuy().getPrice());
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
				this.selectedBuy = b.getBuyIndex();

				break;
			}
		}
	}

	@Override
	public void onCurrencyChange(long prevCurrency, long newCurrency)
	{
		this.refreshGilt();
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();

		int scroll = MathHelper.clamp(Mouse.getEventDWheel(), -1, 1);

		if (scroll != 0 && (InputHelper.isHovered(this.buy) || InputHelper.isHovered(this.upArrow) || InputHelper.isHovered(this.downArrow)))
		{
			this.addBuyCount(scroll);
		}
	}
}
