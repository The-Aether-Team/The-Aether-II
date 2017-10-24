package com.gildedgames.orbis.client.overlays;

import com.gildedgames.aether.client.gui.overlays.IOverlay;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.client.player.godmode.selection_types.ISelectionTypeClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.util.InputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GodModeOverlay implements IOverlay
{

	private final static ResourceLocation BACKDROP_TEXTURE = AetherCore.getResource("orbis/godmode/overlay/hotbar_power.png");

	private static final Minecraft mc = Minecraft.getMinecraft();

	public GodModeOverlay()
	{

	}

	@Override
	public boolean isEnabled()
	{
		return mc.world != null && PlayerOrbisModule.get(mc.player).inDeveloperMode();
	}

	@Override
	public void draw()
	{
		GlStateManager.pushMatrix();

		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();

		mc.getTextureManager().bindTexture(BACKDROP_TEXTURE);

		final int centerX = (int) InputHelper.getScreenWidth() / 2;
		final int centerZ = (int) (InputHelper.getScreenHeight() - 42);

		Gui.drawModalRectWithCustomSizedTexture(centerX - (33), centerZ, 0, 0, 66, 20, 66, 20);
		final PlayerOrbisModule module = PlayerOrbisModule.get(mc.player);

		final IGodPowerClient powerClient = module.powers().getCurrentPower().getClientHandler();

		mc.getTextureManager().bindTexture(powerClient.getIcon().getResourceLocation());

		int width = (int) powerClient.getIcon().dim().originalState().width();
		int height = (int) powerClient.getIcon().dim().originalState().height();

		Gui.drawModalRectWithCustomSizedTexture(centerX - 7, centerZ + 5, 0, 0, width, height, width, height);

		final ISelectionTypeClient selectionClient = module.selectionTypes().getCurrentSelectionType().getClient();

		mc.getTextureManager().bindTexture(selectionClient.getIcon().getResourceLocation());

		width = (int) selectionClient.getIcon().dim().originalState().width();
		height = (int) selectionClient.getIcon().dim().originalState().height();

		GlStateManager.translate(centerX + 16, centerZ + 9, 0);

		GlStateManager.scale(0.75F, 0.75F, 0.0F);

		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);

		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();

		GlStateManager.popMatrix();
	}

}
