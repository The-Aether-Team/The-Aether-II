Aether II 1.8
======

In development by Gilded Games, the Aether II is an extensive mod for Minecraft which implements a remarkable world in the skies. Adventure, danger, and mystery awaits in the depths of the Aether.

### Setting up your Workspace

Before you begin, you need to install the _Gradle Intergration for Eclipse (4.4)_. You can install it through the Eclipse Marketplace, which is found in _Help > Eclipse Marketplace_.

1. Open the Import dialog by clicking _File > Import..._ and search for "Gradle Project", then click _Next_.

2. Fill in the _Root folder_ field by clicking _Browse_ and navigating to the location you cloned the repository, then click "Build Model".

3. If all goes well, two projects will be shown. Checkmark _both_ of them if they aren't already. In the "Import options" section, checkmark "Run before" and set it's contents to: `setupDecompWorkspace cleanEclipse eclipse`.

4. Click "Finish" and wait for Eclipse to setup your projects. This may take 10 or so minutes.

5. If the imported projects have errors, try right clicking each one and going to _Gradle > Refresh All..._

6. Once done, go to the "Launch" button and click the dropdown, then click "Run Configurations". Create the following configurations below by double clicking on "Java Application" in the left pane.

	a. Aether Client
	```
	[Main tab]
	Project: (the Aether project)
	Main class: GradleStart

	[Arguments tab]
	Program arguments: --username MY_USERNAME
	Working directory: {WORKSPACE_LOC}\eclipse
	```

	Replace `MY_USERNAME` with your Minecraft character's username and `{WORKSPACE_LOC}` with the default working directory's text.

	b. Aether Server
	```
	[Main tab]
	Project: (the Aether project)
	Main class: net.minecraftforge.fml.relauncher.ServerLaunchWrapper

	[Arguments tab]
	Working directory: {WORKSPACE_LOC}\eclipse
	```

	Be sure to replace `{WORKSPACE_LOC}` with the default working directory's text.

7. Choose which configuration you want to run and simply press the "Run" button. After you run it, it'll be added to your launch history and will be accessible from the Launch dropdown menu.

If the gradle configurations have changed since the last time you used it, you'll need to go to each imported project, right click, and run _Gradle > Refresh All..._