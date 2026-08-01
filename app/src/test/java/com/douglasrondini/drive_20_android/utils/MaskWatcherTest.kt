package com.douglasrondini.drive_20_android.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class MaskWatcherTest {

    @Test
    fun `unmask should remove all non-alphanumeric characters`() {
        val maskedPhone = "(65) 99650-8975"
        val expectedPhone = "65996508975"
        assertEquals(expectedPhone, MaskWatcher.unmask(maskedPhone))

        val maskedCpf = "123.456.789-11"
        val expectedCpf = "12345678911"
        assertEquals(expectedCpf, MaskWatcher.unmask(maskedCpf))

        val maskedPlate = "ABC-1D23"
        val expectedPlate = "ABC1D23"
        assertEquals(expectedPlate, MaskWatcher.unmask(maskedPlate))
    }
}
