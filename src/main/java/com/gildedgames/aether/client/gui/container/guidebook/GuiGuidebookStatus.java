package com.gildedgames.aether.client.gui.container.guidebook;

import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.List;

public class GuiGuidebookStatus extends AbstractGuidebookPage
{
	private static final ResourceLocation HEALTH_ICON = AetherCore.getResource("textures/gui/guidebook/icons/heart.png");
	private static final ResourceLocation ARMOR_ICON = AetherCore.getResource("textures/gui/guidebook/icons/armor.png");

	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_left.png");

	private static final ResourceLocation RIGHT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_right.png");

	private IGuiCurrencyValue coins;

	public GuiGuidebookStatus(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer, new EmptyContainer());
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		super.drawScreen(mouseX, mouseY, partialTick);

		this.drawPlayer(mouseX, mouseY);

		int health = (int) Minecraft.getMinecraft().player.getHealth();
		int maxHealth = (int) Minecraft.getMinecraft().player.getMaxHealth();
		this.drawString(Minecraft.getMinecraft().fontRenderer, health + "/" + maxHealth,
				(this.width / 2) - 131, (this.height / 2) - 61, 0xFFFFFF);

		int armor = Minecraft.getMinecraft().player.getTotalArmorValue();
		int maxArmor = 20;
		this.drawString(Minecraft.getMinecraft().fontRenderer, armor + "/" + maxArmor,
				(this.width / 2) - 131, (this.height / 2) - 47, 0xFFFFFF);

		IPlayerAether playerAether = PlayerAether.getPlayer(Minecraft.getMinecraft().player);
		int value = (int) playerAether.getModule(PlayerCurrencyModule.class).getCurrencyValue();
		this.coins.setCurrencyValue(value);
	}

	//PLAYER
	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				LEFT_PAGE);

		final GuiText header = new GuiText(Dim2D.build().x(screenX + 72).y(screenY + 13).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.status"), 1.0F));

		GuiTexture heartTexture = new GuiTexture(Dim2D.build().x(screenX + 32).y(screenY + 30).width(9).height(9).flush(), HEALTH_ICON);
		GuiTexture armorTexture = new GuiTexture(Dim2D.build().x(screenX + 32).y(screenY + 44).width(9).height(9).flush(), ARMOR_ICON);

		Pos2D center = InputHelper.getCenter().clone().addX(-81).flush();
		this.coins = new ShopCurrencyGilt()
				.createCurrencyValueGui(Dim2D.build().centerY(true).pos(center).y(this.height).addY(-103).flush());

		return Lists.newArrayList(leftPage,
				header,
				heartTexture,
				armorTexture,
				this.coins);
	}

	//MOA
	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE);

		final GuiText header = new GuiText(Dim2D.build().x(screenX + 73).y(screenY + 13).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.mount"), 1.0F));

		return Lists.newArrayList(rightPage,
				header);
	}

	private void drawPlayer(final int mouseX, final int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GuiInventory.drawEntityOnScreen(
				this.width / 2 - 54,
				this.height / 2, 32, (this.guiLeft + 88) - mouseX, (this.guiTop + 35) - mouseY, this.mc.player);
	}

//	private void drawEquipmentEffects()
//	{
//		final ArrayList<String> label = new ArrayList<>();
//
//		final PlayerEquipmentModule equipment = this.aePlayer.getModule(PlayerEquipmentModule.class);
//		equipment.getActivePools().forEach((pool) -> pool.getInstance().ifPresent(instance -> instance.addInformation(label, TextFormatting.BLUE, TextFormatting.RED)));
//
//		final String compiled = StringUtils.join(label, TextFormatting.RESET + ", ");
//
//		this.mc.fontRenderer.drawString(compiled, this.guiLeft, this.guiTop + 160, 0xFFFFFF, true);
//	}
}
