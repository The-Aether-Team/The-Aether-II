package com.gildedgames.aether.common.entities.util.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IFlyingMountData
{

	boolean canBeMounted();

	boolean canProcessMountInteraction(EntityPlayer rider, ItemStack stack);

	void resetRemainingAirborneTime();

	float getRemainingAirborneTime();

	void setRemainingAirborneTime(float set);

	void addRemainingAirborneTime(float add);

	boolean isFastFalling();

}
