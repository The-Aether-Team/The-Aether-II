package com.gildedgames.aether.common.network.packets.dialog;

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

import java.util.Map;

public class PacketConditionsMetData implements IMessage
{
	private Map<String, Boolean> conditionsMet;

	private NBTFunnel funnel;

	public PacketConditionsMetData(Map<String, Boolean> conditionsMet)
	{
		this.conditionsMet = conditionsMet;
	}

	@Override
	public void fromBytes(final PacketBuffer buf)
	{
		this.funnel = new NBTFunnel(buf.readCompoundTag());
	}

	@Override
	public void toBytes(final PacketBuffer buf)
	{
		CompoundNBT tag = new CompoundNBT();
		NBTFunnel funnel = new NBTFunnel(tag);

		funnel.setMap("c", this.conditionsMet, NBTFunnel.STRING_SETTER, NBTFunnel.BOOLEAN_SETTER);

		buf.writeCompoundTag(tag);
	}

	public static class HandlerClient extends MessageHandlerClient<PacketConditionsMetData>
	{
		@Override
		protected void onMessage(PacketConditionsMetData message, ClientPlayerEntity player)
		{
			final IPlayerAether aePlayer = PlayerAether.getPlayer(player);

			Map<String, Boolean> conditionsMet = message.funnel.getMap("c", NBTFunnel.STRING_GETTER, NBTFunnel.BOOLEAN_GETTER);

			if (conditionsMet != null)
			{
				aePlayer.getModule(PlayerDialogModule.class).setConditionsMetData(conditionsMet);
			}
		}
	}

}
