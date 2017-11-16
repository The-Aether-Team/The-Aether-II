package com.gildedgames.orbis.client.player.godmode;

import com.gildedgames.aether.api.orbis.IShape;
import com.gildedgames.orbis.client.gui.util.GuiTexture;
import com.gildedgames.orbis.common.player.PlayerOrbisModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;

import java.util.List;

public interface IGodPowerClient
{

	void onOpenGui(EntityPlayer player);

	String displayName();

	GuiTexture getIcon();

	boolean has3DCursor(PlayerOrbisModule module);

	float minFade3DCursor(PlayerOrbisModule module);

	int getShapeColor(PlayerOrbisModule module);

	List getActiveRenderers(PlayerOrbisModule module, World world);

	/**
	 * @return True if it should use the regular active selection behaviour. False to cancel.
	 */
	boolean onRightClickShape(PlayerOrbisModule module, IShape selectedShape, MouseEvent event);

}
