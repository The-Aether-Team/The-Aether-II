package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMasonryProcessOutput implements IMessage
{

    public PacketMasonryProcessOutput()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class HandlerServer extends MessageHandlerServer<PacketMasonryProcessOutput, IMessage>
    {
        @Override
        public IMessage onMessage(PacketMasonryProcessOutput message, EntityPlayer player)
        {
            if (player == null || player.world == null)
            {
                return null;
            }

            if (player.openContainer instanceof ContainerMasonryBench)
            {
                ItemStack outputStack = player.openContainer.inventorySlots.get(1).getStack();

                if (player.inventory.getItemStack().isEmpty())
                {
                    player.inventory.setItemStack(outputStack.copy());
                }
                else if (ItemHelper.getKeyForItemStack(player.inventory.getItemStack()) == ItemHelper
                        .getKeyForItemStack(player.openContainer.inventorySlots.get(1).getStack()))
                {
                    player.inventory.getItemStack().setCount(player.inventory.getItemStack().getCount() + 1);
                }

                player.openContainer.inventorySlots.get(0).decrStackSize(1);
            }

            return null;
        }
    }
}
