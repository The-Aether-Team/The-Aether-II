package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.world.World;

import java.util.List;

public interface IGodPowerClient
{

	/**
	 *
	 * @return Whether or not the gui screen was opened. If not, will default
	 * to normal inventory screen.
	 */
	boolean openGuiScreen();

	String displayName();

	GuiTexture getIcon();

	boolean has3DCursor(PlayerOrbisModule module);

	float minFade3DCursor(PlayerOrbisModule module);

	int getShapeColor(PlayerOrbisModule module);

	List<IWorldRenderer> getActiveRenderers(PlayerOrbisModule module, World world);

}
