package com.gildedgames.aether.common.network.packets.dialog;

import com.gildedgames.aether.api.dialog.IDialogController;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.network.IMessage;
import com.gildedgames.aether.common.network.MessageHandlerClient;
import com.gildedgames.orbis.lib.util.io.NBTFunnel;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Map;

public class PacketOpenDialog implements IMessage
{
	private ResourceLocation name;

	private String startingNodeId;

	private Map<String, Boolean> conditionsMet;

	private NBTFunnel funnel;

	public PacketOpenDialog(final ResourceLocation res, String startingNodeId, Map<String, Boolean> conditionsMet)
	{
		this.name = res;
		this.startingNodeId = startingNodeId;
		this.conditionsMet = conditionsMet;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.name = buf.readResourceLocation();
		this.startingNodeId = buf.readString();
		this.funnel = new NBTFunnel(buf.readCompoundTag());
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		buf.writeResourceLocation(this.name);
		buf.writeString(this.startingNodeId);

		CompoundNBT tag = new CompoundNBT();
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setMap("c", this.conditionsMet, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);

		buf.writeCompoundTag(tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketOpenDialog>
	{
		@Override
		protected void onMessage(PacketOpenDialog message, ClientPlayerEntity player)
		{
			Map<String, Boolean> conditionsMet = message.funnel.getMap("c", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);

			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
			final IDialogController controller = aePlayer.getModule(PlayerDialogModule.class);

			controller.setConditionsMetData(conditionsMet);
			controller.openScene(message.name, message.startingNodeId);
		}
	}
}
