package com.gildedgames.aether.common.entities.util.mounts;

public interface IFlyingMountData
{

	void resetRemainingAirborneTime();

	float getRemainingAirborneTime();

	void setRemainingAirborneTime(float set);

	void addRemainingAirborneTime(float add);

}
