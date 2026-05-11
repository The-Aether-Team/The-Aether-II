# The Aether II - NeoForge - 26.1.2-alpha.3.1

Additions

- Add a backend Healing Overflow effect to save the Healing Stone's extra hearts after logout
- Add biome color and structure icon support for maps like https://map.jacobsjo.eu/

Changes

- Rename Carrion Pull backend-effect to Carrion Trap
- Rename `mob_effect/typed` tag to `mob_effect/milk_doesnt_clear` to represent the proper purpose of the tag
- Put more of Aether II's effects into the `milk_doesnt_clear` tag

Fixes

- Fixed a crash from throwing a glider item out of the player's inventory
- Fixed a crash when running the mod with SpongeNeo
- Fixed a crash when trying to break blocks in the world using Integrated Tunnels
- Fixed non-dyeable gloves from rendering the base texture as completely black
- Fixed the top face of mossy leaves not rendering properly
- Fixed advancement names not having unique colors on dedicated servers
- Fixed /clear command and creative mode inventory screen deletion slot not clearing accessory items
- Fixed Aerbunnies not staying on the player's head when falling out of the Aether in a vehicle
- Fixed Copper Torches not igniting Hestveil blocks