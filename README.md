## Modding API

First, import the project using [jitpack](https://jitpack.io/). Below is an example using Gradle and Maven respectively

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.User:Repo:Tag'
}
```
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <!-- The rest of your repos... -->
</repositories>
<dependency>
    <groupId>com.github.User</groupId>
    <artifactId>Repo</artifactId>
    <version>Tag</version>
</dependency>
```

### Examples

### Guidelines

There are a few other guidelines to note:

#### Avoid Enums at all costs!

This one is pretty simple, if you ever want to add into something, its far easier to make it public and still a `array.add(object)` somewhere and make it work.\
Its also partly preference from the main devs as well, so do what you want but beware of the consequences.

## Licensing

1D6 will forever remain <abbr title="Free Open Source Software">FOSS</abbr> under the MIT

### Dependencies

These are all the dependencies for the project, and their accompanying licenses:

<!-- https://www.tablesgenerator.com/markdown_tables -->
|   	| Links 	| Version 	| License 	                                                          | Use 	|
|---	|---	|:---:	|--------------------------------------------------------------------|---	|
| LibGDX 	| [Github](https://github.com/libgdx/libgdx), [Homepage](https://libgdx.com/) 	| 1.12.1 (core)<br>2.3.20 (roboVM)<br>1.5 (box2Dlights)<br>1.7.4 (ashley)<br>1.8.2 (ai)<br>2.2.1 (controllers) 	| [Apache-2.0](https://github.com/libgdx/libgdx/blob/master/LICENSE) 	 | Wrapper around OpenGL serving as a game engine 	|
| gson 	| [Github](https://github.com/google/gson) 	| 2.10.1 	| [Apache-2.0](https://github.com/google/gson/blob/main/LICENSE) 	   | JSON parser 	|  	|
| jsvg 	| [Github](https://github.com/weisJ/jsvg) 	| 1.4.0 	| [MIT](https://github.com/weisJ/jsvg/blob/master/LICENSE) 	         | SVG parser 	|  	|
| SLF4J 	| [Github](https://github.com/qos-ch/slf4j), [Homepage](https://www.slf4j.org/) 	| 1.7.32 (SLF4J)<br>1.2.6 (Logback) 	| [ARR](https://github.com/qos-ch/slf4j/blob/master/LICENSE.txt) 	   | Logger 	|
| nashorn 	| [Github](https://github.com/openjdk/nashorn), [Homepage](https://openjdk.org/projects/nashorn/) 	| 15.4 	| [GPL-2.0](https://github.com/openjdk/nashorn/blob/main/LICENSE) 	  | Javascript Engine 	|
| lombok 	| [Github](https://github.com/projectlombok/lombok), [Homepage](https://projectlombok.org/) 	| 1.18.26 	| [MIT](https://github.com/projectlombok/lombok/blob/master/LICENSE) 	 | Utilities 	|
| ASM 	| [Homepage](https://asm.ow2.io/) 	| 9.6 	| [3-Clause BSD](https://asm.ow2.io/license.html) 	                  | Bytecode manipulation 	|  	|
| byte buddy 	| [Github](https://github.com/raphw/byte-buddy), [Homepage](https://bytebuddy.net/) 	| 1.14.14 	| [Apache-2.0](https://github.com/raphw/byte-buddy/blob/master/LICENSE) 	 | Safer bytecode manipulation 	|
| guava 	| [Github](https://github.com/google/guava) 	| 33.1.0-jre 	| [Apache-2.0](https://github.com/google/guava/blob/master/LICENSE) 	 | Utilities 	|
| Fabric-ASM 	| [Github](https://github.com/Chocohead/Fabric-ASM) 	| v2.3 	| [MLP-2.0](https://github.com/Chocohead/Fabric-ASM/blob/master/LICENSE) 	 | Modifies Enums 	|