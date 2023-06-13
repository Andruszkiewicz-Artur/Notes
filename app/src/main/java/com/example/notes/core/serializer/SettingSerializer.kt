package com.example.notes.core.serializer

import androidx.datastore.core.Serializer
import com.example.notes.core.model.ProfileModel
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingSerializer: Serializer<ProfileModel> {
    override val defaultValue: ProfileModel
        get() = ProfileModel()

    override suspend fun readFrom(input: InputStream): ProfileModel {
        return try {
            Json.decodeFromString(
                deserializer = ProfileModel.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ProfileModel, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ProfileModel.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}