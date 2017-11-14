package com.gildedgames.orbis.client.gui.util.vanilla;

import com.gildedgames.aether.api.orbis_core.util.ObjectFilter;
import com.gildedgames.orbis.client.gui.util.IGuiFrame;
import com.gildedgames.orbis.client.util.rect.ModDim2D;
import com.gildedgames.orbis.client.util.rect.RectHolder;
import com.gildedgames.orbis.client.util.rect.RectModifier;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuiFrameCreative extends GuiContainerCreativePublic implements IGuiFrame
{

	private final List<IGuiFrame> children = new CopyOnWriteArrayList<>();

	private final ModDim2D dim = new ModDim2D();

	private boolean drawDefaultBackground = true;

	private boolean hasInit, enabled = true, visible = true, hoverEntered = false;

	public GuiFrameCreative(final EntityPlayer player)
	{
		super(player);
	}

	public void setDrawDefaultBackground(final boolean flag)
	{
		this.drawDefaultBackground = flag;
	}

	@Override
	public void clearChildren()
	{
		this.children.clear();
	}

	@Override
	public boolean hasInit()
	{
		return this.hasInit;
	}

	@Override
	public void onHovered()
	{

	}

	@Override
	public void onHoverEnter()
	{

	}

	@Override
	public void onHoverExit()
	{

	}

	@Override
	public void init()
	{

	}

	@Override
	public List<IGuiFrame> seekAllContent()
	{
		return this.children;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(final boolean flag)
	{
		this.visible = flag;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(final boolean flag)
	{
		this.enabled = flag;
	}

	@Override
	public ModDim2D dim()
	{
		return this.dim;
	}

	@Override
	public void updateState()
	{
		for (final IGuiFrame frame : this.children)
		{
			frame.updateState();
		}
	}

	@Override
	public void drawDefaultBackground()
	{

	}

	@Override
	public void publicDrawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		for (final IGuiFrame frame : this.children)
		{
			frame.publicDrawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		}
	}

	@Override
	public void addChildNoMods(final IGuiFrame element)
	{
		this.addChild(element, false);
	}

	@Override
	public void addChild(final IGuiFrame element)
	{
		this.addChild(element, true);
	}

	private void addChild(final IGuiFrame element, final boolean mods)
	{
		final RectHolder gui = ObjectFilter.cast(element, RectHolder.class);
		final RectHolder parentModifier = ObjectFilter.cast(this, RectHolder.class);

		if (mods && gui != null && gui.dim().mod() != null && parentModifier != null)
		{
			gui.dim().add(parentModifier, RectModifier.ModifierType.POS, RectModifier.ModifierType.SCALE);
		}

		if (!element.hasInit() && this.mc != null)
		{
			final GuiScreen g = ObjectFilter.cast(element, GuiScreen.class);

			if (gui != null)
			{
				g.setWorldAndResolution(this.mc, (int) InputHelper.getScreenWidth(), (int) InputHelper.getScreenHeight());
			}
		}

		this.children.add(element);
	}

	@Override
	public void removeChild(final IGuiFrame gui)
	{
		this.children.remove(gui);
	}

	@Override
	public void initGui()
	{
		if (Minecraft.getMinecraft().currentScreen == this)
		{
			super.initGui();
		}

		if (!this.hasInit)
		{
			this.init();

			this.hasInit = true;
		}
	}

	@Override
	public void preDraw()
	{

	}

	@Override
	public void draw()
	{

	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks)
	{
		if (!this.isVisible())
		{
			return;
		}

		if (InputHelper.isHovered(this))
		{
			if (!this.hoverEntered)
			{
				this.onHoverEnter();

				this.hoverEntered = true;
			}

			this.onHovered();
		}
		else if (this.hoverEntered)
		{
			this.onHoverExit();

			this.hoverEntered = false;
		}

		this.preDraw();

		if (this.drawDefaultBackground)
		{
			super.drawDefaultBackground();
		}

		/** Enable for debug rectangle rendering to see dimensions **/
		//Gui.drawRect((int) this.dim().x(), (int) this.dim().y(), (int) this.dim().maxX(), (int) this.dim().maxY(), Integer.MAX_VALUE);

		final float x = this.dim().x();
		final float y = this.dim().y();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();

		GlStateManager.translate(x + this.dim().originX(), y + this.dim().originY(), 0);

		GlStateManager.translate(this.dim().isCenteredX() ? (this.dim().width() / 2) : 0, this.dim().isCenteredY() ? (this.dim().height() / 2) : 0, 0);

		GlStateManager.scale(this.dim().scale(), this.dim().scale(), 0);

		GlStateManager.rotate(this.dim().degrees(), 0.0F, 0.0F, 1.0F);

		GlStateManager.translate(this.dim().isCenteredX() ? -(this.dim().width() / 2) : 0, this.dim().isCenteredY() ? -(this.dim().height() / 2) : 0, 0);

		GlStateManager.translate(-x - this.dim().originX(), -y - this.dim().originY(), 0);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GlStateManager.enableAlpha();

		this.draw();

		for (final IGuiFrame frame : this.children)
		{
			final GuiScreen gui = ObjectFilter.cast(frame, GuiScreen.class);

			if (gui != null)
			{
				gui.drawScreen(mouseX, mouseY, partialTicks);
			}
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GlStateManager.disableAlpha();

		GlStateManager.popMatrix();

		if (Minecraft.getMinecraft().currentScreen == this)
		{
			super.drawScreen(mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public void publicMouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		this.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void publicMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick)
	{
		this.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	public void publicMouseReleased(final int mouseX, final int mouseY, final int state)
	{
		this.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	public void publicKeyTyped(final char typedChar, final int keyCode) throws IOException
	{
		this.keyTyped(typedChar, keyCode);
	}

	@Override
	public void publicActionPerformed(final GuiButton button) throws IOException
	{
		this.actionPerformed(button);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);

		for (final IGuiFrame frame : this.children)
		{
			frame.publicMouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick)
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

		for (final IGuiFrame frame : this.children)
		{
			frame.publicMouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		}
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state)
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.mouseReleased(mouseX, mouseY, state);

		for (final IGuiFrame frame : this.children)
		{
			frame.publicMouseReleased(mouseX, mouseY, state);
		}
	}

	@Override
	protected void handleMouseClick(final Slot slotIn, final int slotId, final int mouseButton, final ClickType type)
	{
		if (!this.isEnabled())
		{
			return;
		}

		if (Minecraft.getMinecraft().currentScreen == this)
		{
			if (this.inventorySlots != null && slotIn != null && slotIn.getSlotIndex() > 54)
			{
				this.inventorySlots.slotClick(slotIn.slotNumber, mouseButton, type, this.mc.player);

				return;
			}

			super.handleMouseClick(slotIn, slotId, mouseButton, type);
		}
	}

	protected void keyTypedInner(final char typedChar, final int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void handleMouseInput() throws IOException
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.handleMouseInput();

		final int i = Mouse.getEventDWheel();

		if (i != 0)
		{
			this.onMouseWheel(i);
		}
	}

	@Override
	public void onMouseWheel(final int state)
	{
		if (!this.isEnabled())
		{
			return;
		}

		for (final IGuiFrame frame : this.children)
		{
			frame.onMouseWheel(state);
		}
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException
	{
		if (!this.isEnabled())
		{
			return;
		}

		if (Minecraft.getMinecraft().currentScreen == this)
		{
			this.keyTypedInner(typedChar, keyCode);
		}

		for (final IGuiFrame frame : this.children)
		{
			frame.publicKeyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		for (final IGuiFrame frame : this.children)
		{
			final GuiScreen gui = ObjectFilter.cast(frame, GuiScreen.class);

			if (gui != null)
			{
				gui.onGuiClosed();
			}
		}
	}

	@Override
	public void updateScreen()
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.updateScreen();

		for (final IGuiFrame frame : this.children)
		{
			final GuiScreen gui = ObjectFilter.cast(frame, GuiScreen.class);

			if (gui != null)
			{
				gui.updateScreen();
			}
		}
	}

	@Override
	public void setWorldAndResolution(final Minecraft mc, final int width, final int height)
	{
		super.setWorldAndResolution(mc, width, height);

		for (final IGuiFrame frame : this.children)
		{
			final GuiScreen gui = ObjectFilter.cast(frame, GuiScreen.class);

			if (gui != null)
			{
				gui.setWorldAndResolution(mc, width, height);
			}
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException
	{
		if (!this.isEnabled())
		{
			return;
		}

		super.actionPerformed(button);

		for (final IGuiFrame frame : this.children)
		{
			frame.publicActionPerformed(button);
		}
	}

	@Override
	public void onResize(final Minecraft mcIn, final int w, final int h)
	{
		super.onResize(mcIn, w, h);

		for (final IGuiFrame frame : this.children)
		{
			final GuiScreen gui = ObjectFilter.cast(frame, GuiScreen.class);

			if (gui != null)
			{
				gui.onResize(mcIn, w, h);
			}
		}
	}
}