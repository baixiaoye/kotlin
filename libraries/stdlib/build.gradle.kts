
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

buildscript {
    repositories {
        mavenLocal()
        maven { setUrl(rootProject.extra["repo"]) }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra["kotlinVersion"]}")
    }
}

apply { plugin("kotlin") }

configure<JavaPluginConvention> {
    sourceSets.getByName("main").apply {
        java.setSrcDirs(listOf(File(projectDir,"src")))
    }
    sourceSets.getByName("test").apply {
        java.setSrcDirs(emptyList<File>())//listOf(File(projectDir,"test")))
    }
}

dependencies {
    compile(project(":core.builtins"))
    compile("junit:junit:4.11")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs =
            listOf("-Xallow-kotlin-package",
                    "moduleName", "kotlin-stdlib",
                    "-Xmultifile-parts-inherit",
                    "-Xdump-declarations-to", File(buildDir, "declarations/stdlib-declarations.json").canonicalPath)
}

