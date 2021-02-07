package com.gildedgames.aether.common.network.packets.effects;

import com.gildedgames.aether.api.entity.effects.IAetherStatusEffectPool;
import com.gildedgames.aether.api.registrar.CapabilitiesAether;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketResetResistance implements IMessage
{
    int playerID;

    public PacketResetResistance()
    {

    }

    public PacketResetResistance(EntityPlayer player)
    {
        this.playerID = player.getEntityId();
    }

    @Override
    public void fromBytes(final ByteBuf buf)
    {
        this.playerID = buf.readInt();
    }

    @Override
    public void toBytes(final ByteBuf buf)
    {
        buf.writeInt(this.playerID);
    }

    public static class HandlerClient extends MessageHandlerClient<PacketResetResistance, PacketResetResistance>
    {
        @Override
        public PacketResetResistance onMessage(final PacketResetResistance message, final EntityPlayer clientPlayer)
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
                            statusEffectPool.resetAllResistances();
                        }
                    }
                }
            }

            return null;
        }
    }
}
