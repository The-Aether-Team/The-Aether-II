package com.gildedgames.aether.common.entities.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityRewardItemStack extends EntityItem
{

	private static final DataParameter<String> PLAYER_NAME = EntityDataManager.createKey(EntityRewardItemStack.class, DataSerializers.STRING);

	private static final DataParameter<Boolean> IS_WHITELIST = EntityDataManager.createKey(EntityRewardItemStack.class, DataSerializers.BOOLEAN);

	public EntityRewardItemStack(World world, double x, double y, double z, String playerName, boolean whitelist)
	{
		super(world);

		this.setPlayerName(playerName);
		this.setWhitelistMode(whitelist);
	}

	public EntityRewardItemStack(World world, double x, double y, double z, ItemStack stack, String playerName, boolean whitelist)
	{
		super(world, x, y, z, stack);

		this.setPlayerName(playerName);
		this.setWhitelistMode(whitelist);
	}

	public EntityRewardItemStack(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(PLAYER_NAME, "");
		this.dataManager.register(IS_WHITELIST, true);
	}

	public void setWhitelistMode(boolean whitelist)
	{
		this.dataManager.set(IS_WHITELIST, whitelist);
	}

	public boolean isWhitelisted()
	{
		return this.dataManager.get(IS_WHITELIST);
	}

	public void setPlayerName(String playerName)
	{
		this.dataManager.set(PLAYER_NAME, playerName);
	}

	public String getPlayerName()
	{
		return this.dataManager.get(PLAYER_NAME);
	}

	public boolean combineItems(EntityItem entityItem)
	{
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setString("PlayerName", this.getPlayerName());
		tag.setBoolean("Whitelisted", this.isWhitelisted());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		this.setPlayerName(tag.getString("PlayerName"));
		this.setWhitelistMode(tag.getBoolean("Whitelisted"));
	}

	@Override
	public void onUpdate()
	{
		if (!this.isWhitelisted() && !this.onGround)
		{
			for (int sparkCount = 1; sparkCount <= 5; sparkCount++)
			{
				double motX = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();
				double motY = this.rand.nextDouble();
				double motZ = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();

				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX, this.posY, this.posZ, motX, motY, motZ);
			}

			if (this.ticksExisted % 5 == 0)
			{
				for (int sparkCount = 1; sparkCount <= 10; sparkCount++)
				{
					double motX = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();
					double motY = this.rand.nextDouble();
					double motZ = (this.rand.nextBoolean() ? -1 : 1) * this.rand.nextDouble();

					this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ,
							motX / 2, motY / 2, motZ / 2);
				}
			}
		}

		super.onUpdate();
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player)
	{
		if (!this.world.isRemote)
		{
			String username = player.getCommandSenderEntity().getName();

			if (this.getPlayerName() != null && (username.equalsIgnoreCase(this.getPlayerName()) && this.isWhitelisted()) || (
					!username.equalsIgnoreCase(this.getPlayerName()) && !this.isWhitelisted()))
			{
				super.onCollideWithPlayer(player);
			}
		}
	}
}
