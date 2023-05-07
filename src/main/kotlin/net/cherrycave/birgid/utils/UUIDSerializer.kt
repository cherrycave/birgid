package net.cherrycave.birgid.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal typealias SerializableUUID = @Serializable(with = UUIDSerializer::class) java.util.UUID

internal class UUIDSerializer : KSerializer<java.util.UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: java.util.UUID) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): java.util.UUID {
        return java.util.UUID.fromString(decoder.decodeString())
    }
}