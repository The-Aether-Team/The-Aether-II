### Creating issues
When opening issues on our issue tracker, we ask that you provide the following information:

- The exact version of the Aether II you are using, such as `1.10.2-1.0.0`. If you're not using the Aether Launcher, please also include the version of Forge you are using, such as `1.10.2-12.18.3.2185`.

- If your issue is a bug, state what you expected to happen.

- If your issue is a crash, attach the latest client/server log and crash report.

- If your issue only occurs with other mods/plugins installed, list the exact mod/plugin versions installed.

Make sure to keep your issue's description clear and concise. Your issue's title should also be easy to digest, giving our developers and reporters a good idea of what's wrong without including too many details.

Our reporters and developers work around the clock, and will take care of debugging, labeling, and sorting your issues. However, please note that there are currently _many_ open issues, and that it may take some time (up to a few weeks in the worst case) for your issue to be triaged and resolved. Typically, issues are resolved in order of severity and complexity, not the date submitted.

### Getting started for contributors
We're excited to hear that you're interested in contributing to the Aether!

Before getting started, you'll need to install [Git](https://git-scm.com/) (even if you have your own Git client, our buildscripts require Git to be present on your system's environment path) and the latest version of the [Java 8 JDK](www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) (or OpenJDK 8). On Windows, we recommend you use [Chocolately](https://chocolatey.org) to install these packages, although you're free to install them manually. If you're developing on Mac OS or Linux, these should be standard packages in your software repositories.

If you're going to be making code contributions, we strongly recommend you use [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/), which is our weapon of choice for taming the beast. We've received reports that Gradle integration in other IDEs (such as Eclipse) can have issues with the ForgeGradle project, and as such we can only recommend and provide instructions for IntelliJ IDEA.

If you're not familiar with setting up IntelliJ IDEA for use with ForgeGradle projects, cpw has created a setup video which runs over a few of the basics of ForgeGradle [here](https://www.youtube.com/watch?v=PfmlNiHonV0).

If you have any questions or issues, or would just like to discuss Aether development, feel free to [join us on Discord](https://discord.gg/HYG3VPj).

### Creating merge requests
When contributing source code changes to the Aether II, it is **mandatory** to keep consistent use of the formatting and style guidelines used throughout the codebase.

If you are using IntelliJ IDEA 2017 or newer, we provide configuration files for our code formatting guidelines and inspections in the `idea` folder.

Furthermore, please make sure before opening a merge request that:

- Your merge request has an overview of the changes it makes, along with a link to the open issue(s) it resolves, if applicable.

- Your changes include appropriate documentation and conform to our style guidelines.

- If your merge request contains multiple commits, that you squash them before submitting.

- You state in the description of your merge request that you agree to the Contributor License Agreement (CLA) found below.

### Contributor License Agreement
By submitting code, assets, or documentation to the repository you are hereby agreeing that:

- You grant Gilded Games and other users the right to use your contributions under one of the following respective licenses:

    - [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/) for assets in `/src/main/java/resources` and wiki pages.

    - [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) for code or other changes.

- Your contributions are of your own work and are free of legal restrictions (such as patents or copyrights).

If you have any questions about these terms, please [get in contact with us](https://aether.gildedgames.com/support). **If you do not agree to these terms, please do not submit contributions to this repository.**
