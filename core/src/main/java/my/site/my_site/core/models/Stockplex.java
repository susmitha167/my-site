package my.site.my_site.core.models;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import my.site.my_site.core.models.StockModel;
import java.util.Map;
import com.day.cq.wcm.api.designer.Style;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import com.adobe.cq.export.json.ComponentExporter;

@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { ComponentExporter.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = { "training/components/content/stockplex" })
@Exporter(name = "jackson", extensions = { "json" })
public class Stockplex implements ComponentExporter
{
    @ScriptVariable
    private Resource resource;
    @ValueMapValue
    private String symbol;
    @ValueMapValue
    private String summary;
    @ValueMapValue
    private String showStockDetails;
    @ResourcePath(path = "/content/stocks")
    private Resource root;
    @ScriptVariable
    private Style currentStyle;
    private Map<String, Object> data;

    private StockModel getStock() {
        if (this.root == null) {
            return null;
        }
        final Resource stockResource = this.root.getChild(this.symbol);
        if (stockResource == null) {
            return null;
        }
        return (StockModel)stockResource.adaptTo((Class)StockModel.class);
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getShowStockDetails() {
        return this.showStockDetails;
    }

    public String getCurrentPrice() {
        final StockModel model = this.getStock();
        if (model == null) {
            return "No Data";
        }
        return String.valueOf(model.getLastTrade());
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    @PostConstruct
    public void constructDataMap() {
        this.data = new HashMap<String, Object>();
        final StockModel model = this.getStock();
        if (this.symbol != null && model != null) {
            this.data.put("Request Date", model.getRequestDate());
            this.data.put("Request Time", model.getRequestTime());
            this.data.put("UpDown", model.getUpDown());
            this.data.put("Open Price", model.getOpenPrice());
            this.data.put("Range High", model.getRangeHigh());
            this.data.put("Range Low", model.getRangeLow());
            this.data.put("Volume", model.getVolume());
            this.data.put("Company", model.getCompanyName());
            this.data.put("Sector", model.getSector());
            this.data.put("52 Week High", model.get52weekHigh());
            this.data.put("52 Week Low", model.get52weekLow());
            this.data.put("YTD % Change", model.getYtdPercentChange());
        }
    }

    public String getExportedType() {
        return this.resource.getResourceType();
    }
}