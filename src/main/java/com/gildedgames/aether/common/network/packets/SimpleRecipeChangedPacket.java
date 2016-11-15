package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.common.containers.ContainerSimpleCrafting;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SimpleRecipeChangedPacket implements IMessage
{

	private ISimpleRecipe recipe;

	public SimpleRecipeChangedPacket()
	{

	}

	public SimpleRecipeChangedPacket(ISimpleRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		this.recipe = AetherAPI.crafting().getRecipe(pBuf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		pBuf.writeInt(AetherAPI.crafting().getId(this.recipe));
	}

	public static class HandlerServer extends MessageHandlerServer<SimpleRecipeChangedPacket, IMessage>
	{
		@Override
		public IMessage onMessage(SimpleRecipeChangedPacket message, EntityPlayer player)
		{
			if (player == null || player.worldObj == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerSimpleCrafting)
			{
				ContainerSimpleCrafting container = (ContainerSimpleCrafting) player.openContainer;

				container.onNewRecipe(message.recipe);
			}

			return null;
		}
	}
}
