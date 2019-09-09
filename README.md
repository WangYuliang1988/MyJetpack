Android Jetpack 学习工程，参考 https://github.com/googlesamples/android-sunflower

## Android Jetpack
Android Jetpack 是谷歌官方提供的一套用户构建App的组件、工具和规范，主要基于支持库和架构组件，并将它们重新划分到4个目录下：**Foundation**、**Architecture**、**UI**、**Behavior**。

具体到本工程中，主要涉及以下内容：
* **Foundation** - Components for core system capabilities, Kotlin extensions and support for multidex and automated testing.
  * **AppCompat** - Degrade gracefully on older versions of Android.
  * **Android KTX** - Write more concise, idiomatic Kotlin code.
  * **Test** - An Android testing framework for unit and runtime UI tests.
* **Architecture** - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
  * **Data Binding** - Declaratively bind observable data to UI elements.
  * **Lifecycles** - Create a UI that automatically responds to lifecycle events.
  * **LiveData** - Build data objects that notify views when the underlying database changes.
  * **Navigation** - Handle everything needed for in-app navigation.
  * **Room** - Access your app's SQLite database with in-app objects and compile-time checks.
  * **ViewModel** - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
  * **WorkManager** - Manage your Android background jobs.
* **UI** - Details on why and how to use UI Components in your apps - together or separate
  * **Animations & Transitions** - Move widgets and transition between screens.
  * **Fragment** - A basic unit of composable UI.
  * **Layout** - Lay out widgets using different algorithms.
* **Third party**
  * **Glide** for image loading.
  * **Kotlin Coroutines** for managing background threads with simplified code and reducing needs for callbacks.

## Ktlint
本工程使用 Ktlint 来确保良好的 Kotlin 编码风格，Ktlint 在Android Studio中的配置步骤如下：

* 在工程根目录下，创建一个名为ktlint.gradle的文件，内容如下：

    ``` 
    repositories {
        jcenter()
    }
    
    configurations {
        ktlint
    }
    
    dependencies {
        ktlint "com.pinterest:ktlint:#version" // replace #version with the latest version of ktlint
    }
    
    task ktlint(type: JavaExec, group: "verification") {
        description = "Check Kotlin code style."
        classpath = configurations.ktlint
        main = "com.pinterest.ktlint.Main"
        args "src/**/*.kt"
    }
    
    task ktlintFormat(type: JavaExec, group: "formatting") {
        description = "Fix Kotlin code style deviations."
        classpath = configurations.ktlint
        main = "com.pinterest.ktlint.Main"
        args "-F", "src/**/*.kt"
    }
    ```
* 在工程根目录下的build.gradle文件中，allprojects配置下，新增以下内容：
    ```
    allprojects {
        ...
        apply from: "$rootDir/ktlint.gradle"
    }
    ```