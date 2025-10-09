import com.google.gson.annotations.SerializedName
import com.example.adminlivria.orderscontext.domain.Item
import com.example.adminlivria.orderscontext.domain.Order
import com.example.adminlivria.orderscontext.domain.Shipping
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

// DTO para la dirección de envío
data class ShippingDto(
    val address: String,
    val city: String,
    val district: String,
    val reference: String
)

// DTO para cada ítem (libro) en la orden
data class ItemDto(
    val id: Int,
    val bookId: Int,
    val bookTitle: String,
    val bookAuthor: String,
    val bookPrice: Double,
    val bookCover: String,
    val quantity: Int,
    val itemTotal: Double
)

// DTO principal para la Orden
data class OrderDto(
    val id: Int,
    val code: String,
    val userClientId: Int,
    val userEmail: String,
    val userPhone: String,
    val userFullName: String,
    val recipientName: String,
    val status: String,
    @SerializedName("isDelivery")
    var isDelivery: Boolean,

    val shipping: ShippingDto,
    val total: Double,
    val date: String,
    val items: List<ItemDto>
)

// --- Extension Functions (Mappers) ---

// Mapea de DTO a la Entidad de Dominio
fun OrderDto.toDomain() = Order(
        id = id,
        code = code,
        userClientId = userClientId,
        userEmail = userEmail,
        userPhone = userPhone,
        userFullName = userFullName,
        recipientName = recipientName,
        status = status,
        isDelivery = isDelivery,
        shipping = shipping.toDomain(),
        total = total,
        date = try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            LocalDateTime.parse(date, formatter).toInstant(ZoneOffset.UTC)
        } catch (e: Exception) {
            Instant.now()
        },
        items = items.map { it.toDomain() }
    )

fun ShippingDto.toDomain() = Shipping(address, city, district, reference)
fun ItemDto.toDomain() = Item(id, bookId, bookTitle, bookAuthor, bookPrice, bookCover, quantity, itemTotal)

// Mapea de Entidad de Dominio a DTO
fun Order.toDto(): OrderDto {
    return OrderDto(
        id = id,
        code = code,
        userClientId = userClientId,
        userEmail = userEmail,
        userPhone = userPhone,
        userFullName = userFullName,
        recipientName = recipientName,
        status = status,
        isDelivery = isDelivery,
        shipping = shipping.toDto(),
        total = total,
        date = date.toString(),
        items = items.map { it.toDto() }
    )
}

fun Shipping.toDto() = ShippingDto(address, city, district, reference)
fun Item.toDto() = ItemDto(id, bookId, bookTitle, bookAuthor, bookPrice, bookCover, quantity, itemTotal)