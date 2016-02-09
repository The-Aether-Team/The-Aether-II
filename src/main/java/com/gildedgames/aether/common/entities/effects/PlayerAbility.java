package com.gildedgames.aether.common.entities.effects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface PlayerAbility extends Ability<EntityPlayer>
{

	/**
	 * Called when the player interacts with anything in the game, mostly related
	 * to mouse input.
	 * @param event The event that is fired off.
	 * @param source The player that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void onInteract(PlayerInteractEvent event, EntityPlayer source, EntityEffect<EntityPlayer> holder, NBTTagCompound attributes);
	
}
