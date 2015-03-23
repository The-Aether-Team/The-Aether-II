Aether II
======

In development by Gilded Games, the Aether II is an extensive mod for Minecraft which implements
a remarkable world in the skies. Adventure, danger, and mystery awaits in the depths of the Aether.

#### Developers
See [CONTRIBUTING.md](https://github.com/gildedgames/aether/blob/master/CONTRIBUTING.md).


##### Not a developer and need to setup your Eclipse workspace?

1. Simply clone the Aether repo. If you don't have the GitHub client installed, grab it [here](https://windows.github.com/).
![cloning.png](http://i.imgur.com/72jYiaS.png)

2. Download the [Forge 1.7.10.1291 source code](http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.2.1291/forge-1.7.10-10.13.2.1291-src.zip)  and extract the following files into the location you cloned the Aether to. *DO NOT COPY THE SRC OR BUILD.GRADLE!*
  - gradlew.bat
  - eclipse
  - gradle

3. Run the _setup.bat_ inside the Aether repo folder. This might take a while to finish the first time.

4. Open Eclipse. When asked where to open your workspace, choose the _eclipse_ folder within the Aether repo folder.

5. To launch the Aether, look for the _Launch_ button on the toolbar. It looks a bit like a green play button.

###### Syncing changes made to the Aether (getting the latest commits)

The GitHub client will usually alert you whenever there's unsynced changes.

![sync.png](http://i.imgur.com/H9lZ0HP.png)

Clicking the "Sync" button in the top right corner of the GitHub client will automatically get the latest changes.

##### Compiling the Aether

1. Run the _build.bat_ file inside the Aether repo folder. This might take a while to finish.

2. Once the build is finished (and without errors), the compiled JARs will be copied to the _build/libs_ folder. 

_Note: Gilded Games Util is built seperately from the Aether and is needed for the Aether._
