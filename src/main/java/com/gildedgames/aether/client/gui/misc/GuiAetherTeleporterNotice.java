package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.api.registrar.BlocksAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTextBox;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiViewer;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;

public class GuiAetherTeleporterNotice extends GuiViewer
{
	private static final ResourceLocation BACKDROP = AetherCore.getResource("textures/gui/notice/backdrop.png");

	private static final ResourceLocation MATRIX = AetherCore.getResource("textures/gui/notice/matrix.png");

	private GuiText title;

	private GuiTextBox body;

	private GuiButtonVanilla close;

	private GuiTexture backdrop, matrix;

	private final ItemStack teleporter;

	private final ItemStack stone;

	private final ItemStack iron_ingot;

	private Pos2D center;

	public GuiAetherTeleporterNotice()
	{
		super(new GuiElement(Dim2D.flush(), false));

		this.allowUserInput = true;

		this.teleporter = new ItemStack(BlocksAether.aether_teleporter);
		this.stone = new ItemStack(Blocks.STONE);
		this.iron_ingot = new ItemStack(Items.IRON_INGOT);
	}

	@Override
	public void build(IGuiContext context)
	{
		this.setDrawDefaultBackground(true);

		this.getViewing().dim().mod().width(this.width).height(this.height).flush();

		this.center = InputHelper.getCenter().clone().addX(-60).flush();

		this.backdrop = new GuiTexture(Dim2D.build().width(200).height(121).pos(this.center).center(true).flush(), BACKDROP);

		this.matrix = new GuiTexture(Dim2D.build().width(111).height(68).pos(this.center).addX(170).center(true).flush(), MATRIX);

		this.title = new GuiText(Dim2D.build().pos(this.center).addX(-67).addY(-83).flush(),
				new Text(new TextComponentTranslation("notice.holdUp"), 2.0F));

		this.body = new GuiTextBox(Dim2D.build().pos(this.center).width(180).height(60).addX(-84).addY(-44).flush(),
				false, new Text(new TextComponentTranslation(
				"notice.body"),
				1.0F));

		this.close = new GuiButtonVanilla(Dim2D.build().width(80).height(20).pos(this.center).addX(-84).addY(27).flush());

		this.close.getInner().displayString = I18n.format("notice.gotcha");

		context.addChildren(this.backdrop, this.matrix, this.body, this.title, this.close);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.enableRescaleNormal();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();

		GlStateManager.translate(0.5F, 0.5F, 0.0F);

		int x = (int) this.center.x() + 140;
		int y = (int) this.center.y() - 8;

		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.teleporter, x + 57, y);

		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.stone, x + 18, y - 18);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.stone, x - 18, y + 18);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.stone, x + 18, y + 18);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.stone, x - 18, y - 18);

		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.iron_ingot, x - 18, y);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.iron_ingot, x + 18, y);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.iron_ingot, x, y - 18);
		Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(this.iron_ingot, x, y + 18);

		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.close))
		{
			this.mc.displayGuiScreen(null);
		}
	}
}
