package mx.com.tarea3.util.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the ErrorCodesEnum enum.
 * This class tests the getName method of the ErrorCodesEnum enum.
 */
public class ErrorCodesEnumTest {

    @Test
    void testGetName_RNS001() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.RNS001;
        assertEquals("RNS001", errorCode.getName());
    }

    @Test
    void testGetName_RNS002() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.RNS002;
        assertEquals("RNS002", errorCode.getName());
    }

    @Test
    void testGetName_RNS004() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.RNS004;
        assertEquals("RNS004", errorCode.getName());
    }

    @Test
    void testGetName_RNN001() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.RNN001;
        assertEquals("RNN001", errorCode.getName());
    }

    @Test
    void testGetName_ERROR() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.ERROR;
        assertEquals("ERROR", errorCode.getName());
    }

    @Test
    void testGetName_INVALID_LINK() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.INVALID_LINK;
        assertEquals("INVALID_LINK", errorCode.getName());
    }

    @Test
    void testGetName_BAD_REQUEST() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.BAD_REQUEST;
        assertEquals("BAD_REQUEST", errorCode.getName());
    }

    @Test
    void testGetName_NOT_FOUND() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.NOT_FOUND;
        assertEquals("NOT_FOUND", errorCode.getName());
    }

    @Test
    void testGetName_NEW_LINK() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.NEW_LINK;
        assertEquals("NEW_LINK", errorCode.getName());
    }

    @Test
    void testGetName_CAPA_PERSISTENCIA() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.CAPA_PERSISTENCIA;
        assertEquals("CAPA_PERSISTENCIA", errorCode.getName());
    }

    @Test
    void testGetName_ERROR_EN_COMUNICACIONES() {
        ErrorCodesEnum errorCode = ErrorCodesEnum.ERROR_EN_COMUNICACIONES;
        assertEquals("ERROR_EN_COMUNICACIONES", errorCode.getName());
    }

}