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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

	@Override
	public void setPlayerCapabilities(EntityPlayer player)
	{
		this.baseController.setPlayerCapabilities(player);
	}

	@Override
	public boolean isSpectator()
	{
		return this.baseController.isSpectator();
	}

	@Override
	public void setGameType(GameType type)
	{
		this.baseController.setGameType(type);
	}

	@Override
	public void flipPlayer(EntityPlayer playerIn)
	{
		this.baseController.flipPlayer(playerIn);
	}

	@Override
	public boolean shouldDrawHUD()
	{
		return this.baseController.shouldDrawHUD();
	}

	@Override
	public boolean onPlayerDestroyBlock(BlockPos pos)
	{
		return this.baseController.onPlayerDestroyBlock(pos);
	}

	@Override
	public boolean clickBlock(BlockPos loc, EnumFacing face)
	{
		return this.baseController.clickBlock(loc, face);
	}

	@Override
	public void resetBlockRemoving()
	{
		this.baseController.resetBlockRemoving();
	}

	@Override
	public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing)
	{
		return this.baseController.onPlayerDamageBlock(posBlock, directionFacing);
	}

	@Override
	public float getBlockReachDistance()
	{
		return this.baseController.getBlockReachDistance() + this.getExtendedBlockReachDistance();
	}

	@Override
	public void updateController()
	{
		this.baseController.updateController();
	}

	@Override
	public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, @Nullable ItemStack stack, BlockPos pos, EnumFacing facing, Vec3d vec, EnumHand hand)
	{
		return this.baseController.processRightClickBlock(player, worldIn, stack, pos, facing, vec, hand);
	}

	@Override
	public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand)
	{
		return this.baseController.processRightClick(player, worldIn, stack, hand);
	}

	@Override
	public EntityPlayerSP createClientPlayer(World worldIn, StatisticsManager statWriter)
	{
		return this.baseController.createClientPlayer(worldIn, statWriter);
	}

	@Override
	public void attackEntity(EntityPlayer playerIn, Entity targetEntity)
	{
		this.baseController.attackEntity(playerIn, targetEntity);
	}

	@Override
	public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, @Nullable ItemStack heldItem, EnumHand hand)
	{
		return this.baseController.interactWithEntity(player, target, heldItem, hand);
	}

	@Override
	public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, RayTraceResult raytrace, @Nullable ItemStack heldItem, EnumHand hand)
	{
		return this.baseController.interactWithEntity(player, target, raytrace, heldItem, hand);
	}

	@Override
	public ItemStack windowClick(int windowId, int slotId, int mouseButton, ClickType type, EntityPlayer player)
	{
		return this.baseController.windowClick(windowId, slotId, mouseButton, type, player);
	}

	@Override
	public void sendEnchantPacket(int windowID, int button)
	{
		this.baseController.sendEnchantPacket(windowID, button);
	}

	@Override
	public void sendSlotPacket(ItemStack itemStackIn, int slotId)
	{
		this.baseController.sendSlotPacket(itemStackIn, slotId);
	}

	@Override
	public void sendPacketDropItem(ItemStack itemStackIn)
	{
		this.baseController.sendPacketDropItem(itemStackIn);
	}

	@Override
	public void onStoppedUsingItem(EntityPlayer playerIn)
	{
		this.baseController.onStoppedUsingItem(playerIn);
	}

	@Override
	public boolean gameIsSurvivalOrAdventure()
	{
		return this.baseController.gameIsSurvivalOrAdventure();
	}

	@Override
	public boolean isNotCreative()
	{
		return this.baseController.isNotCreative();
	}

	@Override
	public boolean isInCreativeMode()
	{
		return this.baseController.isInCreativeMode();
	}

	@Override
	public boolean extendedReach()
	{
		return this.baseController.extendedReach();
	}

	@Override
	public boolean isRidingHorse()
	{
		return this.baseController.isRidingHorse();
	}

	@Override
	public boolean isSpectatorMode()
	{
		return this.baseController.isSpectatorMode();
	}

	@Override
	public GameType getCurrentGameType()
	{
		return this.baseController.getCurrentGameType();
	}

	@Override
	public boolean getIsHittingBlock()
	{
		return this.baseController.getIsHittingBlock();
	}

	@Override
	public void pickItem(int index)
	{
		this.baseController.pickItem(index);
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
