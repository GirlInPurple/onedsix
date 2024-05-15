# Contribution Guidelines

## List of Directories

- [`./assets/`](../assets) - Universal assets for every other directory. Holds Logger configs, Desktop Icons, Error textures, and whatever else is needed at all times.
- [`./basemod/`](../basemod) - The base extension that most of the ecosystem is based around. Has different modules for different tweaks and addons for the game.
- [`./core/.../java/onedsix/api/`](../core/src/main/java/onedsix/api) - Contains API endpoints for mods. Meant mostly for
- [`./core/.../java/onedsix/core/`](../core/src/main/java/onedsix/core) - The base assets, graphics engine, and utilities for other mods.
- [`./core/.../resources/`](../core/src/main/resources) - The assets built into 1D6, free for use by everyone.
- [`./desktop/`](../desktop) - The Desktop (Windows, Mac, Linux, SteamOS, etc.) client. Runs after [`./loader/`](../loader) has, as it runs the files that the loader creates.
- [`./examplemod/`](../examplemod) - The Example Mod shown on the dev wiki. Will get updated over time when the mod api updates.
- [`./launcher/`](../launcher) - The official launcher for 1D6. A simple [Electron](https://www.electronjs.org/) app that has an "instancer", mod downloader, news feed, etc.
- [`./loader/`](../loader) - The 1D6 Mod Loader. Uses Mixin + ByteBuddy, so if you're familiar with Minecraft modding you'll be right at home.
- [`./server/`](../server) - The official 1D6 server software. Only the boot-up and CLI code lives the rest in [`./core/.../server/`](../core/src/main/java/onedsix/core/server)

## About Android

**TLDR; Android support is both nearly impossible, and out of scope for this project.**

Originally, 1D6 was supposed to be cross-platform, between IOS, Android, and PC, but after the Mod Loader-Core Library split, that idea was thrown away.\
Porting the entirety of 1D6's mod loader, engine, and launcher over to Android, while not impossible, it just *way* out of scope of this pet project.\
Even the launcher and networking was a little over my head, but ive managed over the past few months.
