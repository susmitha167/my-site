package my.site.my_site.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BookingFormModel {

    @Inject
    private String[] fromCities;

    @Inject
    private String[] toCities;

    @Inject
    private String[] travellerOptions;

    public List<String> getFromCities() {
        return fromCities != null ? Arrays.asList(fromCities) : null;
    }

    public List<String> getToCities() {
        return toCities != null ? Arrays.asList(toCities) : null;
    }

    public List<String> getTravellerOptions() {
        return travellerOptions != null ? Arrays.asList(travellerOptions) : null;
    }
}
