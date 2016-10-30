package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.MidAirJumpsChangedPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public class ExtraJumpEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public ExtraJumpEffect()
	{
		super("ability.doubleJump.name", "ability.doubleJump.desc");
	}

	@Override
	public void apply(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityPlayerMP))
		{
			return;
		}

		PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(source);

		playerAether.getAbilitiesModule().setMidAirJumpsAllowed(1);

		NetworkingAether.sendPacketToPlayer(new MidAirJumpsChangedPacket(1), (EntityPlayerMP) source);
	}

	@Override
	public void cancel(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityPlayerMP))
		{
			return;
		}

		PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(source);

		playerAether.getAbilitiesModule().setMidAirJumpsAllowed(0);

		NetworkingAether.sendPacketToPlayer(new MidAirJumpsChangedPacket(0), (EntityPlayerMP) source);
	}


}
