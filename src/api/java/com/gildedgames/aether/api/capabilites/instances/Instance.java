package com.gildedgames.aether.api.capabilites.instances;

import com.gildedgames.aether.api.util.NBT;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface Instance extends NBT
{

	void onJoin(EntityPlayer player);

	void onLeave(EntityPlayer player);

	List<EntityPlayer> getPlayers();

	int getDimIdInside();

}
