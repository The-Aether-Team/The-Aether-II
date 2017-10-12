package com.gildedgames.orbis.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class OrbisKeyBindings
{

	public static KeyBinding keyBindControl = new KeyBinding("keybindings.control", /** TODO: Find out if mac **/
			false ? Keyboard.KEY_LMETA : Keyboard.KEY_LCONTROL,
			"key.categories.misc");

	public static void init()
	{
		ClientRegistry.registerKeyBinding(keyBindControl);
	}

}
