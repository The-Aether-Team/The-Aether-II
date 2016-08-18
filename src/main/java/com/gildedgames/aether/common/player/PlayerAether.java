package com.gildedgames.aether.common.player;

import com.gildedgames.aether.api.capabilites.AetherCapabilities;
import com.gildedgames.aether.api.entities.effects.EntityEffectInstance;
import com.gildedgames.aether.api.entities.effects.EntityEffectProcessor;
import com.gildedgames.aether.api.entities.effects.IEntityEffectsCapability;
import com.gildedgames.aether.api.items.IItemEffectsCapability;
import com.gildedgames.aether.api.player.IPlayerAetherCapability;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.DimensionsAether;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment;
import com.gildedgames.aether.common.containers.inventory.InventoryEquipment.PendingItemChange;
import com.gildedgames.aether.common.entities.blocks.EntityMovingBlock;
import com.gildedgames.aether.common.entities.effects.EntityEffects;
import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.items.armor.ItemAetherArmor;
import com.gildedgames.aether.common.items.tools.ItemGravititeTool;
import com.gildedgames.aether.common.items.tools.ItemValkyrieTool;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.EquipmentChangedPacket;
import com.gildedgames.aether.common.util.PlayerUtil;
import com.gildedgames.util.core.util.BlockPosDimension;
import com.gildedgames.util.core.util.TeleporterGeneric;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerAether implements IPlayerAetherCapability
{
	private final EntityPlayer player;

	private final InventoryEquipment equipmentInventory;

	private final PlayerCompanionManager companionManager;

	private EntityMovingBlock heldBlock;

	private boolean hasDoubleJumped;

	private int ticksAirborne;

	private BlockPosDimension bedSpawnpoint;

	public PlayerAether(EntityPlayer player)
	{
		this.player = player;

		this.equipmentInventory = new InventoryEquipment(this);
		this.companionManager = new PlayerCompanionManager(this);
	}

	public static PlayerAether getPlayer(Entity player)
	{
		return (PlayerAether) player.getCapability(AetherCapabilities.PLAYER_DATA, null);
	}

	@Override
	public void onUpdate(LivingUpdateEvent event)
	{
		this.updateAbilities();

		if (!this.player.worldObj.isRemote)
		{
			this.handleEquipmentChanges();

			if (!this.player.isDead)
			{
				this.updatePickedBlock();

				this.companionManager.update();
			}
		}

		AetherCore.PROXY.setExtendedReachDistance(this.player, this.calculateExtendedReach());
	}

	@Override
	public void onRespawn()
	{

	}

	private float calculateExtendedReach()
	{
		ItemStack stack = this.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

		if (stack != null)
		{
			Item item = stack.getItem();

			if (item instanceof ItemValkyrieTool || item == ItemsAether.valkyrie_lance)
			{
				return 3.5f;
			}
		}

		return 0.0f;
	}

	private void updateAbilities()
	{
		if (this.getPlayer().onGround)
		{
			this.hasDoubleJumped = false;
			this.ticksAirborne = 0;
		}
		else
		{
			this.ticksAirborne++;
		}

		if (PlayerUtil.isWearingEquipment(this, ItemsAether.obsidian_armor_set))
		{
			this.player.setSprinting(false);
		}
		else if (PlayerUtil.isWearingEquipment(this, ItemsAether.phoenix_armor_set))
		{
			this.player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2, 0, false, false));

			this.player.extinguish();
		}
		else if (PlayerUtil.isWearingEquipment(this, ItemsAether.neptune_armor_set))
		{
			if (this.player.isInsideOfMaterial(Material.WATER))
			{
				this.player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 2, 0, false, false));
			}
		}
	}

	private void updatePickedBlock()
	{
		if (this.heldBlock != null)
		{
			if (this.heldBlock.isDead || this.heldBlock.isFalling())
			{
				this.heldBlock = null;
			}
			else
			{
				ItemStack stack = this.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);

				// Check if the player is still using a gravitite tool
				if (!(stack != null && stack.getItem() instanceof ItemGravititeTool))
				{
					this.heldBlock.setHoldingPlayer(null);
				}
				else if (this.heldBlock.ticksExisted % 20 == 0)
				{
					// Does damage 2 damage/sec, increasing the amount of damage by 1 every 3 seconds,
					// for a maximum of 8 damage/sec

					int extra = (int) Math.floor(Math.min(6, this.heldBlock.ticksExisted / 60));

					stack.damageItem(2 + extra, this.player);
				}
			}
		}
	}

	private void sendEquipmentChanges(Set<PendingItemChange> dirties)
	{
		List<Pair<Integer, ItemStack>> updates = new ArrayList<>();

		for (PendingItemChange change : dirties)
		{
			updates.add(Pair.of(change.getSlot(), change.getAfter()));
		}

		NetworkingAether.sendPacketToWatching(new EquipmentChangedPacket(this.player, updates), this.player);
	}

	private void handleEquipmentChanges()
	{
		if (this.equipmentInventory.getDirties().size() > 0)
		{
			Set<PendingItemChange> changes = this.equipmentInventory.getDirties();

			this.sendEquipmentChanges(changes);

			IEntityEffectsCapability effects = EntityEffects.get(this.getPlayer());

			for (PendingItemChange change : changes)
			{
				ItemStack beforeStack = change.getBefore();

				if (beforeStack != null && beforeStack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
				{
					IItemEffectsCapability itemEffects = beforeStack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

					for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
					{
						EntityEffectProcessor processor = effect.getLeft();
						EntityEffectInstance instance = effect.getRight();

						effects.removeInstance(processor, instance);
					}
				}

				ItemStack afterStack = change.getAfter();

				if (afterStack != null && afterStack != beforeStack && afterStack.hasCapability(AetherCapabilities.ITEM_EFFECTS, null))
				{
					IItemEffectsCapability itemEffects = afterStack.getCapability(AetherCapabilities.ITEM_EFFECTS, null);

					for (Pair<EntityEffectProcessor, EntityEffectInstance> effect : itemEffects.getEffectPairs())
					{
						EntityEffectProcessor processor = effect.getLeft();
						EntityEffectInstance instance = effect.getRight();

						effects.addInstance(processor, instance);
					}
				}
			}

			changes.clear();
		}
	}

	@Override
	public void onDeath(LivingDeathEvent event)
	{
		this.companionManager.onDeath(event);

		this.dropHeldBlock();
	}

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
		if (PlayerUtil.isWearingEquipment(this, ItemsAether.gravitite_armor_set))
		{
			event.setResult(Result.DENY);
		}
	}

	@Override
	public void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		this.companionManager.onTeleport(event);
	}

	@Override
	public void onSpawned(PlayerEvent.PlayerLoggedInEvent event)
	{
		this.companionManager.onSpawned(event);
	}

	@Override
	public void onDespawn(PlayerEvent.PlayerLoggedOutEvent event)
	{
		this.companionManager.onDespawned(event);
	}

	@Override
	public InventoryEquipment getEquipmentInventory()
	{
		return this.equipmentInventory;
	}

	@Override
	public PlayerCompanionManager getCompanionManager()
	{
		return this.companionManager;
	}

	@Override
	public float getMiningSpeedMultiplier()
	{
		if (PlayerUtil.isWearingEquipment(this, ItemsAether.neptune_armor_set))
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

	@Override
	public int getTicksAirborne()
	{
		return this.ticksAirborne;
	}

	public boolean pickupBlock(BlockPos pos, World world)
	{
		if (this.heldBlock == null)
		{
			if (world.isBlockModifiable(this.player, pos))
			{
				IBlockState state = world.getBlockState(pos);

				EntityMovingBlock movingBlock = new EntityMovingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, state);
				world.spawnEntityInWorld(movingBlock);

				this.holdBlock(movingBlock);

				return true;
			}
		}

		return false;
	}

	private void holdBlock(EntityMovingBlock entity)
	{
		this.dropHeldBlock();

		this.heldBlock = entity;
		this.heldBlock.setHoldingPlayer(this.player);
	}

	public void dropHeldBlock()
	{
		if (this.getHeldBlock() != null)
		{
			this.getHeldBlock().setHoldingPlayer(null);
		}
	}

	public EntityMovingBlock getHeldBlock()
	{
		return this.heldBlock;
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
