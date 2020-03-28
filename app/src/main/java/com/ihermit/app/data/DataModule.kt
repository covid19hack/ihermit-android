package com.ihermit.app.data

import com.ihermit.app.data.database.DatabaseModule
import dagger.Module

@Module(
    includes = [
        DatabaseModule::class
    ]
)
interface DataModule
