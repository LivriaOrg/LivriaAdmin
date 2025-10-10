package com.example.adminlivria.bookcontext.data.local

import com.example.adminlivria.bookcontext.domain.Book
import org.junit.Assert.assertEquals
import org.junit.Test

class BookEntityMapperTest {

    private val TEST_ID = 42
    private val TEST_TITLE = "El Principito"
    private val TEST_DESCRIPTION = "Un cuento filosófico"
    private val TEST_AUTHOR = "Antoine de Saint-Exupéry"
    private val TEST_GENRE = "Ficción"
    private val TEST_LANGUAGE = "FR"
    private val TEST_PRICE = 19.99
    private val TEST_PURCHASE_PRICE = 10.00
    private val TEST_STOCK = 25
    private val TEST_COVER = "base64encodedcoverstring"

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

    private fun assertEntityEquals(expected: BookEntity, actual: BookEntity) {
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
    fun bookEntity_toDomain_debe_mapear_todos_los_campos_correctamente() {
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

        val result = entity.toDomain()

        assertBookEquals(expected, result)
    }

    @Test
    fun book_toEntity_debe_mapear_todos_los_campos_correctamente() {
        val book = Book(
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

        val result = book.toEntity()

        assertEntityEquals(expected, result)
    }
}