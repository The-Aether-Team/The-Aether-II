package com.gildedgames.aether.common.entities.effects;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;


public interface Ability<S extends Entity>
{
	
	String getUnlocalizedName(S source, EntityEffect<S> instance, NBTTagCompound attributes);
	
	String[] getUnlocalizedDesc(S source, EntityEffect<S> instance, NBTTagCompound attributes);
	
	void formatLocalizedDesc(List<String> localizedDesc, S source, EntityEffect<S> instance, NBTTagCompound attributes);
	
	void initAttributes(S source, NBTTagCompound attributes);

	/**
	 * Called once when this ability is added to an entity.
	 * @param source The entity that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void apply(S source, EntityEffect<S> holder, NBTTagCompound attributes);
	
	void tick(S source, EntityEffect<S> holder, NBTTagCompound attributes);
	
	/**
	 * Called once when this ability is removed from an entity.
	 * @param source The entity that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void cancel(S source, EntityEffect<S> holder, NBTTagCompound attributes);
	
	/**
	 * Called when the source kills a living entity while they have this ability.
	 * @param event The event that is fired.
	 * @param source The entity that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void onKill(LivingDropsEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes);
	
	/**
	 * Called when the source attacks a living entity while they have this ability.
	 * @param event The event that is fired.
	 * @param source The entity that is affected by this ability. 
	 * @param holder The instances of this ability that are attached to the source.
	 * @param attributes TODO
	 */
	void onAttack(LivingHurtEvent event, S source, EntityEffect<S> holder, NBTTagCompound attributes);
	
}
