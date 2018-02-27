package com.gildedgames.aether.api.world.instances;

import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public interface IInstance extends NBT
{

	void onJoin(EntityPlayer player);

	void onLeave(EntityPlayer player);

	List<EntityPlayer> getPlayers();

	int getDimIdInside();

}
