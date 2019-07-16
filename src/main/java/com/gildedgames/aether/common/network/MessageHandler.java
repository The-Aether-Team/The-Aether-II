package com.gildedgames.aether.common.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class MessageHandler<REQ extends IMessage> implements BiConsumer<REQ, Supplier<NetworkEvent.Context>>
{

}
