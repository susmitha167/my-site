package my.site.my_site.core.models;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Calendar;
import com.day.cq.commons.Filter;
import com.day.cq.wcm.api.PageFilter;
import java.util.ArrayList;
import org.slf4j.Logger;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import com.day.cq.wcm.api.designer.Style;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { SlingHttpServletRequest.class }, resourceType = { "training/components/structure/footer" }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Footer
{
    @ScriptVariable
    private Style currentStyle;
    @ScriptVariable
    private Page currentPage;
    @ScriptVariable
    private PageManager pageManager;
    @ScriptVariable
    private Logger log;
    private ArrayList<Page> pageList;
    private int curYear;

    @PostConstruct
    public void setup() {
        this.pageList = new ArrayList<Page>();
        final Object policyValue = this.currentStyle.get((Object)"pages");
        String[] pagesFromPolicy = null;
        if (policyValue instanceof String[]) {
            pagesFromPolicy = (String[])policyValue;
        }
        else if (policyValue instanceof String) {
            pagesFromPolicy = new String[] { (String)policyValue };
        }
        if (pagesFromPolicy != null && pagesFromPolicy.length > 0) {
            String[] array;
            for (int length = (array = pagesFromPolicy).length, i = 0; i < length; ++i) {
                final String pathPath = array[i];
                final Page page = this.pageManager.getPage(pathPath);
                this.pageList.add(page);
            }
        }
        else {
            Page root = this.currentPage.getAbsoluteParent(2);
            if (root == null) {
                root = this.currentPage;
            }
            final Iterator<Page> it = (Iterator<Page>)root.listChildren((Filter)new PageFilter());
            while (it.hasNext()) {
                this.pageList.add(it.next());
            }
        }
        this.curYear = Calendar.getInstance().get(1);
    }

    public int getCurrentYear() {
        return this.curYear;
    }

    public ArrayList<Page> getItems() {
        return this.pageList;
    }
}
