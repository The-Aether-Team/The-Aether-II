package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.dialog.*;
import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.client.gui.dialog.GuiDialogViewer;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.dialog.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerDialogModule extends PlayerAetherModule implements IDialogController
{
	private final Set<IDialogChangeListener> listeners = new HashSet<>();

	private ISceneInstance sceneInstance;

	private String lastNodeId;

	private EntityCharacter talkingEntity;

	private Map<String, Boolean> conditionsMet;

	public PlayerDialogModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void setConditionsMetData(Map<String, Boolean> conditionsMet)
	{
		this.conditionsMet = conditionsMet;

		if (this.sceneInstance != null)
		{
			this.sceneInstance.setConditionsMet(conditionsMet);
		}
	}

	@Override
	public EntityPlayer getDialogPlayer()
	{
		return this.getEntity();
	}

	protected void updateListeners()
	{
		for (final IDialogChangeListener listener : this.listeners)
		{
			listener.onDialogChanged();
		}
	}

	@Nullable
	@Override
	public EntityCharacter getTalkingCharacter()
	{
		return this.talkingEntity;
	}

	@Override
	public void setTalkingEntity(final EntityCharacter entity)
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
			this.openSceneClient(path, scene, this.conditionsMet);
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
		return this.sceneInstance != null ? this.sceneInstance.getScene() : null;
	}

	@Override
	public ISceneInstance getCurrentSceneInstance()
	{
		return this.sceneInstance;
	}

	@SideOnly(Side.CLIENT)
	private void openSceneClient(final ResourceLocation res, final IDialogScene scene, Map<String, Boolean> conditionsMet)
	{
		this.sceneInstance = new SceneInstance(this, scene, conditionsMet);

		Minecraft.getMinecraft().displayGuiScreen(new GuiDialogViewer(Minecraft.getMinecraft().player, this, this.sceneInstance));
	}

	private void openSceneServer(final ResourceLocation res, final IDialogScene scene)
	{
		this.sceneInstance = new SceneInstance(this, scene);

		NetworkingAether.sendPacketToPlayer(new PacketOpenDialog(res, scene.getStartingNode().getIdentifier(), this.sceneInstance.getConditionsMet()),
				(EntityPlayerMP) this.getPlayer().getEntity());
	}

	@SideOnly(Side.CLIENT)
	public void navigateNodeClient(String nodeId)
	{
		if (this.sceneInstance.getNode() != null)
		{
			this.lastNodeId = this.sceneInstance.getNode().getIdentifier();
		}

		this.sceneInstance.navigate(nodeId);
	}

	@SideOnly(Side.CLIENT)
	public void navigateBackClient()
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
	public void navigateNode(final String nodeId)
	{
		if (!this.getWorld().isRemote)
		{
			if (this.sceneInstance.getNode() != null)
			{
				this.lastNodeId = this.sceneInstance.getNode().getIdentifier();
			}

			this.sceneInstance.navigate(nodeId);

			NetworkingAether.sendPacketToPlayer(new PacketNavigateNode(nodeId), (EntityPlayerMP) this.getDialogPlayer());
		}
	}

	@Override
	public void navigateBack()
	{
		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketNavigateBack(), (EntityPlayerMP) this.getDialogPlayer());

			if (this.lastNodeId != null)
			{
				this.sceneInstance.navigate(this.lastNodeId);

				while (!this.isNodeFinished())
				{
					this.advance();
				}
			}
		}
	}

	@Override
	public boolean conditionsMet(IDialogButton button)
	{
		if (this.getWorld().isRemote)
		{
			if (this.sceneInstance == null)
			{
				throw new NullPointerException("Scene instance is null in activateButton()");
			}

			if (!this.sceneInstance.getConditionsMet().containsKey(button.getLabel()))
			{
				return false;
			}

			return this.sceneInstance.getConditionsMet().get(button.getLabel());
		}

		boolean flag = false;

		for (IDialogCondition condition : button.getOrConditions())
		{
			if (condition.isMet(this))
			{
				flag = true;
				break;
			}
		}

		for (IDialogCondition condition : button.getConditions())
		{
			if (!condition.isMet(this))
			{
				flag = false;
				break;
			}
			else
			{
				flag = true;
			}
		}

		return flag || (button.getConditions().isEmpty() && button.getOrConditions().isEmpty());
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

		if (!this.conditionsMet(button))
		{
			return;
		}

		if (!this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToPlayer(new PacketActivateButton(button.getLabel()), (EntityPlayerMP) this.getEntity());

			final Collection<IDialogAction> actions = button.getActions();

			for (final IDialogAction action : actions)
			{
				action.performAction(this);
			}
		}
	}

	public void advanceClient()
	{
		if (this.sceneInstance != null)
		{
			this.sceneInstance.forwards();
		}
	}

	@Override
	public void advance()
	{
		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketAdvance());
		}
		else
		{
			NetworkingAether.sendPacketToPlayer(new PacketAdvance(), (EntityPlayerMP) this.getDialogPlayer());

			if (this.sceneInstance != null)
			{
				this.sceneInstance.forwards();
			}
		}
	}

	@Override
	public IDialogNode getCurrentNode()
	{
		return this.sceneInstance.getNode();
	}

	@Override
	public IDialogLine getCurrentLine()
	{
		return this.sceneInstance.getLine();
	}

	@Override
	public boolean isNodeFinished()
	{
		if (this.sceneInstance == null)
		{
			return false;
		}

		return this.sceneInstance.isDoneReading();
	}

	@Override
	public void closeScene(ISceneInstance sceneInstance)
	{
		if (this.sceneInstance != sceneInstance)
		{
			return;
		}

		for (final IDialogChangeListener listener : this.listeners)
		{
			listener.beforeSceneCloses();
		}

		this.listeners.clear();

		this.sceneInstance = null;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketCloseScene());
		}
	}

	public void closeCurrentScene()
	{
		this.closeScene(this.getCurrentSceneInstance());
	}
}
