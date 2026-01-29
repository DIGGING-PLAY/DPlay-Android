pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "DPlay"
include(":app")
include(":feature")
include(":core")
include(":core:domain")
include(":core:data")
include(":core:designsystem")
include(":core:common")
include(":core:network")
include(":feature:main")
include(":core:ui")
include(":feature:dummy")
include(":feature:home")
include(":feature:mypage")
include(":core:navigation")
include(":feature:detail")
include(":feature:splash")
include(":feature:login")
include(":feature:onboarding")
include(":feature:editprofile")
include(":feature:setting")
include(":feature:record")
include(":feature:search")
include(":feature:comment")
include(":feature:otherprofile")
