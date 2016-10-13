package com.gildedgames.aether.common.entities.util.sliding;

public interface ISlidingEntity
{

	boolean shouldSlide();

	void onSlide();

	void onSliding();

	void onStartSlideCooldown(SlidingHorizontalMoveHelper.Direction direction);

	int getSlideCooldown();

}
