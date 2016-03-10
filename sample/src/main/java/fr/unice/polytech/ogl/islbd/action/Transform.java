package fr.unice.polytech.ogl.islbd.action;


import fr.unice.polytech.ogl.islbd.objective.AdvResource;
import fr.unice.polytech.ogl.islbd.objective.BasicResource;

import fr.unice.polytech.ogl.islbd.objective.IResource;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Transform extends Action {

    public Transform(Map<BasicResource, Integer> resources) {
        super("transform");
        for (BasicResource resource : resources.keySet()) {
            addParameter(resource.toString(), resources.get(resource));
        }
    }

    public int getProduction() {
        JSONObject extras = (JSONObject) this.getConsequence().get("extras");
        return Integer.parseInt(extras.get("production").toString());
    }

    public AdvResource getKind() {
        JSONObject extras = (JSONObject) this.getConsequence().get("extras");
        return AdvResource.getResource((String) extras.get("kind"));
    }

    public int usedResource(BasicResource res) {
        String resStr = res.name();
        Object amount = this.getParameter(resStr);
        if (amount == null) {
            return 0;
        }
        return Integer.parseInt(amount.toString());
    }

    public Map<BasicResource, Integer> usedResources() {
        Map<BasicResource, Integer> usedResources = new HashMap<>();
        Map<String, Object> parameters = this.getParameters();
        for (Entry<String, Object> resAmountentry : parameters.entrySet()) {
            BasicResource bRes = BasicResource.getResource(resAmountentry.getKey());
            int amount = Integer.parseInt(resAmountentry.getValue().toString());
            usedResources.put(bRes, amount);
        }
        return usedResources;
    }
}
