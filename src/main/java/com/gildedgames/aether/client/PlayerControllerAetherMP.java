package com.gildedgames.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;

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

	public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP playerController, BlockPos pos, EnumFacing facing)
	{
		PlayerControllerMP.clickBlockCreative(mcIn, playerController, pos, facing);
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
	public void setGameType(GameType type)
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
	public boolean onPlayerDestroyBlock(BlockPos pos)
	{
		return baseController.onPlayerDestroyBlock(pos);
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

	@Override
	public void updateController()
	{
		baseController.updateController();
	}

	@Override
	public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, @Nullable ItemStack stack, BlockPos pos, EnumFacing facing, Vec3d vec, EnumHand hand)
	{
		return baseController.processRightClickBlock(player, worldIn, stack, pos, facing, vec, hand);
	}

	@Override
	public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand)
	{
		return baseController.processRightClick(player, worldIn, stack, hand);
	}

	@Override
	public EntityPlayerSP createClientPlayer(World worldIn, StatisticsManager statWriter)
	{
		return baseController.createClientPlayer(worldIn, statWriter);
	}

	@Override
	public void attackEntity(EntityPlayer playerIn, Entity targetEntity)
	{
		baseController.attackEntity(playerIn, targetEntity);
	}

	@Override
	public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, @Nullable ItemStack heldItem, EnumHand hand)
	{
		return baseController.interactWithEntity(player, target, heldItem, hand);
	}

	@Override
	public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, RayTraceResult raytrace, @Nullable ItemStack heldItem, EnumHand hand)
	{
		return baseController.interactWithEntity(player, target, raytrace, heldItem, hand);
	}

	@Override
	public ItemStack windowClick(int windowId, int slotId, int mouseButton, ClickType type, EntityPlayer player)
	{
		return baseController.windowClick(windowId, slotId, mouseButton, type, player);
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
	public GameType getCurrentGameType()
	{
		return baseController.getCurrentGameType();
	}

	@Override
	public boolean getIsHittingBlock()
	{
		return baseController.getIsHittingBlock();
	}

	@Override
	public void pickItem(int index)
	{
		baseController.pickItem(index);
	}

	public void setExtendedBlockReachDistance(float distance)
	{
		this.extendedReachDistance = distance;
	}

	public float getExtendedBlockReachDistance()
	{
		return this.extendedReachDistance;
	}
}
