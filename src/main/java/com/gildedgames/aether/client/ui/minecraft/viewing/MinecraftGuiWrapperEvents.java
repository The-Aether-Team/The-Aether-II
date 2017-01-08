package com.gildedgames.aether.client.ui.minecraft.viewing;

import com.gildedgames.aether.client.ui.UiManager;
import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.GuiViewer;
import com.gildedgames.aether.client.ui.data.TickInfo;
import com.gildedgames.aether.client.ui.input.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class MinecraftGuiWrapperEvents implements TickInfo
{

	protected Minecraft mc = Minecraft.getMinecraft();

	protected int ticks;

	private double width, height, scaleFactor, touchValue, eventButton;

	private long lastMouseEvent;

	private boolean worldStarted;

	public void handleInput()
	{
		if (this.mc.theWorld != null)
		{
			return;
		}

		if (Mouse.isCreated())
		{
			while (Mouse.next())
			{
				if (this.mc.currentScreen != null)
				{
					if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent.Pre(this.mc.currentScreen)))
					{
						continue;
					}

					try
					{
						this.mc.currentScreen.handleMouseInput();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}

					net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent.Post(this.mc.currentScreen));
				}

				for (UiManager.Overlay overlay : UiManager.inst().overlays())
				{
					GuiFrame frame = overlay.getFrame();
					GuiViewer viewer = overlay.getViewer();

					this.handleMouseInput(frame, viewer);
				}
			}
		}

		if (Keyboard.isCreated())
		{
			for (UiManager.Overlay overlay : UiManager.inst().overlays())
			{
				GuiFrame frame = overlay.getFrame();
				GuiViewer viewer = overlay.getViewer();

				this.handleKeyboardInput(frame, viewer);
			}
		}
	}

	public void handleMouseInput(GuiFrame frame, GuiViewer viewer)
	{
		if (!frame.isEnabled())
		{
			return;
		}

		double i = Mouse.getEventX() * this.width / this.mc.displayWidth;
		double j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		int button = Mouse.getEventButton();

		if (Mouse.getEventButtonState())
		{
			if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0)
			{
				return;
			}

			this.eventButton = button;
			this.lastMouseEvent = Minecraft.getSystemTime();

			MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(button), ButtonState.PRESS), new MouseInput(MouseButton.fromIndex(button), ButtonState.PRESS));

			frame.onMouseInput(pool, viewer.getInputProvider());
		}
		else if (button != -1)
		{
			if (this.mc.gameSettings.touchscreen && --this.touchValue > 0)
			{
				return;
			}

			this.eventButton = -1;

			MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(button), ButtonState.RELEASE));

			frame.onMouseInput(pool, viewer.getInputProvider());
		}
		else if (this.eventButton != -1 && this.lastMouseEvent > 0L)
		{
			long l = Minecraft.getSystemTime() - this.lastMouseEvent;

			MouseInputPool pool = new MouseInputPool(new MouseInput(MouseButton.fromIndex(button), ButtonState.PRESS, MouseMotion.MOVING));

			frame.onMouseInput(pool, viewer.getInputProvider());
		}

		int scrollDifference = Mouse.getDWheel();

		if (scrollDifference != 0)
		{
			frame.onMouseScroll(scrollDifference, viewer.getInputProvider());
		}
	}

	/**
	 * Handles keyboard input.
	 */
	public void handleKeyboardInput(GuiFrame frame, GuiViewer viewer)
	{
		if (Keyboard.getEventKeyState())
		{
			KeyboardInputPool pool = new KeyboardInputPool(new KeyboardInput(Keyboard.getEventKey(), Keyboard.getEventCharacter(), ButtonState.PRESS));

			frame.onKeyboardInput(pool, viewer.getInputProvider());
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		this.renderOverlays(event.getType());
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.RenderTickEvent event)
	{
		if (event.phase == TickEvent.Phase.END)
		{
			this.renderOverlays(null);
		}
	}

	@SubscribeEvent
	public void tickStartClient(TickEvent.ClientTickEvent event)
	{
		if (event.phase == TickEvent.Phase.START)
		{
			this.ticks++;

			if (this.mc.currentScreen instanceof MinecraftGuiWrapper)
			{
				MinecraftGuiWrapper viewer = (MinecraftGuiWrapper) this.mc.currentScreen;

				viewer.tick(this);
			}

			for (UiManager.Overlay overlay : UiManager.inst().overlays())
			{
				GuiFrame frame = overlay.getFrame();
				GuiViewer viewer = overlay.getViewer();

				InputProvider input = viewer.getInputProvider();

				if (this.width != input.getScreenWidth() || this.height != input.getScreenHeight()
						|| this.scaleFactor != input.getScaleFactor())
				{
					viewer.getInputProvider().refreshResolution();

					this.width = input.getScreenWidth();
					this.height = input.getScreenHeight();
					this.scaleFactor = input.getScaleFactor();

					frame.onResolutionChange(input);
				}

				frame.tick(viewer.getTickInfo(), viewer.getInputProvider());
			}

			this.handleInput();
		}
	}

	private void renderOverlays(RenderGameOverlayEvent.ElementType desiredOrder)
	{
		if (!this.worldStarted)
		{
			this.worldStarted = true;

			UiManager.inst().createRegisteredOverlays();
		}

		for (UiManager.Overlay overlay : UiManager.inst().overlays())
		{
			GuiFrame frame = overlay.getFrame();
			GuiViewer viewer = overlay.getViewer();
			RenderGameOverlayEvent.ElementType renderOrder = overlay.getRenderOrder();

			if (renderOrder == desiredOrder)
			{
				GL11.glPushMatrix();

				GL11.glTranslatef(0.0F, 0.0F, 0.0F);

				frame.draw(viewer.getGraphics(), viewer.getInputProvider());

				GL11.glPopMatrix();
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onGuiOpen(GuiOpenEvent event)
	{
		if (event.getGui() instanceof GuiMainMenu)
		{
			this.worldStarted = false;

			//UiManager.inst().destroyRegisteredOverlays();
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void renderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.getType() == ElementType.TEXT)
		{
			//this.renderOverlays(RenderOrder.PRE);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void renderGameOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if (event.getType() == ElementType.TEXT)
		{
			//this.renderOverlays(RenderOrder.POST);
		}
	}

	@Override
	public int getTotalTicks()
	{
		return this.ticks;
	}

}
