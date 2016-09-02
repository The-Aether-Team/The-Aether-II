package com.gildedgames.aether.common.capabilities.entity.effects.processors;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.MidAirJumpsChangedPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public class SlowfallEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public SlowfallEffect()
	{
		super("ability.slowfall.name", "ability.slowfall.desc");
	}

	@Override
	public void tick(Entity source, List<EntityEffectInstance> all)
	{
		if (source.motionY < 0.0D && !source.isSneaking())
		{
			source.motionY *= 0.6F;
		}

		source.fallDistance = 0;
	}

}
