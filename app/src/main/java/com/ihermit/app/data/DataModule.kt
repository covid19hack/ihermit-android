package com.ihermit.app.data

import com.ihermit.app.data.database.DatabaseModule
import com.ihermit.app.data.network.NetworkModule
import com.ihermit.app.data.preference.UserPreferenceModule
import dagger.Module

@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class,
        UserPreferenceModule::class
    ]
)
interface DataModule
