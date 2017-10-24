package com.gildedgames.orbis.common.player.godmode;

import com.gildedgames.aether.api.util.NBT;
import com.gildedgames.orbis.client.player.godmode.IGodPowerClient;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.entity.player.EntityPlayer;

public interface IGodPower extends NBT
{

	void onUpdate(EntityPlayer player, PlayerOrbisModule module);

	boolean hasCustomGui();

	void onOpenGui(EntityPlayer player);

	boolean canInteractWithItems(PlayerOrbisModule module);

	/** If the returned IShapeSelector is null, this GodPower cannot
	 * select shapes in the world.
	 * @return
	 */
	IShapeSelector getShapeSelector();

	IGodPowerClient getClientHandler();

}
