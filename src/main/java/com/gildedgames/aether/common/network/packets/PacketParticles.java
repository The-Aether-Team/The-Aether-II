package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.registry.content.ParticlesAether;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketParticles implements IMessage
{
	private static final ParticlesAether[] PARTICLES = ParticlesAether.values();

	private ParticlesAether particle;

	private double x, y, z, offsetX, offsetY, offsetZ;

	public PacketParticles()
	{

	}

	public PacketParticles(ParticlesAether particle, double x, double y, double z, double offsetX, double offsetY, double offsetZ)
	{
		this.particle = particle;

		this.x = x;
		this.y = y;
		this.z = z;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	@Override
	public void fromBytes(final ByteBuf buf)
	{
		this.particle = PARTICLES[buf.readInt()];

		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();

		this.offsetX = buf.readDouble();
		this.offsetY = buf.readDouble();
		this.offsetZ = buf.readDouble();
	}

	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeInt(this.particle.ordinal());

		buf.writeDouble(this.x);
		buf.writeDouble(this.y);
		buf.writeDouble(this.z);

		buf.writeDouble(this.offsetX);
		buf.writeDouble(this.offsetY);
		buf.writeDouble(this.offsetZ);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketParticles, PacketParticles>
	{
		@Override
		public PacketParticles onMessage(final PacketParticles message, final EntityPlayer player)
		{
			switch (message.particle)
			{
				case SLASH:
					AetherCore.PROXY.spawnSlashParticleFrom(player.world, message.x, message.y, message.z, message.offsetX, message.offsetY, message.offsetZ);
					break;
				case IMPACT:
					AetherCore.PROXY.spawnImpactParticleFrom(player.world, message.x, message.y, message.z, message.offsetX, message.offsetY, message.offsetZ);
					break;
				case PIERCE:
					AetherCore.PROXY.spawnPierceParticleFrom(player.world, message.x, message.y, message.z, message.offsetX, message.offsetY, message.offsetZ);
					break;
			}

			return null;
		}
	}
}
