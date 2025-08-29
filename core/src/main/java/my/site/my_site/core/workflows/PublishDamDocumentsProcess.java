package my.site.my_site.core.workflows;
import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import javax.jcr.Session;
import java.util.Collections;
import java.util.Map;
@Component(service = WorkflowProcess.class, property = {
        "process.label=Publish DAM Documents Under Hierarchy"
})
public class PublishDamDocumentsProcess implements WorkflowProcess {
    @Reference
    private ResourceResolverFactory resolverFactory;
    @Reference
    private Replicator replicator;
    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        String payloadPath = item.getWorkflowData().getPayload().toString();
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "workflow-service"))) {
            Resource root = resolver.getResource(payloadPath);
            if (root != null) {
                findAndPublishPDFs(root, resolver);
            }
        } catch (LoginException | ReplicationException e) {
            throw new WorkflowException("Error during PDF publishing", e);
        }
    }
    private void findAndPublishPDFs(Resource resource, ResourceResolver resolver) throws ReplicationException {
        for (Resource child : resource.getChildren()) {
            if (child.isResourceType("dam:Asset")) {
                Asset asset = child.adaptTo(Asset.class);
                if (asset != null) {
                    Map<String, Object> metadata = asset.getMetadata();
                    String mimeType = (String) metadata.get("dc:format");

                    if ("application/pdf".equals(mimeType)) {
                        replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, asset.getPath());
                    }
                }
            } else {
                findAndPublishPDFs(child, resolver); // Recursive
            }
        }
    }
}



