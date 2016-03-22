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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

// Don't look at me...
public class PlayerControllerAetherMP extends PlayerControllerMP
{
	private final PlayerControllerMP baseController;

	private float extendedReachDistance;

	public PlayerControllerAetherMP(Minecraft mc, NetHandlerPlayClient netHandler, PlayerControllerMP baseController)
	{
		super(mc, netHandler);

		this.baseController = baseController;
	}

	public static PlayerControllerAetherMP create(PlayerControllerMP controller)
	{
		NetHandlerPlayClient netHandler = ObfuscationReflectionHelper.getPrivateValue(PlayerControllerMP.class, controller, "field_78774_b", "netClientHandler");

		return new PlayerControllerAetherMP(Minecraft.getMinecraft(), netHandler, controller);
	}

	@Override
	public void setPlayerCapabilities(EntityPlayer player)
	{
		baseController.setPlayerCapabilities(player);
	}

	@Override
	public boolean isSpectator()
	{
		return baseController.isSpectator();
	}

	@Override
	public void setGameType(WorldSettings.GameType type)
	{
		baseController.setGameType(type);
	}

	@Override
	public void flipPlayer(EntityPlayer playerIn)
	{
		baseController.flipPlayer(playerIn);
	}

	@Override
	public boolean shouldDrawHUD()
	{
		return baseController.shouldDrawHUD();
	}

	@Override
	public boolean onPlayerDestroyBlock(BlockPos pos, EnumFacing side)
	{
		return baseController.onPlayerDestroyBlock(pos, side);
	}

	@Override
	public boolean clickBlock(BlockPos loc, EnumFacing face)
	{
		return baseController.clickBlock(loc, face);
	}

	@Override
	public void resetBlockRemoving()
	{
		baseController.resetBlockRemoving();
	}

	@Override
	public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing)
	{
		return baseController.onPlayerDamageBlock(posBlock, directionFacing);
	}

	@Override
	public float getBlockReachDistance()
	{
		return baseController.getBlockReachDistance() + this.getExtendedBlockReachDistance();
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
		baseController.updateController();
	}

	@Override
	public boolean onPlayerRightClick(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 hitVec)
	{
		return baseController.onPlayerRightClick(player, worldIn, heldStack, hitPos, side, hitVec);
	}

	@Override
	public boolean sendUseItem(EntityPlayer playerIn, World worldIn, ItemStack itemStackIn)
	{
		return baseController.sendUseItem(playerIn, worldIn, itemStackIn);
	}

	@Override
	public EntityPlayerSP func_178892_a(World worldIn, StatFileWriter statWriter)
	{
		return baseController.func_178892_a(worldIn, statWriter);
	}

	@Override
	public void attackEntity(EntityPlayer playerIn, Entity targetEntity)
	{
		baseController.attackEntity(playerIn, targetEntity);
	}

	@Override
	public boolean interactWithEntitySendPacket(EntityPlayer playerIn, Entity targetEntity)
	{
		return baseController.interactWithEntitySendPacket(playerIn, targetEntity);
	}

	@Override
	public boolean isPlayerRightClickingOnEntity(EntityPlayer player, Entity entity, MovingObjectPosition mos)
	{
		return baseController.isPlayerRightClickingOnEntity(player, entity, mos);
	}

	@Override
	public ItemStack windowClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn)
	{
		return baseController.windowClick(windowId, slotId, mouseButtonClicked, mode, playerIn);
	}

	@Override
	public void sendEnchantPacket(int windowID, int button)
	{
		baseController.sendEnchantPacket(windowID, button);
	}

	@Override
	public void sendSlotPacket(ItemStack itemStackIn, int slotId)
	{
		baseController.sendSlotPacket(itemStackIn, slotId);
	}

	@Override
	public void sendPacketDropItem(ItemStack itemStackIn)
	{
		baseController.sendPacketDropItem(itemStackIn);
	}

	@Override
	public void onStoppedUsingItem(EntityPlayer playerIn)
	{
		baseController.onStoppedUsingItem(playerIn);
	}

	@Override
	public boolean gameIsSurvivalOrAdventure()
	{
		return baseController.gameIsSurvivalOrAdventure();
	}

	@Override
	public boolean isNotCreative()
	{
		return baseController.isNotCreative();
	}

	@Override
	public boolean isInCreativeMode()
	{
		return baseController.isInCreativeMode();
	}

	@Override
	public boolean extendedReach()
	{
		return baseController.extendedReach();
	}

	@Override
	public boolean isRidingHorse()
	{
		return baseController.isRidingHorse();
	}

	@Override
	public boolean isSpectatorMode()
	{
		return baseController.isSpectatorMode();
	}

	@Override
	public WorldSettings.GameType getCurrentGameType()
	{
		return baseController.getCurrentGameType();
	}

	@Override
	public boolean getIsHittingBlock()
	{
		return baseController.getIsHittingBlock();
	}

	public PlayerControllerMP getBaseController()
	{
		return this.baseController;
	}
}
