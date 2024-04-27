1D6
===
#### A Data-Gen based RPG Game/Engine

<img src="https://raw.githubusercontent.com/GirlInPurple/onedsix/master/assets/icon_large.png" alt="1D6 Logo" height="180px" align="right"/>

This project was originally started because I wanted to make a DnD-like RPG game, homebrew and all, but it has grown quite significantly since then and has basically become its own engine on top of LibGDX.

This project is based around Data-gen and allowing for any code you want to be run. Being open-source and lightweight, it makes this very easy and fast to add new features, to core or your own mod.\

## For Users



## For Server Owners



## For Devs

First, import the 1D6 using [jitpack](https://jitpack.io/) and LibGDX from Sonatype. Below is an example using Gradle and Maven respectively.\
Check this repo's [gradle.properties](gradle.properties) for what versions to use.

```groovy
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
}
dependencies {
    // 1D6
    implementation "com.github.GirlInPurple:onedsix:-SNAPSHOT" // Latest
    implementation "com.github.GirlInPurple:onedsix:a0.1.0" // Alpha 0.1.0
    implementation "com.github.GirlInPurple:onedsix:a0.1.1"// Alpha 0.1.1

    // LibGDX
    api "com.badlogicgames.gdx:gdx:$gdxVersion"
    api "com.badlogicgames.gdx:gdx-ai:$aiVersion" 
    // Technically you dont need the ones below this, but its worth it to get anyways.
    api "com.badlogicgames.ashley:ashley:$ashleyVersion"
    api "com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion"
    api "com.badlogicgames.gdx:gdx-freetype:$gdxVersion"
    api "com.badlogicgames.gdx-controllers:gdx-controllers-core:$gdxControllersVersion"
    api "com.badlogicgames.gdx:gdx-box2d:$gdxVersion"
}
```
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <repository>
        <id>sonatype.snopshots</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
    <repository>
        <id>sonatype.releases</id>
        <url>https://oss.sonatype.org/content/repositories/releases/</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.GirlInPurple</groupId>
        <artifactId>onedsix</artifactId>
        <version>Tag</version>
    </dependency>
    <dependency>
        <groupId>com.badlogicgames.gdx</groupId>
        <artifactId>gdx</artifactId>
        <version>1.12.1</version>
    </dependency>
</dependencies>
```

First, lets create an `Item` and make it move the player X + 1.

```java
import com.badlogic.gdx.math.Vector3;
import onedsix.Player;
import onedsix.gen.assets.*;

public class YourItem extends Item {
    
    // This means it will create an item with no texture, name, or model,
    // but will create everything else needed for the item to exist and be accessible.
    public YourItem(Attributes attributes, Recipe recipe, long roughCost) {
        super(attributes, recipe, roughCost);
    }
    
    // Whenever the item is used (outside battles) it will run this code here.
    @Override public void onUse(Player player) {
        player.position = new Vector3(
                player.position.x + 1,
                player.position.y,
                player.position.z
        );
    }
    
    // Your IDE will tell you to create these, you can ignore them for now
    @Override public void onInteract(Player player) {}
    @Override public void onInteractBattle(Player player) {}
    @Override public void onUseBattle(Player player) {}
}
```

Now, make a hook into `ModStartupListener` and make sure you register your item.

```java
import example.YourItem;
import onedsix.event.modstartup.ModStartupEvent;
import onedsix.event.modstartup.ModStartupListener;
import onedsix.gen.DatagenHandler;
import org.slf4j.*;

public class ExampleMod implements ModStartupListener {
    
    private static final Logger L = LoggerFactory.getLogger(ExampleMod.class);
    
    @Override
    public void onStartup(ModStartupEvent event) {
        addCustomItem(YourItem.class);
    }
}

```

Done! Now you have added an item to the game!\
For a more detailed explanation, check out the [wiki](https://github.com/GirlInPurple/onedsix/wiki).

## Licensing

1D6 will forever remain <abbr title="Free Open Source Software">FOSS</abbr> under GPL-v3.0 license.\
All art and music assets are owned by their creators, please ask them about licensing.

LibGDX, ByteBuddy, and GSON are under Apache-2.0\
jsvg is under MIT\
ASM is under BSD 3-Clause