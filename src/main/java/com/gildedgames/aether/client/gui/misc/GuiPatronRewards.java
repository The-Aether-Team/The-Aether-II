package com.gildedgames.aether.client.gui.misc;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.patron.IPatronReward;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerPatronRewards;
import com.gildedgames.aether.common.patron.PatronChoices;
import com.gildedgames.aether.common.patron.PatronRewards;
import com.gildedgames.aether.common.patron.armor.PatronRewardArmor;
import com.gildedgames.orbis_api.client.gui.data.Text;
import com.gildedgames.orbis_api.client.gui.data.list.IListNavigator;
import com.gildedgames.orbis_api.client.gui.data.list.IListNavigatorListener;
import com.gildedgames.orbis_api.client.gui.data.list.ListNavigator;
import com.gildedgames.orbis_api.client.gui.util.GuiAbstractButton;
import com.gildedgames.orbis_api.client.gui.util.GuiText;
import com.gildedgames.orbis_api.client.gui.util.GuiTexture;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis_api.client.gui.util.gui_library.GuiViewer;
import com.gildedgames.orbis_api.client.gui.util.gui_library.IGuiContext;
import com.gildedgames.orbis_api.client.gui.util.list.GuiListViewer;
import com.gildedgames.orbis_api.client.rect.Dim2D;
import com.gildedgames.orbis_api.client.rect.Pos2D;
import com.gildedgames.orbis_api.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.io.IOException;
import java.util.Comparator;

public class GuiPatronRewards extends GuiViewer implements IListNavigatorListener<IPatronReward>
{
	private static final ResourceLocation BACKDROP = AetherCore.getResource("textures/gui/patron/backdrop.png");

	private static final ResourceLocation QUESTION = AetherCore.getResource("textures/gui/patron/reward_question.png");

	private static final ResourceLocation QUESTION_HOVERED = AetherCore.getResource("textures/gui/patron/reward_question_hovered.png");

	private static final ResourceLocation USE = AetherCore.getResource("textures/gui/patron/reward_use.png");

	private static final ResourceLocation USE_HOVERED = AetherCore.getResource("textures/gui/patron/reward_use_hovered.png");

	private GuiListViewer<IPatronReward, GuiPatronRewardEntry> rewardViewer;

	private final IListNavigator<IPatronReward> rewards = new ListNavigator<>();

	private GuiText patronRewardText;

	private GuiAbstractButton use, question;

	private GuiTexture backdrop;

	private IPatronReward selected;

	private final PatronRewardArmor defaultArmor = new PatronRewardArmor("aether.reward.none.name", PatronRewards.armorIcon("none"), null, null, null, (d) -> true);

	private GuiText useText;

	private final PlayerPatronRewards patronRewards;

	private final Text useString = new Text(new TextComponentTranslation("gui.aether.patreon.rewards.button.use"), 1.0F);

	private final Text lockedString = new Text(new TextComponentTranslation("gui.aether.patreon.rewards.button.locked"), 1.0F);

	public GuiPatronRewards()
	{
		super(new GuiElement(Dim2D.flush(), false));

		PlayerAether playerAether = PlayerAether.getPlayer(Minecraft.getMinecraft().player);

		this.patronRewards = playerAether.getPatronRewardsModule();

		this.allowUserInput = true;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();

		GuiPatronRewardEntry.SELECTED_INDEX = -1;
	}

	@Override
	public void build(IGuiContext context)
	{
		this.setDrawDefaultBackground(true);

		this.getViewing().dim().mod().width(this.width).height(this.height).flush();

		final Pos2D center = InputHelper.getCenter().clone().addY(10).flush();

		this.backdrop = new GuiTexture(Dim2D.build().width(256).height(213).pos(center).center(true).flush(), BACKDROP);

		this.patronRewardText = new GuiText(Dim2D.build().pos(center).addX(-83).addY(-89).flush(),
				new Text(new TextComponentString("Cosmetic Patron Rewards"), 1.0F));

		this.use = new GuiAbstractButton(Dim2D.build().pos(center).addX(16).addY(61).width(70).height(21).flush(),
				new GuiTexture(Dim2D.build().width(70).height(21).flush(), USE),
				new GuiTexture(Dim2D.build().width(70).height(21).flush(), USE_HOVERED),
				new GuiTexture(Dim2D.build().width(70).height(21).flush(), USE),
				new GuiTexture(Dim2D.build().width(70).height(21).flush(), USE_HOVERED));

		this.useText = new GuiText(
				Dim2D.build().x(this.use.dim().originalState().width() / 2).y(this.use.dim().originalState().height() / 2).addY(1).center(true).flush(),
				this.useString);

		this.question = new GuiAbstractButton(Dim2D.build().pos(center).addX(92).addY(61).width(21).height(21).flush(),
				new GuiTexture(Dim2D.build().width(21).height(21).flush(), QUESTION),
				new GuiTexture(Dim2D.build().width(21).height(21).flush(), QUESTION_HOVERED),
				new GuiTexture(Dim2D.build().width(21).height(21).flush(), QUESTION));

		this.rewardViewer = new GuiListViewer<>
				(
						Dim2D.build().width(110).height(154).pos(center).addX(-100).addY(-71).flush(),
						navigator -> navigator.getNodes().inverse().values().stream().max(Comparator.naturalOrder())
								.orElse(-1) + 1,
						this.rewards,
						(pos, node, index) ->
						{
							GuiPatronRewardEntry entry = new GuiPatronRewardEntry(node.getUnlocalizedName(), node.getRewardIcon(), index);

							entry.dim().mod().pos(pos).flush();

							return entry;
						},
						i -> null,
						22
				).allowModifications(false);

		this.rewards.addListener(this);

		this.rewards.put(this.defaultArmor, 0, false);

		int i = 1;

		for (IPatronReward reward : AetherAPI.content().patronRewards().getRewards())
		{
			this.rewards.put(reward, i, false);

			i++;
		}

		context.addChildren(this.backdrop, this.patronRewardText, this.use, this.question, this.rewardViewer);

		this.use.context().addChildren(this.useText);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		if (this.selected != null)
		{
			GlStateManager.pushMatrix();

			this.selected.getPreviewRenderer().renderPreview((this.width / 2) + 64, (this.height / 2) + 40);

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void drawElements()
	{
		super.drawElements();

		if (this.selected != null)
		{
			if (this.selected.isUnlocked(this.patronRewards.getFeatures()))
			{
				this.useText.setText(this.useString);
				this.use.state().setEnabled(true);
			}
			else
			{
				this.useText.setText(this.lockedString);
				this.use.state().setEnabled(false);
			}
		}
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (InputHelper.isHovered(this.use))
		{
			if (this.selected instanceof PatronRewardArmor)
			{
				PatronChoices choices = this.patronRewards.getChoices();

				if (this.selected == this.defaultArmor)
				{
					choices.setArmorChoice(null);
				}
				else if (choices.getArmorChoice() != this.selected)
				{
					choices.setArmorChoice((PatronRewardArmor) this.selected);
				}
			}
		}
	}

	@Override
	public void onRemoveNode(IPatronReward node, int index)
	{

	}

	@Override
	public void onAddNode(IPatronReward node, int index, boolean newNode)
	{

	}

	@Override
	public void onNodeClicked(IPatronReward node, int index)
	{
		this.selected = node;
		this.selected.getPreviewRenderer().renderInit();
	}
}
