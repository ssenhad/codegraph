package com.dnfeitosa.codegraph.core.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VersionTest {

    @Test
    public void shouldTellWhetherAVersionDeclarationIsDynamicOrNot() {
        assertFalse(new Version("1.0").isDynamic());
        assertFalse(new Version("1.0-SNAPSHOT").isDynamic());
        assertFalse(new Version("1.0-RELEASE").isDynamic());
        assertTrue(new Version("1.+").isDynamic());
    }

}
