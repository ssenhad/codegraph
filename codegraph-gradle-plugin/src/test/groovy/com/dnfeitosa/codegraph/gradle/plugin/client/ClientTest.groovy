package com.dnfeitosa.codegraph.gradle.plugin.client

import org.gradle.api.InvalidUserDataException
import org.junit.Test

public class ClientTest {

    @Test(expected = InvalidUserDataException.class)
    def void shouldThrowExceptionWhenUrlIsNull() {
        new Client(null)
    }

    @Test(expected = InvalidUserDataException.class)
    def void shouldThrowExceptionWhenUrlIsEmpty() {
        new Client('')
    }
}