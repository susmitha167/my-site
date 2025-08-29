package my.site.my_site.core.models;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)


public class SusmithaModel {

        @ValueMapValue
        private String image;

        @ValueMapValue
        private String link;

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    public String getLink() {
        return link;
    }
    }


