package com.example.adminlivria.bookcontext.data.local

import com.example.adminlivria.bookcontext.domain.Book
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests para verificar el mapeo entre Book y BookEntity (bidireccional).
 * Estos tests validan que las funciones toEntity() y toDomain() funcionen correctamente.
 */
class BookEntityMappingTest {

    private val TEST_ID = 42
    private val TEST_TITLE = "El Principito"
    private val TEST_DESCRIPTION = "Un clásico de la literatura"
    private val TEST_AUTHOR = "Antoine de Saint-Exupéry"
    private val TEST_GENRE = "Ficción"
    private val TEST_LANGUAGE = "Español"
    private val TEST_PRICE = 15.99
    private val TEST_PURCHASE_PRICE = 5.50
    private val TEST_STOCK = 25
    private val TEST_COVER = "base64_cover_image"

    private fun assertBookEquals(expected: Book, actual: Book) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.title, actual.title)
        assertEquals(expected.description, actual.description)
        assertEquals(expected.author, actual.author)
        assertEquals(expected.genre, actual.genre)
        assertEquals(expected.language, actual.language)
        assertEquals(expected.price, actual.price, 0.001)
        assertEquals(expected.purchasePrice, actual.purchasePrice, 0.001)
        assertEquals(expected.stock, actual.stock)
        assertEquals(expected.cover, actual.cover)
    }

    @Test
    fun toDomain_debe_convertir_BookEntity_a_Book_correctamente() {
        // Arrange
        val entity = BookEntity(
            id = TEST_ID,
            title = TEST_TITLE,
            description = TEST_DESCRIPTION,
            author = TEST_AUTHOR,
            genre = TEST_GENRE,
            language = TEST_LANGUAGE,
            price = TEST_PRICE,
            purchasePrice = TEST_PURCHASE_PRICE,
            stock = TEST_STOCK,
            cover = TEST_COVER
        )

        val expected = Book(
            id = TEST_ID,
            title = TEST_TITLE,
            description = TEST_DESCRIPTION,
            author = TEST_AUTHOR,
            genre = TEST_GENRE,
            language = TEST_LANGUAGE,
            price = TEST_PRICE,
            purchasePrice = TEST_PURCHASE_PRICE,
            stock = TEST_STOCK,
            cover = TEST_COVER
        )

        // Act
        val result = entity.toDomain()

        // Assert
        assertBookEquals(expected, result)
    }

    @Test
    fun toEntity_debe_convertir_Book_a_BookEntity_correctamente() {
        // Arrange
        val domain = Book(
            id = TEST_ID,
            title = TEST_TITLE,
            description = TEST_DESCRIPTION,
            author = TEST_AUTHOR,
            genre = TEST_GENRE,
            language = TEST_LANGUAGE,
            price = TEST_PRICE,
            purchasePrice = TEST_PURCHASE_PRICE,
            stock = TEST_STOCK,
            cover = TEST_COVER
        )

        val expected = BookEntity(
            id = TEST_ID,
            title = TEST_TITLE,
            description = TEST_DESCRIPTION,
            author = TEST_AUTHOR,
            genre = TEST_GENRE,
            language = TEST_LANGUAGE,
            price = TEST_PRICE,
            purchasePrice = TEST_PURCHASE_PRICE,
            stock = TEST_STOCK,
            cover = TEST_COVER
        )

        // Act
        val result = domain.toEntity()

        // Assert
        assertEquals(expected, result)
    }
}
