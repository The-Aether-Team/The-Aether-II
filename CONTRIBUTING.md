## Contributing

Before you begin, you need to install the _Gradle Intergration for Eclipse (4.4)_. You can install it through the Eclipse Marketplace, which is found in _Help > Eclipse Marketplace_.

1. Run the _setup_multi_project.bat_ inside the scripts directory. It might take a while to run the first time.

2. Open Eclipse (if it isn't already), and navigate to _File > Import..._ In the dialog, search for "Gradle Project", then click _Next_.

3. Fill in the _Root folder_ field by clicking _Browse_ and navigating to the location you cloned the repository, then click "Build Model".

4. If all goes well, two projects will be shown. Checkmark _both_ of them if they aren't already.

5. Once done, go to the "Launch" button and click the dropdown, then click "Run Configurations". Create the following configurations below by double clicking on "Java Application" in the left pane.

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

6. Choose which configuration you want to run and simply press the "Run" button. After you run it, it'll be added to your launch history and will be accessible from the Launch dropdown menu.

If the Gradle configurations have changed since the last time you used it (Forge gets updated, etc), you'll need to run _setup_multi_project.bat_ again. After the setup script has run, right click both the Aether 1.8 and Gilded-Games-Util projects, and then click _Gradle > Refresh All..._