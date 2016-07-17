package com.gildedgames.aether.common.player;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.api.player.inventory.IInventoryEquipment;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.armor.ItemGravititeArmor;
import com.gildedgames.aether.common.items.armor.ItemNeptuneArmor;
import com.gildedgames.aether.common.items.tools.ItemValkyrieTool;
import com.gildedgames.aether.common.util.PlayerUtil;

public class PlayerAether implements IPlayerAetherCapability
{
	private final EntityPlayer player;

	private InventoryEquipment equipmentInventory;
	
	private BlockPos linkingSchematicBoundary;

	private boolean hasDoubleJumped;

	private int ticksAirborne;
	
	private EntityMovingBlock pickedBlock;

	public PlayerAether(EntityPlayer player)
	{
		this.player = player;
		this.equipmentInventory = new InventoryEquipment(this);
	}

	public static IPlayerAetherCapability getPlayer(Entity player)
	{
		return player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}
	
	@Override
	public void setLinkingSchematicBoundary(BlockPos pos)
	{
		this.linkingSchematicBoundary = pos;
	}
	
	@Override
	public BlockPos getLinkingSchematicBoundary()
	{
		return this.linkingSchematicBoundary;
	}

	@Override
	public void onUpdate(LivingUpdateEvent event)
	{
		float extendedReach = 0.0f;

		if (this.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != null)
		{
			Item item = this.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem();

			if (item instanceof ItemValkyrieTool || item == ItemsAether.valkyrie_lance)
			{
				extendedReach = 3.5f;
			}
		}

		if (this.getPlayer().onGround)
		{
			this.hasDoubleJumped = false;
			this.ticksAirborne = 0;
		}
		else
		{
			this.ticksAirborne++;
		}

		AetherCore.PROXY.setExtendedReachDistance(this.player, extendedReach);
	}

	@Override
	public void onDeath(LivingDeathEvent event) { }

	@Override
	public void onDrops(LivingDropsEvent event)
	{
		if (!this.player.getEntityWorld().getGameRules().getBoolean("keepInventory"))
		{
			this.player.captureDrops = true;

			this.equipmentInventory.dropAllItems();

			this.player.captureDrops = false;
		}
	}

	@Override
	public void onHurt(LivingHurtEvent event)
	{
		if (!event.getSource().isUnblockable())
		{
			for (ItemStack stack : this.player.inventory.armorInventory)
			{
				if (stack != null && stack.getItem() instanceof ItemAetherArmor)
				{
					event.setAmount(event.getAmount() - ((ItemAetherArmor) stack.getItem()).getExtraDamageReduction(stack));
				}
			}
		}
	}

	@Override
	public void onFall(LivingFallEvent event)
	{
		Class<? extends Item> fullSet = PlayerUtil.findArmorSet(player);

		if (fullSet == ItemGravititeArmor.class)
		{
			event.setResult(Result.DENY);
		}
	}

	@Override
	public void onJump(LivingJumpEvent event)
	{

	}

	@Override
	public IInventoryEquipment getEquipmentInventory()
	{
		return this.equipmentInventory;
	}

	@Override
	public float getMiningSpeedMultiplier()
	{
		if (PlayerUtil.isWearingFullSet(this.player, ItemNeptuneArmor.class))
		{
			if (!EnchantmentHelper.getAquaAffinityModifier(this.player) && this.player.isInsideOfMaterial(Material.WATER))
			{
				return 5.0f;
			}
		}

		return 1.0f;
	}

	@Override
	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public boolean performDoubleJump()
	{
		if (!this.hasDoubleJumped && this.ticksAirborne > 2)
		{
			AetherCore.PROXY.spawnJumpParticles(this.getPlayer().worldObj, this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, 1.5D, 12);

			this.getPlayer().motionY = 0.55D;
			this.getPlayer().fallDistance = -4;

			this.getPlayer().motionX *= 1.4D;
			this.getPlayer().motionZ *= 1.4D;

			this.hasDoubleJumped = true;

			return true;
		}

		return false;
	}

	public int getTicksAirborne()
	{
		return this.ticksAirborne;
	}
	
	public EntityMovingBlock getPickedBlock()
	{
		return this.pickedBlock;
	}
	
	public void dropBlock()
	{
		if (this.pickedBlock != null)
		{
			this.pickedBlock.drop();
			
			this.pickedBlock = null;
		}
	}
	
	public boolean pickupBlock(BlockPos pos, World world)
	{
		if (world.isRemote)
		{
			return false;
		}
		
		IBlockState state = world.getBlockState(pos);
		
		if (state != Blocks.AIR.getDefaultState())
		{
			this.pickedBlock = new EntityMovingBlock(world, pos.getX(), pos.getY(), pos.getZ(), state, this.player);
			
			world.spawnEntityInWorld(this.pickedBlock);
			
			return true;
		}
		
		return false;
	}

	public static class Storage implements IStorage<IPlayerAetherCapability>
	{
		@Override
		public NBTBase writeNBT(Capability<IPlayerAetherCapability> capability, IPlayerAetherCapability instance, EnumFacing side)
		{
			NBTTagCompound compound = new NBTTagCompound();

			NBTTagCompound equipment = new NBTTagCompound();
			instance.getEquipmentInventory().write(equipment);

			compound.setTag("equipment", equipment);

			return compound;
		}

		@Override
		public void readNBT(Capability<IPlayerAetherCapability> capability, IPlayerAetherCapability instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound compound = (NBTTagCompound) nbt;

			if (compound.hasKey("equipment"))
			{
				instance.getEquipmentInventory().read(compound.getCompoundTag("equipment"));
			}
		}
	}
}
