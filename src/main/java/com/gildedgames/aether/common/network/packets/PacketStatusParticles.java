package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.init.ParticlesAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketStatusParticles implements IMessage
{
    private double x, y, z, motionX, motionY, motionZ;

    private float particleRed, particleGreen, particleBlue;

    public PacketStatusParticles()
    {

    }

    public PacketStatusParticles(double x, double y, double z, double motionX, double motionY, double motionZ, float particleRed, float particleGreen, float particleBlue)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;
    }

    @Override
    public void fromBytes(final ByteBuf buf)
    {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();

        this.motionX = buf.readDouble();
        this.motionY = buf.readDouble();
        this.motionZ = buf.readDouble();

        this.particleRed = buf.readFloat();
        this.particleGreen = buf.readFloat();
        this.particleBlue = buf.readFloat();
    }

    @Override
    public void toBytes(final ByteBuf buf)
    {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);

        buf.writeDouble(this.motionX);
        buf.writeDouble(this.motionY);
        buf.writeDouble(this.motionZ);

        buf.writeFloat(this.particleRed);
        buf.writeFloat(this.particleGreen);
        buf.writeFloat(this.particleBlue);
    }

    public static class HandlerClient extends MessageHandlerClient<PacketStatusParticles, PacketStatusParticles>
    {
        @Override
        public PacketStatusParticles onMessage(final PacketStatusParticles message, final EntityPlayer player)
        {
            AetherCore.PROXY.spawnEffectParticles(player.world, message.x, message.y, message.z, message.motionX, message.motionY, message.motionZ, message.particleRed, message.particleGreen,
                    message.particleBlue);

            return null;
        }
    }
}
