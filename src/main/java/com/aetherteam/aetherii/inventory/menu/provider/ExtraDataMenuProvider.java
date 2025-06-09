package com.aetherteam.aetherii.inventory.menu.provider;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;

import java.util.function.BiConsumer;

public class ExtraDataMenuProvider implements MenuProvider {
    private final Component title;
    private final BiConsumer<AbstractContainerMenu, RegistryFriendlyByteBuf> clientExtraData;
    private final MenuConstructor menuConstructor;

    public ExtraDataMenuProvider(MenuConstructor menuConstructor, BiConsumer<AbstractContainerMenu, RegistryFriendlyByteBuf> clientExtraData, Component title) {
        this.menuConstructor = menuConstructor;
        this.clientExtraData = clientExtraData;
        this.title = title;
    }

    public Component getDisplayName() {
        return this.title;
    }

    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return this.menuConstructor.createMenu(i, inventory, player);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        this.clientExtraData.accept(menu, buffer);
    }
}
