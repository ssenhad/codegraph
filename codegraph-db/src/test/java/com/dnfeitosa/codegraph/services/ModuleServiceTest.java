package com.dnfeitosa.codegraph.services;

import com.dnfeitosa.codegraph.db.graph.converters.ModuleConverter;
import com.dnfeitosa.codegraph.db.graph.repositories.ModuleRepository;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModuleServiceTest {

    private ModuleService moduleService;
    private ModuleRepository moduleRepository;

    @Before
    public void setUp() {
        moduleRepository = mock(ModuleRepository.class);
        moduleService = new ModuleService(moduleRepository, new ModuleConverter(), null, null);
    }

    @Test
    public void shouldReturnNullWhenModuleIsNotFound() {
        String moduleName = "non-existent";
        when(moduleRepository.findByName(moduleName)).thenReturn(null);

        assertNull(moduleService.find(moduleName));
    }
}