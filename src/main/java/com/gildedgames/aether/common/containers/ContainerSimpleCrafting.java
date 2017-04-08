package com.gildedgames.aether.common.containers;

import com.gildedgames.aether.api.registry.simple_crafting.ISimpleRecipe;
import com.gildedgames.aether.common.blocks.BlocksAether;
import com.gildedgames.aether.common.containers.slots.SlotMatrix;
import com.gildedgames.aether.common.containers.slots.SlotSimpleCrafting;
import com.gildedgames.aether.common.util.helpers.RecipeUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ContainerSimpleCrafting extends Container
{

	private EntityPlayer player;

	private InventoryPlayer inventory;

	private World world;

	private BlockPos pos;

	public IInventory craftResult = new InventoryCraftResult();

	private SlotSimpleCrafting craftingResult;

	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);

	public ContainerSimpleCrafting(EntityPlayer player, BlockPos pos)
	{
		this.player = player;
		this.inventory = player.inventory;

		this.world = player.getEntityWorld();
		this.pos = pos;

		this.createSlots();
	}

	private void setSimpleCraftingForMatrix(boolean flag)
	{
		for (Slot slot : this.inventorySlots)
		{
			if (slot instanceof SlotMatrix)
			{
				SlotMatrix slotMatrix = (SlotMatrix) slot;
				slotMatrix.setSimpleCrafting(flag);
			}
		}
	}

	private void createSlots()
	{
		for (int k = 0; k < 3; ++k)
		{
			for (int i1 = 0; i1 < 9; ++i1)
			{
				this.addSlotToContainer(new Slot(this.inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + (k * 18)));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(this.inventory, l, 8 + l * 18, 142));
		}

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; ++j)
			{
				this.addSlotToContainer(new SlotMatrix(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
			}
		}

		this.craftingResult = new SlotSimpleCrafting(this.player, this.craftMatrix, this.craftResult, 45, 124, 35);

		this.addSlotToContainer(this.craftingResult);

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	private void moveMaterialsToInventory()
	{
		for (int materialIndex = 0; materialIndex < 9; materialIndex++)
		{
			ItemStack material = this.craftMatrix.getStackInSlot(materialIndex);

			if (material != ItemStack.EMPTY)
			{
				if (!this.player.inventory.addItemStackToInventory(material))
				{
					this.player.dropItem(material, false);
				}

				this.craftMatrix.setInventorySlotContents(materialIndex, ItemStack.EMPTY);
			}
		}
	}

	public void onNewRecipe(ISimpleRecipe recipe)
	{
		this.moveMaterialsToInventory();
		this.craftingResult.setSimpleCrafting(recipe != null);
		this.setSimpleCraftingForMatrix(recipe != null);

		if (recipe == null || !RecipeUtil.canCraft(this.player, recipe))
		{
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			this.craftingResult.setRecipe(null);

			return;
		}

		this.craftingResult.setRecipe(recipe);
		this.craftResult.setInventorySlotContents(0, recipe.getResult());
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!this.world.isRemote)
		{
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

				if (itemstack != ItemStack.EMPTY)
				{
					playerIn.dropItem(itemstack, false);
				}
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.world));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() == BlocksAether.aether_crafting_table
				&& player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	@Override
	@Nonnull
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 45)
			{
				if (!this.mergeItemStack(itemstack1, 0, 36, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 0 && index < 27)
			{
				if (!this.mergeItemStack(itemstack1, 27, 36, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (index >= 27 && index < 36)
			{
				if (!this.mergeItemStack(itemstack1, 0, 27, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 36, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

}
