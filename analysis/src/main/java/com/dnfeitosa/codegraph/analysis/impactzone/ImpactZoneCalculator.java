package com.dnfeitosa.codegraph.analysis.impactzone;

import com.dnfeitosa.codegraph.model.Impact;
import com.dnfeitosa.codegraph.model.ImpactZone;
import com.dnfeitosa.codegraph.model.Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImpactZoneCalculator {

    private List<Module> modules;

    public ImpactZoneCalculator(List<Module> modules) {
        this.modules = modules;
    }

    public ImpactZone impactOf(Module module) {
        List<Impact> impacts = impactOf(module, new HashSet<Module>());
        return new ImpactZone(module, impacts);
    }

    private List<Impact> impactOf(Module impacting, Set<Module> known) {
        if (known.contains(impacting)) {
            return Collections.emptyList();
        }

        List<Impact> impacts = new ArrayList<Impact>();
        for (Module module : modules) {
            if (module.dependsOn(impacting.getName())) {
                impacts.add(new Impact(impacting, module));
                impacts.addAll(impactOf(module, known));
                known.add(module);
            }
        }
        return impacts;
    }
}
