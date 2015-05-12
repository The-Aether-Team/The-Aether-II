package com.gildedgames.aether.player;

import com.gildedgames.util.player.common.IPlayerHookPool;
import com.gildedgames.util.player.common.player.IPlayerHook;
import com.gildedgames.util.player.common.player.IPlayerProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class PlayerAether implements IPlayerHook
{

	private final IPlayerProfile playerProfile;

	private final IPlayerHookPool<PlayerAether> playerHookPool;

	private boolean isDirty;

	private EntityPlayer player;

	public PlayerAether(IPlayerProfile playerProfile, IPlayerHookPool<PlayerAether> playerHookPool)
	{
		this.playerProfile = playerProfile;
		this.playerHookPool = playerHookPool;
	}

	@Override
	public IPlayerHookPool getParentPool()
	{
		return this.playerHookPool;
	}

	@Override
	public void entityInit(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public IPlayerProfile getProfile()
	{
		return this.playerProfile;
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
	public void syncTo(ByteBuf output, SyncSide to)
	{

	}

	@Override
	public void syncFrom(ByteBuf input, SyncSide from)
	{

	}
}
