package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMasonryRecipeChanged implements IMessage
{

	private ISimpleRecipe recipe;

	public PacketMasonryRecipeChanged()
	{

	}

	public PacketMasonryRecipeChanged(ISimpleRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		this.recipe = AetherAPI.content().masonry().getRecipeFromID(pBuf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		pBuf.writeInt(AetherAPI.content().masonry().getIDFromRecipe(this.recipe));
	}

	public static class HandlerServer extends MessageHandlerServer<PacketMasonryRecipeChanged, IMessage>
	{
		@Override
		public IMessage onMessage(PacketMasonryRecipeChanged message, PlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return null;
			}

			if (player.openContainer instanceof ContainerMasonryBench)
			{
				ContainerMasonryBench container = (ContainerMasonryBench) player.openContainer;

				container.onNewRecipe(message.recipe);
			}

			return null;
		}
	}
}
