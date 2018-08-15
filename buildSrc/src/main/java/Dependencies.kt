object Versions {
    /**
     * General versions
     */
    const val COMPILE_SDK = 27
    const val TARGET_SDK = 27
    const val MIN_SDK = 21
    const val KOTLIN = "1.2.41"

    /**
     * Gradle dependencies versions
     */
    const val ANDROID_GRADLE = "3.1.2"

    /**
     * Support versions
     */
    const val KTX = "0.3"
    const val SUPPORT = "27.1.0"
    const val CONSTRAINT = "1.1.1"
    const val ARCH_COMPONENTS = "1.1.1"

    /**
     * Thrid party versions
     */
    const val DAGGER = "2.16"
    const val RX_JAVA_2 = "2.1.10"
    const val RX_KOTLIN = "2.2.0"
    const val GSON = "2.8.5"
    const val DBFLOW = "4.2.4"

    const val GLIDE = "4.6.1"
    const val MATERIAL_DIALOG = "0.9.6.0"
    const val TIMBER = "4.7.0"
    const val EXOPLAYER = "2.8.3"
    const val PERMISSIONS_DISPATCHER = "3.3.1"
}

object Libraries {
    /**
     * Basic dependencies
     */
    const val KTX = "androidx.core:core-ktx:${Versions.KTX}"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"

    /**
     * Support and basic android dependencies
     */
    @JvmField
    val SUPPORT = arrayOf(
            "com.android.support:appcompat-v7:${Versions.SUPPORT}",
            "com.android.support:design:${Versions.SUPPORT}",
            "com.android.support.constraint:constraint-layout:${Versions.CONSTRAINT}",
            "com.android.support:cardview-v7:${Versions.SUPPORT}"
    )
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val ARCH_COMPONENTS = "android.arch.lifecycle:extensions:${Versions.ARCH_COMPONENTS}"


    /**
     * Third party libraries dependencies
     */
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_PROCESSOR = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    @JvmField
    val DAGGER_ANDROID = arrayOf(
            DAGGER,
            "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
    )
    @JvmField
    val DAGGER_ANDROID_PROCESSORS = arrayOf(
            DAGGER_PROCESSOR,
            "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"
    )

    @JvmField val DBFLOW = arrayOf(
        "com.github.Raizlabs.DBFlow:dbflow-core:${Versions.DBFLOW}",
        "com.github.Raizlabs.DBFlow:dbflow:${Versions.DBFLOW}"
    )
    const val DBFLOW_PROCESSOR = "com.github.Raizlabs.DBFlow:dbflow-processor:${Versions.DBFLOW}"

    const val RX_JAVA_2 = "io.reactivex.rxjava2:rxjava:${Versions.RX_JAVA_2}"
    const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:${Versions.RX_KOTLIN}"

    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val MATERIAL_DIALOG = "com.afollestad.material-dialogs:core:${Versions.MATERIAL_DIALOG}"

    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val EXOPLAYER = "com.google.android.exoplayer:exoplayer:${Versions.EXOPLAYER}"
    const val PERMISSIONS_DISPATCHER = "com.github.hotchemi:permissionsdispatcher:${Versions.PERMISSIONS_DISPATCHER}"
    const val PERMISSIONS_DISPATCHER_PROCESSOR = "com.github.hotchemi:permissionsdispatcher-processor:${Versions.PERMISSIONS_DISPATCHER}"

}


object Plugins {
    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE}"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
}