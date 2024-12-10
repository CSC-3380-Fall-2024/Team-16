# Team-16

Group Members:
- Nicholas Thai (Gitmaster)
- Nathan Soto (Project Manager)
- Devin Dao (Communications Lead)
- Minseo Lee (Quality Assurance Tester)
- Peter Nguyen (Design Lead)

## Universal Fitness

This is an Android app for your fitness needs. Features include workout generator/logger, calorie/macro tracker, news, and social media. The app uses Kotlin Multiplatform, so it may be runnable on iOS. However, it has only been tested on Android.

## Set up development environment

1. Install [Docker](https://www.docker.com/). This will be used to setup the database.
2. Install the latest [JDK](https://adoptium.net/temurin/releases/?package=jdk) if you do not already have a JDK installed.
3. Install [Android Studio](https://developer.android.com/studio).
4. Install the [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform) on Android Studio.
5. Clone this repo.
6. Open it in Android Studio.
7. Wait for Android Studio to fully sync and index all the files. This may take a long time for the first time because it has to download all the dependencies.
7. Copy [`.env.template`](/.env.template) to a new file named `.env`.

### Configuring `.env`.

There are two variables in `.env` that you need to configure for the app to work:

#### `SERVER_IP`

If you are planning to run the app on Android Studio's emulator, you can skip this step.
Otherwise, you need to configure this. When running on a real phone, your phone needs to know
what the IP address of the backend is. You must set this variable to the local IP address of
your computer.

To get your computer's local IP address, it depends on your operating system:
- For Windows: [[link](https://support.microsoft.com/en-us/windows/find-your-ip-address-in-windows-f21a9bbc-c582-55cd-35e0-73431160a1b9)]
- For macOS: [[link](https://www.security.org/vpn/find-mac-ip-address/)]

Once you've gotten your local IP address, set it in this variable.

#### `BING_SEARCH_API_KEY`

The backend uses Microsoft's Bing Search API to search for news articles.
You must set this variable for the app to work.

Register a Microsoft Azure account and activate your free trial. If you already have an old Azure account with an expired free trial, you can activate a pay-as-you-go plan. Don't worry, no costs will be charged because you can use the free tier.

Once you've registered an account, create a Bing Search resource [here](https://portal.azure.com/#create/microsoft.bingsearch). You will need to configure some fields:
- For the resource group, it doesn't matter. Create a new one if you don't have a resource group.
- For the pricing tier, you can use `F1`, which is the free tier.
- For the name, put anything. It does not matter.

Once you have created the resource, you need to get one of the subscription keys. From the web page of your resource, click the link next to "Manage keys".

![how to find subscription keys](/images/azure.png)

Once you've clicked on the link, copy either key to your clipboard. Paste it in this variable.

### Running the app

Before you run the app, open Docker Desktop. This is to make sure the Docker Daemon is running on your computer.

You will run the app using Android Studio's run configurations feature. It is located on the top right of Android Studio and looks like this:

![run configurations example](/images/run-configurations.png)

Run the `Backend` run configuration.
When running for the first time, it may take a while.
This will do the following:
1. Spin up the database using Docker Compose.
2. Build the server into a `jar` file.
3. Run the built `jar` file.

While the database is running, you can navigate to http://localhost:26545/ to view database graphically.

While `Backend` is running. Switch the run configuration to `Frontend`. Then, run it. This will do the following:
1. Build the Android app into an APK.
2. Install the app your phone or emulator.
3. Run it on your phone or emulator.

## Code guidelines

- Don't commit to the main branch. Instead, create feature branches off of dev branch.
- Use meaningful commit and branch names.
- Format your code before committing.
- Follow the [code conventions](CODE_STYLE.md).

## Enable useful settings

1. Navigate to **File > Settings > Tools > Actions on Save**.
2. Enable **Reformat code** and **Optimize imports**.
3. Navigate to **Version Control > Git**.
4. Enable staging area.

## Links

- [Kotlin](https://kotlinlang.org/docs/)
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation)
- [List of Composables](https://composables.com/)
- [Material 3 Components](https://m3.material.io/components)
- [Material Icons](https://fonts.google.com/icons?icon.set=Material+Icons&icon.style=Filled)
- [Ktor](https://ktor.io/docs/welcome.html)

## Useful directories

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.
