package com.gildedgames.aether.common.entities.util.resistance;

import java.util.Map;

public interface IResistances
{
	public void addResistance(String id, float res);
	public void setResistance(String id, float res);
	public void removeResistance(String id, float res);

	public float getResistance(String id);

	public Map<String, Float> getResistances();
	public void setResistances(Map<String, Float> list);
}
