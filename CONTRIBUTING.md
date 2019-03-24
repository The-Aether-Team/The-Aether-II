### Getting started for programmers
We're excited to hear that you're interested in contributing to the Aether!

Before getting started, you'll need to install the latest 64-bit version of the OpenJDK 8 for your environment.
- Windows users: We **strongly** recommend you use the Hotspot OpenJDK 8 builds provided by the [AdoptOpenJDK project](https://adoptopenjdk.net/) instead of the builds provided by Oracle.
- macOS and Linux users: If you are already using a package manager, OpenJDK builds should be present in your software repositories. If not, we recommend using [SDKMan](https://sdkman.io/) to install the Hotspot OpenJDK 8 builds provided by the [AdoptOpenJDK](https://adoptopenjdk.net/) project.

We strongly recommend you use [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/) when making code contributions. While other IDEs may work (in theory, anyways), support is spotty and you will often run into issues and other roadblocks. If you're not familiar with setting up IntelliJ IDEA for use with ForgeGradle projects, cpw has created a setup video which runs over a few of the basics of ForgeGradle [here](https://www.youtube.com/watch?v=PfmlNiHonV0).

If you have any questions or issues, or would just like to discuss Aether development, feel free to [join us on Discord](https://discord.gg/HYG3VPj).

### Creating merge requests
When contributing source code changes to the Aether II, it is **mandatory** to keep consistent use of the formatting and style guidelines used throughout the codebase. Merge requests which do not adhere to them will be held back until they're updated to meet our requirements.

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
