package com.gildedgames.aether.common.capabilities.entity.player.modules;

import com.gildedgames.aether.client.gui.dialog.GuiTrade;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAetherModule;
import com.gildedgames.aether.common.containers.ContainerTrade;
import com.gildedgames.aether.common.network.AetherGuiHandler;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketCloseScreen;
import com.gildedgames.aether.common.network.packets.trade.PacketTradeInitial;
import com.gildedgames.aether.common.network.packets.trade.PacketTradeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTradeModule extends PlayerAetherModule
{

	private PlayerAether target;

	private int requestedTime, failTime, openSlots, tradeSlots;

	private boolean isTrading, lockedIn, confirmed, error;

	private double coinCount, tradeValue;

	private Vec3i requestPosition;

	public PlayerTradeModule(final PlayerAether playerAether)
	{
		super(playerAether);
	}

	@Override
	public void tickStart(TickEvent.PlayerTickEvent event)
	{
	}

	@Override
	public void tickEnd(TickEvent.PlayerTickEvent event)
	{
	}

	@Override
	public void onUpdate()
	{
		if (this.requestedTime > 0)
		{
			this.requestedTime--;

			if (!this.getWorld().isRemote && this.requestedTime == 0 && this.target != null)
			{
				ITextComponent message = new TextComponentTranslation("aether.trade.chat.failexpired", this.target.getEntity().getDisplayName().getFormattedText());

				message.setStyle(new Style().setColor(TextFormatting.RED).setItalic(true));
				this.getEntity().sendMessage(message);
			}
		}

		if (this.failTime > 0)
		{
			this.failTime--;
		}
	}

	@Override
	public void write(final NBTTagCompound output)
	{
	}

	@Override
	public void read(final NBTTagCompound input)
	{
	}

	public PlayerAether getTarget()
	{
		return this.target;
	}

	public boolean isTrading()
	{
		return this.isTrading && this.target != null && this.getPlayer().equals(this.target.getTradingModule().target);
	}

	public void setConfirmed(boolean confirmed)
	{
		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketTradeState((byte) 2));
		}
		else
		{
			this.confirmed = confirmed;
			this.sendState(3);
		}
	}

	public boolean isConfirmed()
	{
		return this.confirmed;
	}

	public void setLockedIn(boolean lockedIn)
	{
		this.lockedIn = lockedIn;
		this.confirmed = false;

		if (this.getWorld().isRemote)
		{
			NetworkingAether.sendPacketToServer(new PacketTradeState((byte) (this.lockedIn ? 0 : 1)));
		}
		else
		{
			this.sendState(0);
		}
	}

	public boolean isLockedIn()
	{
		return this.lockedIn;
	}

	public boolean canAccept(BlockPos pos)
	{
		return !this.isTrading && this.requestPosition.distanceSq(pos) < 100 && this.requestedTime > 0;
	}

	public void sendState(int state)
	{
		if (!this.getWorld().isRemote)
		{
			PlayerTradeModule targetTrade = this.target.getTradingModule();

			if (this.error)
			{
				state = -1;

				this.lockedIn = false;
				this.confirmed = false;
			}
			else if (state == 0)
			{
				if (targetTrade.isLockedIn())
				{
					state += 1;
				}

				if (this.lockedIn)
				{
					state += 2;
				}
			}
			else if (state == 3)
			{
				if (targetTrade.isConfirmed())
				{
					state += 1;
				}

				if (this.confirmed)
				{
					state += 2;
				}
			}

			NetworkingAether.sendPacketToPlayer(new PacketTradeState((byte) state), this.getPlayerMP());
		}
	}

	public void request(PlayerAether other)
	{
		this.target = other;
		this.requestedTime = 20 * 20;
		this.requestPosition = this.getEntity().getPosition();

		ITextComponent message = new TextComponentTranslation("aether.trade.chat.request", this.getEntity().getDisplayName().getFormattedText());

		message.setStyle(new Style().setColor(TextFormatting.AQUA).setItalic(true));
		other.getEntity().sendMessage(message);
	}

	public void clear()
	{
		this.failTime = 0;
		this.requestedTime = 0;
		this.coinCount = 0;
		this.confirmed = false;
		this.lockedIn = false;
	}

	public void accept()
	{
		this.isTrading = true;
		this.clear();
		this.openGui();
	}

	public void setTrading(PlayerTradeModule other)
	{
		this.clear();

		if (other == null)
		{
			this.isTrading = false;
			this.target = null;
		}
		else
		{
			this.isTrading = true;
			this.target = other.getPlayer();
			this.openGui();
		}
	}

	private void openGui()
	{
		BlockPos pos = this.getEntity().getPosition();

		this.getEntity().openGui(AetherCore.MOD_ID, AetherGuiHandler.TRADE_ID, this.getWorld(), pos.getX(), pos.getY(), pos.getZ());

		NetworkingAether.sendPacketToPlayer(new PacketTradeInitial(this.target.getEntity().getEntityId()), this.getPlayerMP());
	}

	public void closeGui()
	{
		NetworkingAether.sendPacketToPlayer(new PacketCloseScreen(AetherGuiHandler.TRADE_ID), (EntityPlayerMP) this.getEntity());
	}

	public boolean canRequest()
	{
		return this.requestedTime == 0;
	}

	public int getFailTime()
	{
		return this.failTime;
	}

	public void failRequest(PlayerTradeModule other)
	{
		ITextComponent message = new TextComponentTranslation("aether.trade.chat.fail" + (other.isTrading() ? "trade" : "sent"), other.getEntity().getDisplayName().getFormattedText());
		message.setStyle(new Style().setColor(TextFormatting.RED).setItalic(true));

		this.getEntity().sendMessage(message);

		this.failTime = 30;
	}

	public void endTrade(PlayerAether aePlayer)
	{
		if (this.isTrading() && aePlayer.equals(this.getTarget()))
		{
			this.setTrading(null);

			if (!this.getWorld().isRemote)
			{
				EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.getPlayer().getEntity();

				entityPlayerMP.closeContainer();
				NetworkingAether.sendPacketToPlayer(new PacketCloseScreen(AetherGuiHandler.TRADE_ID), entityPlayerMP);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateClientState(int state)
	{
		Minecraft mc = Minecraft.getMinecraft();

		this.lockedIn = state >= 2;

		if (mc.currentScreen instanceof GuiTrade)
		{
			GuiTrade tradeGui = (GuiTrade) mc.currentScreen;

			tradeGui.setTradeState(state);
		}
	}


	public int getTradeSlots()
	{
		return this.tradeSlots;
	}

	public void setTradeSlots(int size)
	{
		this.tradeSlots = size;

		if (this.getWorld().isRemote)
		{
			this.setTradeSlotsClient(size);
		}
		else if (this.getEntity().openContainer instanceof ContainerTrade)
		{
			ContainerTrade tradeContainer = (ContainerTrade) this.getEntity().openContainer;

			tradeContainer.updateSlots(size);
		}
	}

	@SideOnly(Side.CLIENT)
	private void setTradeSlotsClient(int size) {
		GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;

		if (currentScreen instanceof GuiTrade)
		{
			ContainerTrade tradeContainer = (ContainerTrade) ((GuiTrade) currentScreen).inventorySlots;

			tradeContainer.updateSlots(size);
		}
	}

	public EntityPlayerMP getPlayerMP()
	{
		return (EntityPlayerMP) this.getEntity();
	}


	public EntityPlayerMP getTargetMP()
	{
		return (EntityPlayerMP) this.getTarget().getEntity();
	}

	public int getOpenSlots()
	{
		return this.openSlots;
	}

	public void setOpenSlots(int openSlots)
	{
		this.openSlots = openSlots;
	}

	public void setTarget(PlayerAether playerAether)
	{
		this.target = playerAether;
	}

	public void sizeError(boolean error)
	{
		this.error = error;
		this.sendState(0);
	}

	public void setCoinAmount(double coinCount)
	{
		this.coinCount = coinCount;
	}

	public double getCoinAmount()
	{
		return this.coinCount;
	}

	public void setValue(double value)
	{
		this.tradeValue = value;
	}

	public double getValue()
	{
		return this.tradeValue;
	}
}
