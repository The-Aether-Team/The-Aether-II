package com.gildedgames.aether.common.network.packets;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.recipes.simple.ISimpleRecipe;
import com.gildedgames.aether.common.containers.tiles.ContainerMasonryBench;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

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
	public void fromBytes(PacketBuffer buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		this.recipe = AetherAPI.content().masonry().getRecipeFromID(pBuf.readInt());
	}

	@Override
	public void toBytes(PacketBuffer buf)
	{
		PacketBuffer pBuf = new PacketBuffer(buf);

		pBuf.writeInt(AetherAPI.content().masonry().getIDFromRecipe(this.recipe));
	}

	public static class HandlerServer extends MessageHandlerServer<PacketMasonryRecipeChanged>
	{
		@Override
		protected void onMessage(PacketMasonryRecipeChanged message, ServerPlayerEntity player)
		{
			if (player == null || player.world == null)
			{
				return;
			}

			if (player.openContainer instanceof ContainerMasonryBench)
			{
				ContainerMasonryBench container = (ContainerMasonryBench) player.openContainer;
				container.onNewRecipe(message.recipe);
			}
		}
	}
}
