package com.gildedgames.aether.common.dungeons;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiViewerNoContainer;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Rect;
import com.gildedgames.orbis.lib.core.world_objects.BlueprintRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Random;

public class GuiDungeonViewer extends GuiViewerNoContainer
{
	private static final ResourceLocation TEXTURE_BASE = AetherCore.getResource("textures/gui/guidebook/guidebook_base.png");

	private IDungeon dungeon;

	private IDungeonGenerator generator;

	public GuiDungeonViewer(IDungeonGenerator generator, IDungeon dungeon)
	{
		super(new GuiElement(Dim2D.flush(), false));

		this.generator = generator;
		this.dungeon = dungeon;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}

	@Override
	public void build(IGuiContext context) {
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


		for (DungeonNode room : this.dungeon.rooms()) {
			AABB aabb = room.getAABB();
			GlStateManager.pushMatrix();

			double scale = 2.0;

			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_BASE);
			GlStateManager.translate(aabb.minX + (this.width / 2), aabb.minY + (this.height / 2), 0);
			//GlStateManager.scale(scale, scale, scale);
			GuiTexture.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, aabb.getWidth(), aabb.getHeight(), aabb.getWidth(), aabb.getHeight());

			GlStateManager.popMatrix();
		}

		boolean finished = this.generator.step(DungeonGenStep.PUSH_ROOMS_APART, this.dungeon);

//		if (finished) {
//			IDungeonGenerator gen = new DungeonGenerator();
//			this.dungeon = gen.generate(DungeonViewer.def, new Random());
//		}
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		IDungeonGenerator gen = new DungeonGenerator();
		this.dungeon = gen.generate(DungeonViewer.def, new Random());
	}

}
