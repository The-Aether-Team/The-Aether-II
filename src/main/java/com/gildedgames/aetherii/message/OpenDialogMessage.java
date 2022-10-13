package com.gildedgames.aetherii.message;

import com.gildedgames.aetherii.api.entity.TalkableMob;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenDialogMessage {

	private int entityId;
	private int ownerID;

	private ResourceLocation name;

	private String startingNodeId;


	public OpenDialogMessage(int id, int ownerID, final ResourceLocation res, String startingNodeId) {
		this.entityId = id;
		this.ownerID = ownerID;
		this.name = res;
		this.startingNodeId = startingNodeId;
	}


	public void serialize(FriendlyByteBuf buffer) {
		buffer.writeInt(this.entityId);
		buffer.writeInt(this.ownerID);
		buffer.writeResourceLocation(this.name);
		buffer.writeUtf(this.startingNodeId);
	}

	public static OpenDialogMessage deserialize(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();
		int ownerId = buffer.readInt();
		ResourceLocation name = buffer.readResourceLocation();
		String nodeId = buffer.readUtf();
		return new OpenDialogMessage(entityId, ownerId, name, nodeId);
	}

	public static boolean handle(OpenDialogMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();

		if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
			context.enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().player.level.getEntity(message.entityId);
				Entity ownerEntity = Minecraft.getInstance().player.level.getEntity(message.ownerID);
				if (entity != null && entity instanceof Player && ownerEntity != null && ownerEntity instanceof TalkableMob) {
					//((TalkableMob) ownerEntity).getTalkableController().setConditionsMetData(message.conditionsMet);
					((TalkableMob) ownerEntity).getTalkableController().openScene(((Player) entity), (TalkableMob) ownerEntity, message.name, message.startingNodeId);
				}
			});
		}

		return true;
	}
}