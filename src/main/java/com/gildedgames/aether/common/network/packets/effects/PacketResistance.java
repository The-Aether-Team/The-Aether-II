package com.gildedgames.aether.common.network.packets.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.entity.effects.IAetherStatusEffects;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketResistance implements IMessage
{
    int playerID;
    String effect;
    double resistance;

    public PacketResistance()
    {

    }

    public PacketResistance(EntityPlayer player, String effect, double resistance)
    {
        this.playerID = player.getEntityId();
        this.effect = effect;
        this.resistance = resistance;
    }

    @Override
    public void fromBytes(final ByteBuf buf)
    {
        this.playerID = buf.readInt();
        this.effect = ByteBufUtils.readUTF8String(buf);;
        this.resistance = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf)
    {
        buf.writeInt(this.playerID);
        ByteBufUtils.writeUTF8String(buf, this.effect);
        buf.writeDouble(this.resistance);
    }

    public static class HandlerClient extends MessageHandlerClient<PacketResistance, PacketResistance>
    {
        @Override
        public PacketResistance onMessage(final PacketResistance message, final EntityPlayer clientPlayer)
        {
            if (clientPlayer != null && clientPlayer.world != null)
            {
                if (clientPlayer.world.getEntityByID(message.playerID) instanceof EntityPlayer)
                {
                    EntityPlayer entityPlayer = (EntityPlayer) clientPlayer.world.getEntityByID(message.playerID);

                    if (entityPlayer != null)
                    {
                        IAetherStatusEffectPool statusEffectPool = entityPlayer.getCapability(CapabilitiesAether.STATUS_EFFECT_POOL, null);

                        if (statusEffectPool != null)
                        {
                            IAetherStatusEffects actualEffect = statusEffectPool.createEffect(message.effect,entityPlayer);

                            statusEffectPool.addResistanceToEffect(actualEffect.getEffectType(), message.resistance);
                        }
                    }
                }
            }

            return null;
        }
    }
}
