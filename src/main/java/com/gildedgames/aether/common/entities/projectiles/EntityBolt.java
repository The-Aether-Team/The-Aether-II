package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.common.ReflectionAether;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.weapons.crossbow.ItemBoltType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBolt extends EntityArrow
{
	private static final DataParameter<Byte> TYPE = new DataParameter<>(20, DataSerializers.BYTE);

	private static final DataParameter<Byte> ABILITY = new DataParameter<>(21, DataSerializers.BYTE);

	public enum BoltAbility
	{
		NORMAL
	}

	public EntityBolt(World worldIn)
	{
		super(worldIn);
	}

	public EntityBolt(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.bolt, 1, this.getBoltType().ordinal());
	}

	@Override
	protected void arrowHit(EntityLivingBase living)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(TYPE, (byte) 0);
		this.dataManager.register(ABILITY, (byte) 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender(float partialTicks)
	{
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

		if (this.world.isBlockLoaded(blockpos$mutableblockpos))
		{
			blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double) this.getEyeHeight()));
			return this.world.getCombinedLight(blockpos$mutableblockpos, 0);
		}
		else
		{
			return 0;
		}
	}

	public void setBoltAbility(BoltAbility ability)
	{
		this.dataManager.set(ABILITY, (byte) ability.ordinal());
	}

	public BoltAbility getBoltAbility()
	{
		return BoltAbility.values()[this.dataManager.get(ABILITY)];
	}

	public void setBoltType(ItemBoltType type)
	{
		this.dataManager.set(TYPE, (byte) type.ordinal());

		ObfuscationReflectionHelper.setPrivateValue(EntityArrow.class, this, type.getDamage(), ReflectionAether.ENTITY_ARROW_DAMAGE.getMappings());
	}

	public ItemBoltType getBoltType()
	{
		return ItemBoltType.values()[this.dataManager.get(TYPE)];
	}
}
