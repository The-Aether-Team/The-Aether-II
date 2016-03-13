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

	public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP p_178891_1_, BlockPos p_178891_2_, EnumFacing p_178891_3_)
	{
		PlayerControllerMP.clickBlockCreative(mcIn, p_178891_1_, p_178891_2_, p_178891_3_);
	}

	@Override
	public void setPlayerCapabilities(EntityPlayer p_78748_1_)
	{
		controller.setPlayerCapabilities(p_78748_1_);
	}

	@Override
	public boolean isSpectator()
	{
		return controller.isSpectator();
	}

	@Override
	public void setGameType(WorldSettings.GameType p_78746_1_)
	{
		controller.setGameType(p_78746_1_);
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
	public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter p_178892_2_)
	{
		return controller.func_178892_a(worldIn, p_178892_2_);
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
	public boolean func_178894_a(EntityPlayer p_178894_1_, Entity p_178894_2_, MovingObjectPosition p_178894_3_)
	{
		return controller.func_178894_a(p_178894_1_, p_178894_2_, p_178894_3_);
	}

	@Override
	public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn)
	{
		return controller.windowClick(windowId, slotId, mouseButtonClicked, mode, playerIn);
	}

	@Override
	public void sendEnchantPacket(int p_78756_1_, int p_78756_2_)
	{
		controller.sendEnchantPacket(p_78756_1_, p_78756_2_);
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
	public boolean func_181040_m()
	{
		return controller.func_181040_m();
	}
}
