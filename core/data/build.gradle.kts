plugins {
    alias(libs.plugins.dplay.data)
    alias(libs.plugins.dplay.hilt)
}

android {
    namespace = "com.dplay.data"
}

dependencies{
    implementation(project.projects.core.network)
    implementation(project.projects.core.domain)
}
