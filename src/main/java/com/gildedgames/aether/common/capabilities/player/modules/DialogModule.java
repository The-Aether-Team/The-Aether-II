package com.gildedgames.aether.common.capabilities.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.client.gui.dialog.GuiDialogViewer;
import com.gildedgames.aether.common.capabilities.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.dialog.DialogClosePacket;
import com.gildedgames.aether.common.network.packets.dialog.DialogOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class DialogModule extends PlayerAetherModule implements IDialogController
{
	private final Set<IDialogChangeListener> listeners = new HashSet<>();

	private SceneInstance sceneInstance;

	public DialogModule(PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void onUpdate()
	{

	}

	private void updateListeners()
	{
		for (IDialogChangeListener listener : this.listeners)
		{
			listener.onDialogChanged();
		}
	}

	@Override
	public void addListener(IDialogChangeListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(IDialogChangeListener listener)
	{
		this.listeners.remove(listener);
	}

	@Override
	public void openScene(ResourceLocation path)
	{
		IDialogScene scene = AetherAPI.content().dialog().getScene(path).orElseThrow(() ->
				new IllegalArgumentException("Couldn't get scene " + path));

		if (this.getPlayer().getEntity().world.isRemote)
		{
			this.openSceneClient(path, scene);
		}
		else
		{
			this.openSceneServer(path, scene);
		}

		this.updateListeners();
	}

	@Override
	public IDialogScene getCurrentScene()
	{
		return this.sceneInstance != null ? this.sceneInstance.scene : null;
	}


	@SideOnly(Side.CLIENT)
	private void openSceneClient(ResourceLocation res, IDialogScene scene)
	{
		this.sceneInstance = new SceneInstance(this, scene);

		Minecraft.getMinecraft().displayGuiScreen(new GuiDialogViewer(this));
	}

	private void openSceneServer(ResourceLocation res, IDialogScene scene)
	{
		this.sceneInstance = new SceneInstance(this, scene);

		NetworkingAether.sendPacketToPlayer(new DialogOpenPacket(res), (EntityPlayerMP) this.getPlayer().getEntity());
	}

	/**
	 * Called from the client when the dialog scene has been closed. Notifies
	 * the server to also close it's end.
	 */
	@SideOnly(Side.CLIENT)
	private void closeSceneClient()
	{
		NetworkingAether.sendPacketToServer(new DialogClosePacket());
	}

	@Override
	public void navigateNode(String nodeId)
	{
		this.sceneInstance.navigate(nodeId);
	}

	@Override
	public void activateButton(IDialogButton button)
	{
		// Make sure this node actually contains the button
		Validate.isTrue(this.sceneInstance.getNode().getButtons().contains(button));

		IDialogAction action = button.getAction();
		action.performAction(this);
	}

	@Override
	public void advance()
	{
		this.sceneInstance.forwards();
	}

	@Override
	public IDialogNode getCurrentNode()
	{
		return this.sceneInstance.node;
	}

	@Override
	public IDialogLine getCurrentLine()
	{
		return this.sceneInstance.getLine();
	}

	@Override
	public boolean isNodeFinished()
	{
		return this.sceneInstance.isDoneReading();
	}

	@Override
	public void closeScene()
	{
		this.sceneInstance = null;

		if (this.getPlayer().getEntity().world.isRemote)
		{
			this.closeSceneClient();
		}

		for (IDialogChangeListener listener : this.listeners)
		{
			listener.onSceneClosed();
		}
	}

	@Override
	public void write(NBTTagCompound compound)
	{

	}

	@Override
	public void read(NBTTagCompound compound)
	{

	}

	private class SceneInstance
	{
		private final DialogModule controller;

		private final IDialogScene scene;

		private IDialogNode node;

		private List<IDialogLine> lines;

		private Collection<IDialogButton> buttons;

		private Collection<IDialogAction> endActions;

		private int index;

		private SceneInstance(DialogModule controller, IDialogScene scene)
		{
			this.controller = controller;
			this.scene = scene;

			this.setNode(this.scene.getStartingNode());
		}

		private void setNode(IDialogNode node)
		{
			Validate.notNull(node);

			this.node = node;

			this.lines = node.getLines();
			this.buttons = node.getButtons();
			this.endActions = node.getEndActions();

			this.index = 0;

			this.controller.updateListeners();
		}

		private IDialogNode getNode()
		{
			return this.node;
		}

		private IDialogLine getLine()
		{
			return this.lines.get(this.index);
		}

		private boolean isDoneReading()
		{
			return this.index >= this.lines.size() - 1;
		}

		private void navigate(String nodeId)
		{
			Optional<IDialogNode> node = this.scene.getNode(nodeId);

			this.setNode(node.orElseThrow(() ->
					new IllegalArgumentException("Node " + nodeId + " doesn't exist")));
		}

		private void forwards()
		{
			if (this.isDoneReading())
			{
				if (this.buttons.size() <= 0)
				{
					for (IDialogAction action : this.endActions)
					{
						action.performAction(this.controller);
					}

					this.setNode(this.scene.getStartingNode());
				}
			}
			else
			{
				this.index++;

				this.controller.updateListeners();
			}
		}
	}
}
