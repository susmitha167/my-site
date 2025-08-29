package my.site.my_site.core.models;

import javax.inject.Named;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = { Resource.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class StockModel
{
    @Self
    private Resource stock;
    @ChildResource
    private Resource lastTrade;
    @ChildResource
    @Named("lastTrade")
    private ValueMap lastTradeValues;

    public String getSymbol() {
        return this.stock.getName();
    }

    public double getLastTrade() {
        return (double)this.lastTradeValues.get("lastTrade", (Class)Double.class);
    }

    public String getRequestDate() {
        return (String)this.lastTradeValues.get("dayOfLastUpdate", (Class)String.class);
    }

    public String getRequestTime() {
        return (String)this.lastTradeValues.get("timeOfUpdate", (Class)String.class);
    }

    public double getUpDown() {
        return (double)this.lastTradeValues.get("upDown", (Class)Double.class);
    }

    public double getOpenPrice() {
        return (double)this.lastTradeValues.get("openPrice", (Class)Double.class);
    }

    public double getRangeHigh() {
        return (double)this.lastTradeValues.get("rangeHigh", (Class)Double.class);
    }

    public double getRangeLow() {
        return (double)this.lastTradeValues.get("rangeLow", (Class)Double.class);
    }

    public int getVolume() {
        return (int)this.lastTradeValues.get("volume", (Class)Integer.class);
    }

    public String getCompanyName() {
        return (String)this.lastTradeValues.get("companyName", (Class)String.class);
    }

    public String getSector() {
        return (String)this.lastTradeValues.get("sector", (Class)String.class);
    }

    public double get52weekHigh() {
        return (double)this.lastTradeValues.get("week52High", (Class)Double.class);
    }

    public double get52weekLow() {
        return (double)this.lastTradeValues.get("week52Low", (Class)Double.class);
    }

    public double getYtdPercentChange() {
        return (double)this.lastTradeValues.get("ytdPercentageChange", (Class)Double.class);
    }
}
