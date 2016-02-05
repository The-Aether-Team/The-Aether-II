package com.gildedgames.aether.common.items.accessories.providers;

import com.gildedgames.aether.common.items.accessories.AccessoryEffect;


public class CosmeticAccessory implements EffectProvider
{
	
	public static final CosmeticAccessory INST = new CosmeticAccessory();
	
	private CosmeticAccessory()
	{
		
	}

	@Override
	public AccessoryEffect[] createEffects()
	{
		return null;
	}

}
