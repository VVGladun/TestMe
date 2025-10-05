package gladun.vlad.testme.data.network

import androidx.annotation.Keep
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import gladun.vlad.testme.data.model.ReserveState

@Keep
class JsonAdapters {
    @FromJson
    fun fromJson(reader: JsonReader): ReserveState? {
        return when (reader.peek()) {
            JsonReader.Token.NUMBER -> ReserveState.fromValue(reader.nextInt())
            JsonReader.Token.NULL -> {
                reader.nextNull()
            }
            else -> {
                reader.skipValue()
                ReserveState.NONE
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: ReserveState?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.value)
        }
    }
}