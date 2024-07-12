import com.hankki.build_logic.setNamespace

plugins {
    alias(libs.plugins.hankki.library)
}

android {
    setNamespace("core.datastore")
}
