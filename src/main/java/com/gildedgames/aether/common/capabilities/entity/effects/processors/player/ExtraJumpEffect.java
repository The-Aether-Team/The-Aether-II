package com.gildedgames.aether.common.capabilities.entity.effects.processors.player;

import com.gildedgames.aether.api.capabilites.entity.effects.EntityEffectInstance;
import com.gildedgames.aether.common.capabilities.entity.effects.AbstractEffectProcessorPlayer;
import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import com.gildedgames.aether.common.entities.projectiles.EntityDaggerfrostSnowball;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.AetherMovementPacket;
import com.gildedgames.aether.common.network.packets.MidAirJumpsChangedPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

public class ExtraJumpEffect extends AbstractEffectProcessorPlayer<EntityEffectInstance>
{

	public ExtraJumpEffect()
	{
		super("ability.tripleJump.localizedName", "ability.tripleJump.desc");
	}

	@Override
	public void apply(Entity source, EntityEffectInstance instance, List<EntityEffectInstance> all)
	{
		if (!(source instanceof EntityPlayerMP))
		{
			return;
		}

		PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(source);

		playerAether.getAbilitiesModule().setMidAirJumpsAllowed(2);

		NetworkingAether.sendPacketToPlayer(new MidAirJumpsChangedPacket(2), (EntityPlayerMP) source);
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
