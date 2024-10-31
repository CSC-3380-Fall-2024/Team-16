# Team-16

Group Members:
- Nicholas Thai (Gitmaster)
- Nathan Soto (Project Manager)
- Devin Dao (Communications Lead)
- Minseo Lee (Quality Assurance Tester)
- Peter Nguyen (Design Lead)

## Universal Fitness

This is a Kotlin Multiplatform project for Android and IOS.

## For group members

### Coding guidelines

- Don't commit to the main branch. Instead, create feature branches off of dev branch.
- Use meaningful commit and branch names.
- Format your code before committing.
- Follow the [code conventions](CODE_STYLE.md).

### Set up development environment

1. Install [Android Studio](https://developer.android.com/studio).
2. Install [Kotlin Multiplatform plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform).
3. Install a [JDK](https://adoptium.net/temurin/releases/?package=jdk) if you don't already have one.
4. Clone this repo.
5. Open it in Android Studio.

### Enable useful settings

1. Navigate to **File > Settings > Tools > Actions on Save**.
2. Enable **Reformat code** and **Optimize imports**.
3. Navigate to **Version Control > Git**.
4. Enable staging area.

### Links

- [Kotlin](https://kotlinlang.org/docs/)
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation)
- [List of Composables](https://composables.com/)
- [Material 3 Components](https://m3.material.io/components)
- [Material Icons](https://fonts.google.com/icons?icon.set=Material+Icons&icon.style=Filled)
- [Ktor](https://ktor.io/docs/welcome.html)

### Useful directories

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
