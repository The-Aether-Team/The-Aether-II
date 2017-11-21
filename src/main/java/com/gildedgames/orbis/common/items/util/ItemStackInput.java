package com.gildedgames.orbis.common.items.util;

import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraftforge.client.event.MouseEvent;

public interface ItemStackInput
{

	void onUpdateInHand(PlayerOrbisModule module);

	void onMouseEvent(MouseEvent event, PlayerOrbisModule module);

}
