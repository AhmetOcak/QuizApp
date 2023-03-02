package com.quizapp.core.common

import android.content.Context
import coil.disk.DiskCache
import coil.memory.MemoryCache

object ImageLoader {
    fun load(context: Context): coil.ImageLoader.Builder {
        return coil.ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context).build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .build()
            }
    }
}