package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import com.gildedgames.aether.common.util.helpers.ItemHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMasonryProcessOutputShift implements IMessage
{

    public PacketMasonryProcessOutputShift()
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

    public static class HandlerServer extends MessageHandlerServer<PacketMasonryProcessOutputShift, IMessage>
    {
        @Override
        public IMessage onMessage(PacketMasonryProcessOutputShift message, EntityPlayer player)
        {
            if (player == null || player.world == null)
            {
                return null;
            }

            if (player.openContainer instanceof ContainerMasonryBench)
            {
                ItemStack newOutputStack = player.openContainer.inventorySlots.get(1).getStack().copy();
                newOutputStack.setCount(player.openContainer.inventorySlots.get(0).getStack().getCount());
//
                player.inventory.addItemStackToInventory(newOutputStack);
//
                player.openContainer.inventorySlots.get(0).decrStackSize(player.openContainer.inventorySlots.get(0).getStack().getCount());
            }

            return null;
        }
    }
}
