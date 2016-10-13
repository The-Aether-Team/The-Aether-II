package com.gildedgames.aether.common.entities.util;

public abstract class BossStage
{

	private boolean hasBegun;

	protected abstract boolean conditionsMet();

	protected abstract void onStageBegin();

	public void update()
	{
		if (!this.hasBegun() && this.conditionsMet())
		{
			this.onStageBegin();
			this.setHasBegun(true);
		}
	}

	public boolean hasBegun()
	{
		return this.hasBegun;
	}

	private void setHasBegun(boolean flag)
	{
		this.hasBegun = flag;
	}

}
