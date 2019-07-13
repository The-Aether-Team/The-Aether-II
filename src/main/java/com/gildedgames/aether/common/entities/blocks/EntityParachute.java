package com.gildedgames.aether.common.entities.blocks;

import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerParachuteModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

public class EntityParachute extends Entity
{

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityParachute.class, DataSerializers.VARINT);

	private PlayerEntity parachutingPlayer;

	public EntityParachute(final World world)
	{
		super(world);

		this.remove();
	}

	public EntityParachute(final World world, final PlayerEntity player, final Type type)
	{
		super(world);

		this.setType(type);
		this.parachutingPlayer = player;

		this.setPosition(player.posX, player.posY, player.posZ);
	}

	@Override
	public void registerData()
	{
		this.dataManager.register(EntityParachute.TYPE, 0);
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (this.getRidingEntity() == null)
		{
			this.startRiding(this.parachutingPlayer, true);
		}

		if (this.getRidingEntity() instanceof PlayerEntity)
		{
			final PlayerEntity player = (PlayerEntity) this.getRidingEntity();
			final Vec3d vec3 = player.getLookVec();

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
				player.motionX = vec3.x * 0.18f;
				player.motionY = -0.08f;
				player.motionZ = vec3.z * 0.18f;
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

			final PlayerAether playerAether = PlayerAether.getPlayer(player);

			if (!playerAether.getModule(PlayerParachuteModule.class).isParachuting())
			{
				this.remove();
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
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

	public void setType(final Type type)
	{
		this.dataManager.set(EntityParachute.TYPE, type.ordinal());
	}

	public PlayerEntity getParachutingPlayer()
	{
		return this.parachutingPlayer;
	}

	@Override
	public void readEntityFromNBT(final CompoundNBT tag)
	{
		this.setType(Type.fromOrdinal(tag.getInt("type")));
	}

	@Override
	public void writeEntityToNBT(final CompoundNBT tag)
	{
		tag.putInt("type", this.getType().ordinal());
	}

	@Override
	public boolean attackEntityFrom(final DamageSource source, final float damage)
	{
		return source.getTrueSource() != this.getRidingEntity();
	}

	public enum Type
	{
		COLD("cold"), GOLDEN("golden"), PURPLE("purple"), GREEN("green"), BLUE("blue");

		public final String name, desc;

		public final ResourceLocation texture;

		Type(final String name)
		{
			this.name = name;
			this.desc = "cloudParachute.ability." + this.name;

			this.texture = AetherCore.getResource("textures/entities/parachute/parachute_" + this.name + ".png");
		}

		public static Type fromOrdinal(final int ordinal)
		{
			final Type[] type = values();

			return type[ordinal > type.length || ordinal < 0 ? 0 : ordinal];
		}
	}

}
