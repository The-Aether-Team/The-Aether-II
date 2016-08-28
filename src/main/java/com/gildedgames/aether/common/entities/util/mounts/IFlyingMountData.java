package com.gildedgames.aether.common.entities.util.mounts;

public interface IFlyingMountData
{

	boolean canBeMounted();

	void resetRemainingAirborneTime();

	float getRemainingAirborneTime();

	void setRemainingAirborneTime(float set);

	void addRemainingAirborneTime(float add);

}
