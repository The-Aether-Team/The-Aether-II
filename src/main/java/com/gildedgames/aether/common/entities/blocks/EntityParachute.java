package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.common.capabilities.player.PlayerAetherImpl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityParachute extends Entity
{

	public enum Type
	{
		COLD("cold"), GOLDEN("golden"), PURPLE("purple"), GREEN("green"), BLUE("blue");

		public final String name, desc;

		public final ResourceLocation texture;

		Type(String name)
		{
			this.name = name;
			this.desc = "cloudParachute.ability." + this.name;

			this.texture = new ResourceLocation("aether", "textures/entities/parachute/parachute_" + this.name + ".png");
		}

		public static Type fromOrdinal(int ordinal)
		{
			Type[] type = values();

			return type[ordinal > type.length || ordinal < 0 ? 0 : ordinal];
		}
	}

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityParachute.class, DataSerializers.VARINT);

	private EntityPlayer parachutingPlayer;

	public EntityParachute(World world)
	{
		super(world);

		this.setDead();
	}

	public EntityParachute(World world, EntityPlayer player, Type type)
	{
		super(world);

		this.setType(type);
		this.parachutingPlayer = player;

		this.setPosition(player.posX, player.posY, player.posZ);
	}

	@Override
	public void entityInit()
	{
		this.dataManager.register(EntityParachute.TYPE, 0);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.getRidingEntity() == null)
		{
			this.startRiding(this.parachutingPlayer, true);
		}

		if (this.getRidingEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) this.getRidingEntity();
			Vec3d vec3 = player.getLookVec();

			if (this.getType() == Type.COLD)
			{
				player.motionX *= 0.6;
				player.motionY = -0.08;
				player.motionZ *= 0.6;
			}

			if (this.getType() == Type.GOLDEN)
			{
				player.motionX *= 0.6;
				player.motionY = -1.08;
				player.motionZ *= 0.6;
			}

			if (this.getType() == Type.PURPLE)
			{
				player.motionX = vec3.xCoord * 0.18f;
				player.motionY = -0.08f;
				player.motionZ = vec3.zCoord * 0.18f;
			}

			if (this.getType() == Type.GREEN)
			{
				player.motionY = -0.08f;
			}

			if (this.getType() == Type.BLUE)
			{
				player.motionX *= 0.6;
				player.motionY = 1.08;
				player.motionZ *= 0.6;
			}

			player.isAirBorne = true;

			PlayerAetherImpl playerAether = PlayerAetherImpl.getPlayer(player);

			if (!playerAether.getParachuteModule().isParachuting())
			{
				this.setDead();
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	public Type getType()
	{
		return Type.fromOrdinal(this.dataManager.get(EntityParachute.TYPE));
	}

	public void setType(Type type)
	{
		this.dataManager.set(EntityParachute.TYPE, type.ordinal());
	}

	public EntityPlayer getParachutingPlayer()
	{
		return this.parachutingPlayer;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		this.setType(Type.fromOrdinal(tag.getInteger("type")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("type", this.getType().ordinal());
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		if (source.getEntity() != this.getRidingEntity())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
