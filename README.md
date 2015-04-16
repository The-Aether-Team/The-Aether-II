<center>

![Aether II Logo](http://i.imgur.com/RgWAINr.png)<br>

In development by Gilded Games, the Aether II is an extensive mod for Minecraft which implements a remarkable world in the skies. Adventure, danger, and mystery awaits in the depths of the Aether.

</center>

### Setting up your Workspace

Setting up your workspace is a lot easier now. Run the `setup_eclipse.bat` found in the root directory of the repo. Aftewards, open Eclipse and navigate to `File > Switch Workspace...`, then choose `Other...` and browse for the `eclipse` folder found inside the repo.

### Developers

When working on the code, it is **mandatory** to keep consistent use of the Gilded Games' Formatting and Cleanup settings. We _will_ revert commits that do not adhere to it.

1. In Eclipse, select the Aether project, and go to `Project > Properties > Java Code Style (in the left sidebar) > Clean Up`. Import the `eclipse-cleanup.xml` which is in the root directory of the repo.

2. Repeat this for the Formatter settings by going to `Java Code Style > Formatter` and choosing the `eclipse-formatter.xml`.

3. In the same properties dialog, go to `Java Editor > Save Actions` and check the following options:
  - Format all lines on save
  - Organize imports on save
  - Perform additional actions. (If not there, make sure to have automatic 'this' qualifiers.)
