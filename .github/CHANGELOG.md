# d1.2.0

- Changelog is added to the repo.
- The 1D6 mod loader is mostly finished
  - Uses [`SpongePowered/Mixin`](https://github.com/SpongePowered/Mixin) and Fabric Mod Loader's `*.mod.json` format
    - The format will be changed back to the NeoForged ML's `mods.toml` format next commit
  - Is based off [`AnvilloyDevStudio/MiniMods`](https://github.com/AnvilloyDevStudio/MiniMods), a mod loader for [`MinicraftPlus`](https://github.com/MinicraftPlus/minicraft-plus-revived)
    - MiniMods is under [GPL-v3.0](https://github.com/AnvilloyDevStudio/MiniMods/blob/main/LICENSE-GPL), as well as [MiniMods Software License Agreement](https://github.com/AnvilloyDevStudio/MiniMods/blob/main/LICENSE)
- Moved around a few classes
  - `onedsix.client.*` is now its own package
  - `onedsix.server.*` is now its own package
  - The game entrypoint was moved to `onedsix.Entrypoint`
  - `onedsix.core.assets.abstracts.Dialog` is now located in `basemod.entities.data.Dialog` under the `Entities` module.
- Removed plans for RCON to be added to dedicated servers
- Changed build files a bit
