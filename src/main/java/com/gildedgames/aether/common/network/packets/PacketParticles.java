package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class PacketParticles implements IMessage
{
	private static final ParticlesAether[] PARTICLES = ParticlesAether.values();

	private ParticlesAether particle;

	private double posX, posY, posZ, offsetX, offsetY, offsetZ;

	public PacketParticles(ParticlesAether particle, double posX, double posY, double posZ, double offsetX, double offsetY, double offsetZ)
	{
		this.particle = particle;

		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.particle = PARTICLES[buf.readInt()];

		this.posX = buf.readDouble();
		this.posY = buf.readDouble();
		this.posZ = buf.readDouble();

		this.offsetX = buf.readDouble();
		this.offsetY = buf.readDouble();
		this.offsetZ = buf.readDouble();
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeInt(this.particle.ordinal());

		buf.writeDouble(this.posX);
		buf.writeDouble(this.posY);
		buf.writeDouble(this.posZ);

		buf.writeDouble(this.offsetX);
		buf.writeDouble(this.offsetY);
		buf.writeDouble(this.offsetZ);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketParticles>
	{
		@Override
		protected void onMessage(PacketParticles message, ClientPlayerEntity player)
		{
			switch (message.particle)
			{
				case SLASH:
					AetherCore.PROXY.spawnSlashParticleFrom(player.world, message.posX, message.posY, message.posZ, message.offsetX, message.offsetY, message.offsetZ);
					break;
				case IMPACT:
					AetherCore.PROXY.spawnImpactParticleFrom(player.world, message.posX, message.posY, message.posZ, message.offsetX, message.offsetY, message.offsetZ);
					break;
				case PIERCE:
					AetherCore.PROXY.spawnPierceParticleFrom(player.world, message.posX, message.posY, message.posZ, message.offsetX, message.offsetY, message.offsetZ);
					break;
			}
		}
	}
}
