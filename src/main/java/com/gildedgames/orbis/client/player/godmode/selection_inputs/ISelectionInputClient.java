package com.gildedgames.orbis.client.player.godmode.selection_inputs;

import com.gildedgames.aether.api.orbis.IWorldRenderer;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import com.gildedgames.orbis.common.player.godmode.selection_input.ISelectionInput;
import net.minecraft.world.World;

import java.util.List;

public interface ISelectionInputClient
{

	String displayName();

	GuiTexture getIcon();

	List<IWorldRenderer> getActiveRenderers(ISelectionInput server, final PlayerOrbisModule module, final World world);

}
