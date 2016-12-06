package com.gildedgames.aether.client.ui;

import com.gildedgames.aether.client.ui.common.GuiFrame;
import com.gildedgames.aether.client.ui.common.GuiViewer;
import com.gildedgames.aether.client.ui.minecraft.util.decorators.MinecraftGui;
import com.gildedgames.aether.client.ui.minecraft.viewing.MinecraftGuiViewer;
import com.gildedgames.aether.client.ui.minecraft.viewing.MinecraftGuiWrapper;
import com.gildedgames.aether.client.ui.util.factory.Factory;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UiManager
{

	private static final UiManager INSTANCE = new UiManager();

	private File saveLocation;

	private String currentUniqueSaveName;

	private GuiFrame currentFrame;

	private List<GuiFrame> openingFrames = new ArrayList<>();

	private List<GuiFrame> closingFrames = new ArrayList<>();

	private Map<String, Overlay> overlays = new LinkedHashMap<>();

	private Map<String, RegisteredOverlay> registeredOverlays = new LinkedHashMap<>();

	private UiManager()
	{

	}

	public static UiManager inst()
	{
		return UiManager.INSTANCE;
	}

	public static class RegisteredOverlay extends Overlay
	{

		private Factory<GuiFrame> factory;

		public RegisteredOverlay(Factory<GuiFrame> factory, GuiViewer viewer, RenderGameOverlayEvent.ElementType renderOrder)
		{
			super(null, viewer, renderOrder);

			this.factory = factory;
		}

		public Factory<GuiFrame> getFactory()
		{
			return this.factory;
		}

		public GuiFrame createFrame()
		{
			this.frame = this.factory.create();

			return this.frame;
		}

	}

	public static class Overlay
	{

		protected GuiFrame frame;

		protected GuiViewer viewer;

		protected RenderGameOverlayEvent.ElementType renderOrder;

		public Overlay(GuiFrame frame, GuiViewer viewer, RenderGameOverlayEvent.ElementType renderOrder)
		{
			this.frame = frame;
			this.viewer = viewer;
			this.renderOrder = renderOrder;
		}

		public GuiFrame getFrame()
		{
			return this.frame;
		}

		public GuiViewer getViewer()
		{
			return this.viewer;
		}

		public RenderGameOverlayEvent.ElementType getRenderOrder()
		{
			return this.renderOrder;
		}

		@Override
		public int hashCode()
		{
			return this.frame.hashCode() ^ this.viewer.hashCode() ^ this.renderOrder.hashCode();
		}

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof Overlay))
			{
				return false;
			}

			Overlay overlay = (Overlay) o;

			return this.frame.equals(overlay.getFrame()) && this.viewer.equals(overlay.getViewer()) && this.renderOrder.equals(overlay.getRenderOrder());
		}

	}

	public Collection<RegisteredOverlay> registeredOverlays()
	{
		return Collections.unmodifiableCollection(this.registeredOverlays.values());
	}

	public Collection<Overlay> overlays()
	{
		return Collections.unmodifiableCollection(this.overlays.values());
	}

	public void createRegisteredOverlays()
	{
		for (Map.Entry<String, RegisteredOverlay> entry : this.registeredOverlays.entrySet())
		{
			String uniqueSaveName = entry.getKey();
			RegisteredOverlay overlay = entry.getValue();

			Factory<GuiFrame> factory = overlay.getFactory();
			GuiViewer viewer = overlay.getViewer();
			RenderGameOverlayEvent.ElementType renderOrder = overlay.getRenderOrder();

			this.overlay(uniqueSaveName, factory.create(), viewer, renderOrder);
		}
	}

	public void destroyRegisteredOverlays()
	{
		for (Map.Entry<String, RegisteredOverlay> entry : this.registeredOverlays.entrySet())
		{
			String uniqueSaveName = entry.getKey();

			this.removeOverlay(uniqueSaveName);
		}
	}

	public Overlay getOverlay(String uniqueSaveName)
	{
		return this.overlays.get(uniqueSaveName);
	}

	public void registerOverlay(String uniqueSaveName, Factory<GuiFrame> factory, GuiViewer viewer)
	{
		this.registerOverlay(uniqueSaveName, factory, viewer, null);
	}

	public void registerOverlay(String uniqueSaveName, Factory<GuiFrame> factory, GuiViewer viewer, RenderGameOverlayEvent.ElementType renderOrder)
	{
		this.registeredOverlays.put(uniqueSaveName, new RegisteredOverlay(factory, viewer, renderOrder));
	}

	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		this.overlay(uniqueSaveName, frame, viewer, null);
	}

	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer, RenderGameOverlayEvent.ElementType renderOrder)
	{
		frame.init(viewer.getInputProvider());

		this.overlays.put(uniqueSaveName, new Overlay(frame, viewer, renderOrder));
	}

	public void removeOverlay(String uniqueSaveName)
	{
		this.overlays.remove(uniqueSaveName);
	}

	public boolean hasGuiScreen()
	{
		return Minecraft.getMinecraft().currentScreen != null;
	}

	public GuiScreen getGuiScreen()
	{
		return Minecraft.getMinecraft().currentScreen;
	}

	/**
	 * Returns true if the given screen is a wrapper around the GuiScreenCustom class
	 * given.
	 */
	public boolean containsFrame(GuiScreen screen, Class<? extends GuiFrame>... frames)
	{
		if (screen instanceof MinecraftGuiWrapper)
		{
			GuiFrame wrapped = ((MinecraftGuiWrapper) screen).getFrame();
			for (Class<? extends GuiFrame> frame : frames)
			{
				if (wrapped.getClass().equals(frame))
				{
					return true;
				}
				if (wrapped instanceof MinecraftGui)
				{
					MinecraftGui mcGui = (MinecraftGui) wrapped;
					if (mcGui.getDecoratedElement().getClass().equals(frame))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasFrame()
	{
		return this.getCurrentFrame() != null;
	}

	public GuiFrame getCurrentFrame()
	{
		return this.currentFrame;
	}

	public String getCurrentFrameName()
	{
		return this.currentUniqueSaveName;
	}

	public void open(String uniqueSaveName, GuiFrame frame)
	{
		this.open(uniqueSaveName, frame, MinecraftGuiViewer.instance());
	}

	public void open(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		//this.load(uniqueSaveName, frame, viewer);

		if (frame.ticksOpening() > 0)
		{
			//this.openingFrames.add(frame);

			//return;
		}

		viewer.open(frame);

		this.currentUniqueSaveName = uniqueSaveName;
		this.currentFrame = frame;
	}

	public void close()
	{
		this.close(MinecraftGuiViewer.instance());
	}

	public void close(String uniqueSaveName)
	{
		this.close(uniqueSaveName, MinecraftGuiViewer.instance());
	}

	public void close(String uniqueSaveName, GuiViewer viewer)
	{
		if (this.currentUniqueSaveName != null && this.currentUniqueSaveName.equals(uniqueSaveName))
		{
			this.close(viewer);
		}
	}

	public void close(GuiViewer viewer)
	{
		//this.save(this.currentUniqueSaveName, this.currentFrame, viewer);

		viewer.close(this.currentFrame);

		if (this.currentFrame != null)
		{
			this.currentFrame.onClose(viewer.getInputProvider());
		}

		this.currentFrame = null;
		this.currentUniqueSaveName = null;
	}
}
