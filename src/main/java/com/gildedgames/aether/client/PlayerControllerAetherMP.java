package com.gildedgames.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

// Don't look at me...
public class PlayerControllerAetherMP extends PlayerControllerMP
{
	private final PlayerControllerMP controller;

	private float extendedReachDistance;

	public PlayerControllerAetherMP(Minecraft mc, NetHandlerPlayClient netHandler, PlayerControllerMP controller)
	{
		super(mc, netHandler);

		this.controller = controller;
	}

	@Override
	public void setPlayerCapabilities(EntityPlayer player)
	{
		controller.setPlayerCapabilities(player);
	}

	@Override
	public boolean isSpectator()
	{
		return controller.isSpectator();
	}

	@Override
	public void setGameType(WorldSettings.GameType type)
	{
		controller.setGameType(type);
	}

	@Override
	public void flipPlayer(EntityPlayer playerIn)
	{
		controller.flipPlayer(playerIn);
	}

	@Override
	public boolean shouldDrawHUD()
	{
		return controller.shouldDrawHUD();
	}

	@Override
	public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side)
	{
		return controller.onPlayerDestroyBlock(pos, side);
	}

	@Override
	public boolean clickBlock(BlockPos loc, EnumFacing face)
	{
		return controller.clickBlock(loc, face);
	}

	@Override
	public void resetBlockRemoving()
	{
		controller.resetBlockRemoving();
	}

	@Override
	public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing)
	{
		return controller.onPlayerDamageBlock(posBlock, directionFacing);
	}

	@Override
	public float getBlockReachDistance()
	{
		return controller.getBlockReachDistance() + this.getExtendedBlockReachDistance();
	}

	public void setExtendedBlockReachDistance(float distance)
	{
		this.extendedReachDistance = distance;
	}

	public float getExtendedBlockReachDistance()
	{
		return this.extendedReachDistance;
	}

	@Override
	public void updateController()
	{
		controller.updateController();
	}

	@Override
	public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec)
	{
		return controller.onPlayerRightClick(player, worldIn, heldStack, hitPos, side, hitVec);
	}

	@Override
	public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn)
	{
		return controller.sendUseItem(playerIn, worldIn, itemStackIn);
	}

	@Override
	public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter statWriter)
	{
		return controller.func_178892_a(worldIn, statWriter);
	}

	@Override
	public void attackEntity(EntityPlayer playerIn, Entity targetEntity)
	{
		controller.attackEntity(playerIn, targetEntity);
	}

	@Override
	public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity)
	{
		return controller.interactWithEntitySendPacket(playerIn, targetEntity);
	}

	@Override
	public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity entity, MovingObjectPosition mos)
	{
		return controller.isPlayerRightClickingOnEntity(player, entity, mos);
	}

	@Override
	public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn)
	{
		return controller.windowClick(windowId, slotId, mouseButtonClicked, mode, playerIn);
	}

	@Override
	public void sendEnchantPacket(int windowID, int button)
	{
		controller.sendEnchantPacket(windowID, button);
	}

	@Override
	public void sendSlotPacket(ItemStack itemStackIn, int slotId)
	{
		controller.sendSlotPacket(itemStackIn, slotId);
	}

	@Override
	public void sendPacketDropItem(ItemStack itemStackIn)
	{
		controller.sendPacketDropItem(itemStackIn);
	}

	@Override
	public void onStoppedUsingItem(EntityPlayer playerIn)
	{
		controller.onStoppedUsingItem(playerIn);
	}

	@Override
	public boolean gameIsSurvivalOrAdventure()
	{
		return controller.gameIsSurvivalOrAdventure();
	}

	@Override
	public boolean isNotCreative()
	{
		return controller.isNotCreative();
	}

	@Override
	public boolean isInCreativeMode()
	{
		return controller.isInCreativeMode();
	}

	@Override
	public boolean extendedReach()
	{
		return controller.extendedReach();
	}

	@Override
	public boolean isRidingHorse()
	{
		return controller.isRidingHorse();
	}

	@Override
	public boolean isSpectatorMode()
	{
		return controller.isSpectatorMode();
	}

	@Override
	public WorldSettings.GameType getCurrentGameType()
	{
		return controller.getCurrentGameType();
	}

	@Override
	public boolean getIsHittingBlock()
	{
		return controller.getIsHittingBlock();
	}
}
