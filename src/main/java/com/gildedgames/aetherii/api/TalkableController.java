package com.gildedgames.aetherii.api;

import com.gildedgames.aetherii.api.dialog.IDialogAction;
import com.gildedgames.aetherii.api.dialog.IDialogButton;
import com.gildedgames.aetherii.api.dialog.IDialogChangeListener;
import com.gildedgames.aetherii.api.dialog.IDialogCondition;
import com.gildedgames.aetherii.api.dialog.IDialogLine;
import com.gildedgames.aetherii.api.dialog.IDialogNode;
import com.gildedgames.aetherii.api.dialog.IDialogScene;
import com.gildedgames.aetherii.api.dialog.scene.ISceneInstance;
import com.gildedgames.aetherii.api.entity.NormalTalker;
import com.gildedgames.aetherii.api.entity.TalkableMob;
import com.gildedgames.aetherii.client.screen.DialogScreen;
import com.gildedgames.aetherii.register.ContentRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TalkableController implements NormalTalker {
	private ISceneInstance sceneInstance;
	private final Set<IDialogChangeListener> listeners = new HashSet<>();
	private Level level;

	protected final TalkableMob talkableMob;

	public TalkableController(TalkableMob talkableMob) {
		this.talkableMob = talkableMob;
	}

	public void activateButton(final IDialogButton button) {
		if (talkableMob instanceof LivingEntity) {
			// Make sure this node actually contains the button
			Validate.isTrue(this.sceneInstance.getNode().getButtons().contains(button));

			if (((LivingEntity) talkableMob).level.isClientSide()) {
				//Networking.sendPacketToServer(new PacketActivateButton(button.getLabel()));

				final Collection<IDialogAction> actions = button.getActions();

				for (final IDialogAction action : actions) {
					action.performAction(this);
				}
			}

			if (!this.conditionsMet(button)) {
				return;
			}

			if (!((LivingEntity) talkableMob).level.isClientSide()) {
				//Networking.sendPacketToPlayer(new PacketActivateButton(button.getLabel()), (EntityPlayerMP) this.getEntity());

				final Collection<IDialogAction> actions = button.getActions();

				for (final IDialogAction action : actions) {
					action.performAction(this);
				}
			}
		}
	}

	public boolean conditionsMet(IDialogButton button) {
		/*if(talkableMob instanceof LivingEntity) {
			if (((LivingEntity) talkableMob).level.isClientSide()) {
				if (this.sceneInstance == null) {
					throw new NullPointerException("Scene instance is null in activateButton()");
				}

				if(this.talkableMob.getTalkingUUID().isEmpty()){
					return false;
				}

				if (!this.sceneInstance.getConditionsMet().containsKey(button.getLabel())) {
					return false;
				}

				return this.sceneInstance.getConditionsMet().get(button.getLabel()) == this.talkableMob.getTalkingUUID().get();
			}
		}*/

		boolean flag = false;

		for (IDialogCondition condition : button.getOrConditions()) {
			if (condition.isMet(this)) {
				flag = true;
				break;
			}
		}

		for (IDialogCondition condition : button.getConditions()) {
			if (!condition.isMet(this)) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}

		return flag || (button.getConditions().isEmpty() && button.getOrConditions().isEmpty());
	}

	protected void updateListeners() {
		for (final IDialogChangeListener listener : this.listeners) {
			listener.onDialogChanged();
		}
	}

	public void advance() {
		if (this.getLevel().isClientSide()) {
			//Networking.sendPacketToServer(new PacketAdvance());
			if (this.sceneInstance != null) {
				this.sceneInstance.forwards();
			}
		} else {
			//Networking.sendPacketToPlayer(new PacketAdvance(), (EntityPlayerMP) this.getDialogPlayer());

			if (this.sceneInstance != null) {
				this.sceneInstance.forwards();
			}
		}
	}

	public IDialogNode getCurrentNode() {
		return this.sceneInstance.getNode();
	}

	public IDialogLine getCurrentLine() {
		return this.sceneInstance.getLine();
	}

	public boolean isNodeFinished() {
		if (this.sceneInstance == null) {
			return false;
		}

		return this.sceneInstance.isDoneReading();
	}

	@Override
	public IDialogScene getCurrentScene() {
		return this.sceneInstance != null ? this.sceneInstance.getScene() : null;
	}

	@Override
	public ISceneInstance getCurrentSceneInstance() {
		return this.sceneInstance;
	}


	public void openScene(Player player, TalkableMob livingEntity, final ResourceLocation path, String startingNodeId) {
		final IDialogScene scene = ContentRegistry.getDialogManager().getScene(path).orElseThrow(() ->
				new IllegalArgumentException("Couldn't getByte scene " + path));

		scene.setStartingNode(startingNodeId);

		if (player.level.isClientSide()) {
			this.openSceneClient(player, livingEntity, path, scene);
		} else if (!player.level.isClientSide()) {
			this.openSceneServer(player, livingEntity, path, scene);
		}

		this.updateListeners();
	}

	@OnlyIn(Dist.CLIENT)
	private void openSceneClient(Player player, TalkableMob livingEntity, final ResourceLocation res, final IDialogScene scene) {
		this.sceneInstance = new SceneInstance(this, scene);

		Minecraft.getInstance().setScreen(new DialogScreen(player, livingEntity));
	}

	private void openSceneServer(Player player, TalkableMob livingEntity, final ResourceLocation res, final IDialogScene scene) {
		this.sceneInstance = new SceneInstance(this, scene);

		/*if(livingEntity instanceof LivingEntity) {
			OpenDialogMessage message = new OpenDialogMessage(player.getId(), ((LivingEntity) livingEntity).getId(), res, scene.getStartingNode().getIdentifier());
			AetherII.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity) livingEntity), message);
		}*/
	}

	@Override
	public void addListener(final IDialogChangeListener listener) {
		this.listeners.add(listener);
	}


	@Override
	public ResourceLocation getTalker() {
		return this.talkableMob.getTalker();
	}

	public TalkableMob getTalkableMob() {
		return talkableMob;
	}

	@Nullable
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public ISceneInstance getSceneInstance() {
		return sceneInstance;
	}
}
