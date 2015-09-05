<p align="center">

 <img src="http://i.imgur.com/RgWAINr.png"><br><br>

  In development by Gilded Games, the Aether II is an extensive mod for Minecraft which implements a remarkable world in the   skies. Adventure, danger, and mystery awaits in the depths of the Aether.

</p>

### Setting up your Workspace

Run the `setup_decomp_workspace.bat` script before following any of the IDE specific instructions below!

**IntelliJ users:** You'll need to enable the Gradle plugin if you've had it disabled (it should be enabled by default). Use the `File > Open...` menu, and choose the build.gradle file inside the repo.

Afterwards, open the Gradle menu (on the right side toolbar, by default), navigate to `Tasks > other`, and double click the `genIntellijRuns` task. This will create your launch configurations automatically.

**Eclipse users:** You'll need to install the Gradle Intergration plugin if you haven't already, and create a new workspace. Use the `File > Import...` menu, and select `Gradle project`.

The `Import Gradle Project` should open. Simply set the `root folder` to the location you've cloned this repo, and click 'Build model'. After it's finished building things, checkmark the Aether II project, and finish importing the project.

You'll need to manually create your run configurations, however. The client's main class is `GradleStart`, and the server's main class is `GradleStartServer`.

You can also pass `--username USERNAME` to the configuration's program arguments to set your in-game username.

### Developers

When working on the code, it is **mandatory** to keep consistent use of the Gilded Games' Formatting and Cleanup settings. We _will_ revert commits that do not adhere to it.

1. In Eclipse, select the Aether project, and go to `Project > Properties > Java Code Style (in the left sidebar) > Clean Up`. Import the `eclipse-cleanup.xml` found in the `eclipse` folder.

2. Repeat this for the Formatter settings by going to `Java Code Style > Formatter` and choosing the `eclipse-formatter.xml`.

3. In the same properties dialog, go to `Java Editor > Save Actions` and check the following options:
  - Format all lines on save
  - Organize imports on save
  - Perform additional actions. (If not there, make sure to have automatic 'this' qualifiers.)
