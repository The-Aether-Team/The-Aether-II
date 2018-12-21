package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.entities.effects.AmbrosiumPoisoningEffect;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;
import java.util.Map;

public class PlayerEffectsModule extends PlayerAetherModule
{
	public PlayerEffectsModule(PlayerAether player)
	{
		super(player);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
		if (!this.getWorld().isRemote)
		{
			System.out.println(StatusEffect.effectsList.size());
			System.out.println(StatusEffect.effectsList.keySet());
			Iterator it = StatusEffect.effectsList.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey() + " " + pair.getValue() + " " + ((StatusEffect)pair.getValue()).getName());
			}
		}
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public void write(NBTTagCompound tag)
	{
	}

	@Override
	public void read(NBTTagCompound tag)
	{
	}
}
