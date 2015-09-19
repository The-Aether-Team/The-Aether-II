package com.gildedgames.aether.common.player;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import java.util.UUID;

public class PlayerAether implements IPlayerHook
{
	private final IPlayerProfile playerProfile;

	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private EntityPlayer player;

	private boolean isDirty;

	public PlayerAether(IPlayerProfile playerProfile, IPlayerHookPool<PlayerAether> playerHookPool)
	{
		this.playerProfile = playerProfile;
		this.playerHookPool = playerHookPool;
	}

	public static PlayerAether getPlayer(EntityPlayer player)
	{
		return AetherCore.locate().getPool().get(player);
	}

	public static PlayerAether getPlayer(UUID uuid)
	{
		return AetherCore.locate().getPool().get(uuid);
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public void onUpdate()
	{

	}

	@Override
	public boolean onLivingAttack(DamageSource source)
	{
		return true;
	}

	@Override
	public void onDeath()
	{

	}

	@Override
	public void onChangedDimension()
	{

	}

	@Override
	public void onRespawn()
	{

	}

	@Override
	public void write(NBTTagCompound output)
	{

	}

	@Override
	public void read(NBTTagCompound input)
	{

	}

	@Override
	public void syncTo(ByteBuf buf, SyncSide side)
	{

	}

	@Override
	public void syncFrom(ByteBuf buf, SyncSide side)
	{

	}

	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}

	@Override
	public void markDirty()
	{
		this.isDirty = true;
	}

	@Override
	public void markClean()
	{
		this.isDirty = false;
	}

	@Override
	public IPlayerHookPool getParentPool()
	{
		return this.playerHookPool;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.playerProfile;
	}
}
