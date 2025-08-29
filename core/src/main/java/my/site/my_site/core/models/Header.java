package my.site.my_site.core.models;

import java.util.List;
import javax.annotation.PostConstruct;
import java.util.Iterator;
import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.PageFilter;
import java.util.ArrayList;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { SlingHttpServletRequest.class },
        resourceType = { "training/components/structure/header" },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Header
{
    @ScriptVariable
    private Page currentPage;
    @ScriptVariable
    private SlingHttpServletRequest request;
    private Page root;
    private ArrayList<Page> pages;

    @PostConstruct
    private void setup() {
        this.root = this.currentPage.getAbsoluteParent(2);
        if (this.root == null) {
            this.root = this.currentPage;
        }
        this.pages = new ArrayList<Page>();
        final Iterator<Page> it = (Iterator<Page>)this.root.listChildren((Filter)new PageFilter());
        while (it.hasNext()) {
            this.pages.add(it.next());
        }
    }

    public String getAnonymous() {
        return (this.request.getRequestParameter("anonymous") != null) ? this.request.getRequestParameter("anonymous").toString() : "false";
    }

    public Page getRootPage() {
        return this.root;
    }

    public List<Page> getItems() {
        return this.pages;
    }
}
