package com.gildedgames.aether.common.entities.mounts;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IFlyingMountData
{

	boolean canBeMounted();

	boolean canProcessMountInteraction(PlayerEntity rider, ItemStack stack);

	void resetRemainingAirborneTime();

	float getRemainingAirborneTime();

	void setRemainingAirborneTime(float set);

	void addRemainingAirborneTime(float add);

	boolean isFastFalling();

}
