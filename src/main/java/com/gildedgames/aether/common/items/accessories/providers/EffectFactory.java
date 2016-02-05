package com.gildedgames.aether.common.items.accessories.providers;

import com.gildedgames.aether.common.items.accessories.AccessoryEffect;
import com.gildedgames.util.io_manager.IOCore;


public class EffectFactory implements EffectProvider
{
	
	private Class<? extends AccessoryEffect>[] classes;
	
	private EffectFactory()
	{
		
	}
	
	@SafeVarargs
	public static EffectFactory of(Class<? extends AccessoryEffect>... classes)
	{
		EffectFactory factory = new EffectFactory();
		
		factory.classes = classes;
	
		return factory;
	}

	@Override
	public AccessoryEffect[] createEffects()
	{
		AccessoryEffect[] array = new AccessoryEffect[this.classes.length];
		
		int i = 0;
		
		for (Class<? extends AccessoryEffect> clazz : this.classes)
		{
			array[i] = IOCore.io().create(clazz);
			
			i++;
		}
		
		return array;
	}

}
