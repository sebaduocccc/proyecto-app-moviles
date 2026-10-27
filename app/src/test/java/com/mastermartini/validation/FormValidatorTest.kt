package com.mastermartini.validation

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class FormValidatorTest {

    private val hoy = LocalDate.of(2026, 10, 27)

    @Test
    fun emailValido() {
        assertNull(FormValidator.validarEmail("admin@mastermartini.cl"))
    }

    @Test
    fun emailInvalido() {
        assertNotNull(FormValidator.validarEmail(""))
        assertNotNull(FormValidator.validarEmail("admin@"))
        assertNotNull(FormValidator.validarEmail("admin.cl"))
    }

    @Test
    fun nombreSoloLetras() {
        assertNull(FormValidator.validarNombre("José", "nombre"))
        assertNotNull(FormValidator.validarNombre("J0se", "nombre"))
        assertNotNull(FormValidator.validarNombre("J", "nombre"))
    }

    @Test
    fun username() {
        assertNull(FormValidator.validarUsername("vixo_01"))
        assertNotNull(FormValidator.validarUsername("ab"))
        assertNotNull(FormValidator.validarUsername("con espacio"))
    }

    @Test
    fun passwordRegistro() {
        assertNull(FormValidator.validarPasswordRegistro("clave123"))
        assertNotNull(FormValidator.validarPasswordRegistro("abc12"))
        assertNotNull(FormValidator.validarPasswordRegistro("solotexto"))
        assertNotNull(FormValidator.validarPasswordRegistro("12345678"))
    }

    @Test
    fun fechaNacimiento() {
        assertNotNull(FormValidator.validarFechaNacimiento(null, hoy))
        assertNotNull(FormValidator.validarFechaNacimiento(hoy.plusDays(1), hoy))
        assertNotNull(FormValidator.validarFechaNacimiento(hoy.minusYears(12), hoy))
        assertNull(FormValidator.validarFechaNacimiento(hoy.minusYears(13), hoy))
    }
}
