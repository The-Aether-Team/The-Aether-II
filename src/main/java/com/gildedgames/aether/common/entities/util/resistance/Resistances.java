package com.gildedgames.aether.common.entities.util.resistance;

import com.gildedgames.aether.common.entities.effects.StatusEffect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Resistances implements IResistances
{
	private Map<String, Float> resistances = new HashMap<>();

	public Resistances()
	{
		Iterator it = StatusEffect.effectsList.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			resistances.put(pair.getKey().toString(), 0.0f);
		}
	}

	@Override
	public void addResistance(String id, float res)
	{
		this.resistances.put(id, this.resistances.get(id) + res);
	}

	@Override
	public void setResistance(String id, float res)
	{
		this.resistances.put(id, res);
	}

	@Override
	public void removeResistance(String id, float res)
	{
		this.resistances.put(id, this.resistances.get(id) - res);
	}

	@Override
	public float getResistance(String id)
	{
		return this.resistances.get(id);
	}

	@Override
	public Map<String, Float> getResistances()
	{
		return this.resistances;
	}

	@Override
	public void setResistances(Map<String, Float> list)
	{
		this.resistances = list;
	}
}
