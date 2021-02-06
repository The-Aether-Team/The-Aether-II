package com.gildedgames.aether.client.gui.container.guidebook.status;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.api.shop.IGuiCurrencyValue;
import com.gildedgames.aether.client.gui.container.guidebook.AbstractGuidebookPage;
import com.gildedgames.aether.client.gui.container.guidebook.discovery.stats.GuiStat;
import com.gildedgames.aether.client.gui.container.guidebook.status.info.GuiEffectBar;
import com.gildedgames.aether.client.gui.container.guidebook.status.info.GuiResistance;
import com.gildedgames.aether.client.gui.misc.GuiScrollableGuidebook;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerCurrencyModule;
import com.gildedgames.aether.common.containers.guidebook.EmptyContainer;
import com.gildedgames.aether.common.shop.ShopCurrencyGilt;
import com.gildedgames.orbis.lib.client.gui.data.Text;
import com.gildedgames.orbis.lib.client.gui.util.GuiText;
import com.gildedgames.orbis.lib.client.gui.util.GuiTexture;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.GuiLibHelper;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiElement;
import com.gildedgames.orbis.lib.client.gui.util.gui_library.IGuiViewer;
import com.gildedgames.orbis.lib.client.rect.Dim2D;
import com.gildedgames.orbis.lib.client.rect.Pos2D;
import com.gildedgames.orbis.lib.util.InputHelper;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiGuidebookStatus extends AbstractGuidebookPage
{
	private static final ResourceLocation HEALTH_ICON = AetherCore.getResource("textures/gui/guidebook/icons/heart.png");
	private static final ResourceLocation ARMOR_ICON = AetherCore.getResource("textures/gui/guidebook/icons/armor.png");

	private static final ResourceLocation LEFT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_left.png");

	private static final ResourceLocation RIGHT_PAGE = AetherCore.getResource("textures/gui/guidebook/status/guidebook_status_right.png");

	private GuiText healthText, armorText;

	private IGuiCurrencyValue coins;

	private GuiScrollableGuidebook statsArea, effectsArea;

	private List<GuiElement> effectBars;

	private HashMap<String, IAetherStatusEffects> pool = new HashMap<>();

	public GuiGuidebookStatus(final IGuiViewer prevViewer, final PlayerAether aePlayer)
	{
		super(prevViewer, aePlayer, new EmptyContainer());
	}

	//PLAYER
	@Override
	protected List<IGuiElement> createLeftPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture leftPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				LEFT_PAGE);

		final GuiText header = new GuiText(Dim2D.build().x(screenX + 72).y(screenY + 13).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.status"), 1.0F));

		GuiTexture heartTexture = new GuiTexture(Dim2D.build().x(screenX + 32).y(screenY + 29).width(9).height(9).flush(), HEALTH_ICON);
		this.healthText = new GuiText(Dim2D.build().x(screenX + 44).y(screenY + 30).flush(),
				new Text(new TextComponentString(""), 1.0F));

		GuiTexture armorTexture = new GuiTexture(Dim2D.build().x(screenX + 32).y(screenY + 42).width(9).height(9).flush(), ARMOR_ICON);
		this.armorText = new GuiText(Dim2D.build().x(screenX + 44).y(screenY + 43).flush(),
				new Text(new TextComponentString(""), 1.0F));

		this.coins = new ShopCurrencyGilt().createCurrencyValueGui(Dim2D.build().x(screenX + 92).y(screenY + 98).flush());

		this.statsArea = new GuiScrollableGuidebook(new GuiElement(Dim2D.build().x(screenX + 24).y(screenY + 59).flush(), false),
				Dim2D.build().width(52 + 9).height(107).flush(), true);

		this.effectsArea = new GuiScrollableGuidebook(new GuiElement(Dim2D.build().x(screenX + 92).y(screenY + 118).flush(), false),
				Dim2D.build().width(52 + 9).height(48).flush(), true);

		return Lists.newArrayList(leftPage,
				header,
				heartTexture,
				this.healthText,
				armorTexture,
				this.armorText,
				this.coins,
				this.statsArea,
				this.effectsArea);
	}

	//MOA
	@Override
	protected List<IGuiElement> createRightPage(final int screenX, final int screenY, final float u, final float v)
	{
		final GuiTexture rightPage = new GuiTexture(Dim2D.build().width(this.PAGE_WIDTH).height(this.PAGE_HEIGHT).x(screenX).y(screenY).flush(),
				RIGHT_PAGE);

		final GuiText header = new GuiText(Dim2D.build().x(screenX + 73).y(screenY + 13).flush(),
				new Text(new TextComponentTranslation("tab.guidebook.mount"), 1.0F));

		return Lists.newArrayList(rightPage,
				header);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTick)
	{
		super.drawScreen(mouseX, mouseY, partialTick);

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		this.drawPlayer(mouseX, mouseY);

		int health = (int) Minecraft.getMinecraft().player.getHealth();
		int maxHealth = (int) Minecraft.getMinecraft().player.getMaxHealth();
		this.healthText.setText(new Text(new TextComponentString(health + "/" + maxHealth), 1.0F));

		int armor = Minecraft.getMinecraft().player.getTotalArmorValue();
		int maxArmor = 20;
		this.armorText.setText(new Text(new TextComponentString(armor + "/" + maxArmor), 1.0F));

		IPlayerAether playerAether = PlayerAether.getPlayer(Minecraft.getMinecraft().player);
		int value = (int) playerAether.getModule(PlayerCurrencyModule.class).getCurrencyValue();
		this.coins.setCurrencyValue(value);

		this.resetStats();

		this.resetEffects();

		if (this.effectBars != null && !this.effectBars.isEmpty())
		{
			for (GuiElement element : this.effectBars)
			{
				if (element instanceof GuiEffectBar)
				{
					GuiEffectBar effect = (GuiEffectBar) element;

					if (InputHelper.isHovered(effect))
					{
						this.drawHoveringText(I18n.format(effect.getEffect().getEffectName()),
								Mouse.getX() * scaledresolution.getScaledWidth() / mc.displayWidth,
								scaledresolution.getScaledHeight() - (Mouse.getY() - 42) * scaledresolution.getScaledHeight() / mc.displayHeight
										- 1, mc.fontRenderer);
					}
				}
			}
		}
	}

	private void drawPlayer(final int mouseX, final int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GuiInventory.drawEntityOnScreen(
				this.width / 2 - 54,
				this.height / 2, 32, (this.guiLeft + 88) - mouseX, (this.guiTop + 35) - mouseY, this.mc.player);
	}

	private void resetStats()
	{
		updateResistances();
	}

	private void updateResistances()
	{
		IAetherStatusEffectPool statusEffectPool = Minecraft.getMinecraft().player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		final IGuiElement statsElement = new GuiElement(Dim2D.build().width(52).flush(), false);

		List<GuiElement> statsArray = new ArrayList<>();

		if (statusEffectPool != null)
		{
			for (Map.Entry<String, IAetherStatusEffects> effect : statusEffectPool.getPool().entrySet())
			{
				if (effect.getValue().getResistance() != 1.0)
				{
					final GuiResistance effectTestStat = new GuiResistance(effect.getValue());

					statsArray.add(effectTestStat);
				}
			}
		}

		GuiElement[] elements = statsArray.toArray(new GuiElement[0]);

		statsElement.build(this);

		GuiLibHelper.alignVertically(this, Pos2D.flush(0, 0), 0, elements);
		statsElement.context().addChildren(elements);

		GuiLibHelper.assembleMinMaxArea(statsElement);

		statsElement.dim().mod().pos(this.statsArea.dim().min()).flush();

		this.statsArea.setDecorated(statsElement);
	}

	private void resetEffects()
	{
		IAetherStatusEffectPool statusEffectPool = Minecraft.getMinecraft().player.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

		if (statusEffectPool != null)
		{
			if ((this.pool.isEmpty() && !statusEffectPool.getPool().isEmpty()) || (!this.pool.isEmpty() && statusEffectPool.getPool().isEmpty()))
			{
				this.updateEffects(statusEffectPool);
			}

			this.pool = new HashMap<>(statusEffectPool.getPool());
		}
		else
		{
			this.pool.clear();
		}
	}

	private void updateEffects(IAetherStatusEffectPool statusEffectPool)
	{
		final IGuiElement statsElement = new GuiElement(Dim2D.build().width(52).flush(), false);

		List<GuiElement> statsArray = new ArrayList<>();

		for (IAetherStatusEffects effect : statusEffectPool.getPool().values())
		{
			if (effect.getResistance() == 1.0D)
			{
				final GuiEffectBar effectBar = new GuiEffectBar(effect);
				statsArray.add(effectBar);
			}
		}

		GuiElement[] elements = statsArray.toArray(new GuiElement[0]);

		statsElement.build(this);

		GuiLibHelper.alignVertically(this, Pos2D.flush(0, 0), 0, elements);
		statsElement.context().addChildren(elements);

		GuiLibHelper.assembleMinMaxArea(statsElement);

		statsElement.dim().mod().pos(this.effectsArea.dim().min()).flush();

		this.effectsArea.setDecorated(statsElement);

		this.effectBars = statsArray;
	}

	@SideOnly(Side.CLIENT)
	protected void drawHoveringText(String text, int x, int y, FontRenderer font)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		int k = font.getStringWidth(text);

		int x1 = x + 12;
		int y1 = y - 12;
		int z1 = 8;

		this.zLevel = 300.0F;
		int color1 = -267386864;
		this.drawGradientRect(x1 - 3, y1 - 4, x1 + k + 3, y1 - 3, color1, color1);
		this.drawGradientRect(x1 - 3, y1 + z1 + 3, x1 + k + 3, y1 + z1 + 4, color1, color1);
		this.drawGradientRect(x1 - 3, y1 - 3, x1 + k + 3, y1 + z1 + 3, color1, color1);
		this.drawGradientRect(x1 - 4, y1 - 3, x1 - 3, y1 + z1 + 3, color1, color1);
		this.drawGradientRect(x1 + k + 3, y1 - 3, x1 + k + 4, y1 + z1 + 3, color1, color1);
		int color2 = 1347420415;
		int color2BG = (color2 & 16711422) >> 1 | color2 & -16777216;
		this.drawGradientRect(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, y1 + z1 + 3 - 1, color2, color2BG);
		this.drawGradientRect(x1 + k + 2, y1 - 3 + 1, x1 + k + 3, y1 + z1 + 3 - 1, color2, color2BG);
		this.drawGradientRect(x1 - 3, y1 - 3, x1 + k + 3, y1 - 3 + 1, color2, color2);
		this.drawGradientRect(x1 - 3, y1 + z1 + 2, x1 + k + 3, y1 + z1 + 3, color2BG, color2BG);

		font.drawString(text, x1, y1, -1);

		this.zLevel = 0.0F;

		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}



//	private void drawEquipmentEffects()
//	{
//		final ArrayList<String> label = new ArrayList<>();
//
//		final PlayerEquipmentModule equipment = this.aePlayer.getModule(PlayerEquipmentModule.class);
//		equipment.getActivePools().forEach((pool) -> pool.getInstance().ifPresent(instance -> instance.addInformation(label, TextFormatting.BLUE, TextFormatting.RED)));
//
//		final String compiled = StringUtils.join(label, TextFormatting.RESET + ", ");
//
//		this.mc.fontRenderer.drawString(compiled, this.guiLeft, this.guiTop + 160, 0xFFFFFF, true);
//	}
}
