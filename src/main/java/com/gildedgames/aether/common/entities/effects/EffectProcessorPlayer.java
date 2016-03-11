package com.gildedgames.aether.common.entities.effects;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public interface EffectProcessorPlayer<I extends EffectInstance> extends EffectProcessor<I>
{

	/**
	 * Called when the player interacts with anything in the game, mostly related
	 * to mouse input.
	 * @param event The event that is fired off.
	 * @param source The player that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void onInteract(PlayerInteractEvent event, EntityPlayer source, List<I> instances);
	
	void onPickupXP(PlayerPickupXpEvent event, EntityPlayer source, List<I> instances);
	
}