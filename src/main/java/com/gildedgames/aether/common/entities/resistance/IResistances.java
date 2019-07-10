package com.gildedgames.aether.common.entities.resistance;

import java.util.Map;

public interface IResistances
{
	void addResistance(String id, float res);

	void setResistance(String id, float res);

	void removeResistance(String id, float res);

	float getResistance(String id);

	Map<String, Float> getResistances();

	void setResistances(Map<String, Float> list);
}
