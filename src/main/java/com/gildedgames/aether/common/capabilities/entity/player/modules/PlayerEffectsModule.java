package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.effects.AmbrosiumPoisoningEffect;
import com.gildedgames.aether.common.entities.effects.StatusEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEffectsModule extends PlayerAetherModule
{
	private StatusEffect ambrosium_poisoning = new AmbrosiumPoisoningEffect();

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
			for (int i = 0; i < StatusEffect.effectsList.size(); i++)
				System.out.println(StatusEffect.effectsList.get(i).getName());
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
