package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.util.GuiFrame;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTextBox;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.vanilla.GuiButtonVanilla;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;

public class GuiAetherTeleporterNotice extends GuiFrame
{
	private static final ResourceLocation BACKDROP = AetherCore.getResource("textures/gui/notice/backdrop.png");

	private static final ResourceLocation MATRIX = AetherCore.getResource("textures/gui/notice/matrix.png");

	private GuiText title;

	private GuiTextBox body;

	private GuiButtonVanilla close;

	private GuiTexture backdrop, matrix;

	private ItemStack teleporter, stone, iron_ingot;

	private Pos2D center;

	public GuiAetherTeleporterNotice()
	{
		this.allowUserInput = true;

		this.teleporter = new ItemStack(BlocksAether.aether_teleporter);
		this.stone = new ItemStack(Blocks.STONE);
		this.iron_ingot = new ItemStack(Items.IRON_INGOT);
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	public void init()
	{
		this.setDrawDefaultBackground(true);

		this.dim().mod().width(this.width).height(this.height).flush();

		this.center = InputHelper.getCenter().clone().addX(-60).flush();

		this.backdrop = new GuiTexture(Dim2D.build().width(200).height(121).pos(this.center).center(true).flush(), BACKDROP);

		this.matrix = new GuiTexture(Dim2D.build().width(111).height(68).pos(this.center).addX(170).center(true).flush(), MATRIX);

		this.title = new GuiText(Dim2D.build().pos(this.center).addX(-67).addY(-83).flush(),
				new Text(new TextComponentString("Woah! Hold up"), 2.0F));

		this.body = new GuiTextBox(Dim2D.build().pos(this.center).width(180).height(60).addX(-84).addY(-44).flush(),
				false, new Text(new TextComponentString(
				"You must be a returning player." + System.lineSeparator() + System.lineSeparator()
						+ "In Aether II, travelling to the Aether is no longer done with a Glowstone Portal." + System.lineSeparator() + System.lineSeparator()
						+ "Instead, craft an Aether Teleporter block."),
				1.0F));

		this.close = new GuiButtonVanilla(Dim2D.build().width(80).height(20).pos(this.center).addX(-84).addY(27).flush());

		this.close.getInner().displayString = "Gotcha!";

		this.addChildren(this.backdrop, this.matrix, this.body, this.title, this.close);
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
	public void draw()
	{

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
