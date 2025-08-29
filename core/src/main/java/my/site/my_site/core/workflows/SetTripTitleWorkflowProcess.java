package my.site.my_site.core.workflows;

import com.day.cq.workflow.WorkflowException;

import com.day.cq.workflow.WorkflowSession;

import com.day.cq.workflow.exec.WorkItem;

import com.day.cq.workflow.exec.WorkflowProcess;

import com.day.cq.workflow.metadata.MetaDataMap;

import org.apache.sling.api.resource.*;

import org.osgi.service.component.annotations.Component;

import org.osgi.framework.Constants;

import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;

import java.util.HashMap;

import java.util.Map;

@Component(

        service = WorkflowProcess.class,

        property = {

                Constants.SERVICE_DESCRIPTION + "=Set tripTitle property from jcr:title",

                "process.label=Set Trip Title Property"

        }

)

public class SetTripTitleWorkflowProcess implements WorkflowProcess {

    @Reference

    private ResourceResolverFactory resolverFactory;

    @Override

    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args)

            throws WorkflowException {

        String payloadPath = workItem.getWorkflowData().getPayload().toString();

        ResourceResolver resourceResolver = null;

        try {

            Map<String, Object> param= new HashMap<>();

            param.put(ResourceResolverFactory.SUBSERVICE,"workflowService");

            resourceResolver = resolverFactory.getServiceResourceResolver(param);

            Resource pageRes = resourceResolver.getResource(payloadPath + "/jcr:content");

            if (pageRes != null) {

                ModifiableValueMap props = pageRes.adaptTo(ModifiableValueMap.class);

                String title = props.get("jcr:title", "");

                if (!title.isEmpty()) {

                    props.put("tripTitle", title); // Custom property set here

                }

                resourceResolver.commit();

            }

        } catch (Exception e) {

            throw new WorkflowException("Error setting tripTitle", e);

        } finally {

            if (resourceResolver != null && resourceResolver.isLive()) {

                resourceResolver.close();

            }

        }

    }

}

