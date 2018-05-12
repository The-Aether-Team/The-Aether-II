package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.client.gui.dialog.GuiDialogViewer;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.dialog.PacketActivateButton;
import com.gildedgames.aether.common.network.packets.dialog.PacketCloseDialog;
import com.gildedgames.aether.common.network.packets.dialog.PacketOpenDialog;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerDialogModule extends PlayerAetherModule implements IDialogController
{
	private final Set<IDialogChangeListener> listeners = new HashSet<>();

	private SceneInstance sceneInstance;

	private String lastNodeId;

	private Entity talkingEntity;

	public PlayerDialogModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{

	}

	@Override
	public void onUpdate()
	{
		
	}

	private void updateListeners()
	{
		for (final IDialogChangeListener listener : this.listeners)
		{
			listener.onDialogChanged();
		}
	}

	@Nullable
	@Override
	public Entity getTalkingEntity()
	{
		return this.talkingEntity;
	}

	@Override
	public void setTalkingEntity(final Entity entity)
	{
		this.talkingEntity = entity;
	}

	@Override
	public void addListener(final IDialogChangeListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public void openScene(final ResourceLocation path, String startingNodeId)
	{
		final IDialogScene scene = AetherAPI.content().dialog().getScene(path).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte scene " + path));

		scene.setStartingNode(startingNodeId);

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
	private void openSceneClient(final ResourceLocation res, final IDialogScene scene)
	{
		this.sceneInstance = new SceneInstance(this, scene);

		Minecraft.getMinecraft().displayGuiScreen(new GuiDialogViewer(Minecraft.getMinecraft().player, this));
	}

	private void openSceneServer(final ResourceLocation res, final IDialogScene scene)
	{
		this.sceneInstance = new SceneInstance(this, scene);

		NetworkingAether.sendPacketToPlayer(new PacketOpenDialog(res, scene.getStartingNode().getIdentifier()), (EntityPlayerMP) this.getPlayer().getEntity());
	}

	/**
	 * Called from the client when the dialog scene has been closed. Notifies
	 * the server to also close it's end.
	 */
	@SideOnly(Side.CLIENT)
	private void closeSceneClient()
	{
		NetworkingAether.sendPacketToServer(new PacketCloseDialog());
	}

	@Override
	public void navigateNode(final String nodeId)
	{
		if (this.sceneInstance.getNode() != null)
		{
			this.lastNodeId = this.sceneInstance.getNode().getIdentifier();
		}

		this.sceneInstance.navigate(nodeId);
	}

	@Override
	public void navigateBack()
	{
		if (this.lastNodeId != null)
		{
			this.sceneInstance.navigate(this.lastNodeId);

			while (!this.isNodeFinished())
			{
				this.advance();
			}
		}
	}

	@Override
	public void activateButton(final IDialogButton button)
	{
		// Make sure this node actually contains the button
		Validate.isTrue(this.sceneInstance.getNode().getButtons().contains(button));

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketActivateButton(button.getLabel()));
		}

		final Collection<IDialogAction> actions = button.getActions();

		for (final IDialogAction action : actions)
		{
			action.performAction(this);
		}
	}

	@Override
	public void advance()
	{
		if (this.sceneInstance != null)
		{
			this.sceneInstance.forwards();
		}
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

		for (final IDialogChangeListener listener : this.listeners)
		{
			listener.onSceneClosed();
		}

		this.listeners.clear();
	}

	@Override
	public void write(final NBTTagCompound compound)
	{

	}

	@Override
	public void read(final NBTTagCompound compound)
	{

	}

	private class SceneInstance
	{
		private final PlayerDialogModule controller;

		private final IDialogScene scene;

		private IDialogNode node;

		private List<IDialogLine> lines;

		private Collection<IDialogButton> buttons;

		private Collection<IDialogAction> endActions;

		private int index;

		private SceneInstance(final PlayerDialogModule controller, final IDialogScene scene)
		{
			this.controller = controller;
			this.scene = scene;

			this.setNode(this.scene.getStartingNode());
		}

		private IDialogNode getNode()
		{
			return this.node;
		}

		private void setNode(final IDialogNode node)
		{
			Validate.notNull(node);

			this.node = node;

			this.lines = node.getLines();
			this.buttons = node.getButtons();
			this.endActions = node.getEndActions();

			this.index = 0;

			this.controller.updateListeners();
		}

		private IDialogLine getLine()
		{
			return this.lines.get(this.index);
		}

		private boolean isDoneReading()
		{
			return this.index >= this.lines.size() - 1;
		}

		private void navigate(final String nodeId)
		{
			final Optional<IDialogNode> node = this.scene.getNode(nodeId);

			this.setNode(node.orElseThrow(() ->
					new IllegalArgumentException("Node " + nodeId + " doesn't exist")));
		}

		private void forwards()
		{
			if (this.isDoneReading())
			{
				if (this.buttons.size() <= 0)
				{
					for (final IDialogAction action : this.endActions)
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
