package com.inovance.dam.core.config;

import lombok.Getter;
import lombok.Setter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * Configuration class for PLM synchronization settings.
 *
 * @author Leon
 */
@Designate(ocd = PLMConfiguration.Config.class)
@Component(
        immediate = true,
        service = PLMConfiguration.class,
        configurationPid = "com.inovance.dam.config.PLMConfiguration"
)
public class PLMConfiguration {

    // Configuration properties

    /**
     * Database name used for PLM synchronization.
     */
    @Getter
    @Setter
    private String databaseName;

    /**
     * Path to the Inovance directory.
     */
    @Getter
    @Setter
    private String inovanceFolderPath;

    /**
     * URL for Inovance IPMS query project.
     */
    @Getter
    @Setter
    private String ipmsProjectUrl;

    /**
     * URL for Inovance PBI SALES CATALOG.
     */
    @Getter
    @Setter
    private String pbiSalesCatalogUrl;

    /**
     * URL for Inovance PBI Sale cat.
     */
    @Getter
    @Setter
    private String pbisalecatUrl;

    /**
     * URL for Inovance PBI catalog.
     */
    @Getter
    @Setter
    private String pbicatalogUrl;

    /**
     * Username for ResourceResolver Factory.
     */
    @Getter
    @Setter
    private String username;

    /**
     * Password for ResourceResolver Factory.
     */
    @Getter
    @Setter
    private String password;

    /**
     * Initialize configuration class.
     */
    @ObjectClassDefinition(name = "PLM Configuration", description = "Configure PLM synchronization settings")
    public @interface Config {

        // Attribute definitions with descriptions

        @AttributeDefinition(
            name = "Database Name",
            description = "Specifies the name of the database where PLM synchronization data is stored."
        )
        String databaseName() default "idtuat_db";

        @AttributeDefinition(
            name = "Inovance Folder Path",
            description = "Specifies the path to the Inovance directory."
        )
        String inovanceFolderPath()
                default "/content/dam/inovance";

        @AttributeDefinition(
            name = "IPMS Project URL",
            description = "Specifies the URL for Inovance IPMS query project."
        )
        String ipmsProjectUrl()
                default "https://idtuat.inovance.com/Thingworx/Things/DT.Project.Connector/Services/getProjectsNormalByClouse";

        @AttributeDefinition(
            name = "PBI Sales Catalog URL",
            description = "Specifies the URL for Inovance PBI SALES CATALOG."
        )
        String pbiSalesCatalogUrl()
                default "https://idtuat.inovance.com/Thingworx/Things/SV.PLM.Connector/Services/getpbisalescatalog";

        @AttributeDefinition(
            name = "PBI Sale Catalog URL",
            description = "Specifies the URL for Inovance PBI Sale catalog."
        )
        String pbisalecatUrl()
                default "https://idtuat.inovance.com/Thingworx/Things/SV.PLM.Connector/Services/getpbisalecat";

        @AttributeDefinition(
            name = "PBI Catalog URL",
            description = "Specifies the URL for Inovance PBI catalog."
        )
        String pbicatalogUrl()
                default "https://idtuat.inovance.com/Thingworx/Things/SV.PLM.Connector/Services/getpbicatalog";

        @AttributeDefinition(
            name = "ResourceResolver Factory User",
            description = "Specifies the username for ResourceResolver Factory."
        )
        String username() default "admin";

        @AttributeDefinition(
            name = "ResourceResolver Factory Password",
            description = "Specifies the password for ResourceResolver Factory."
        )
        String password() default "admin";
    }


    // Activation and initialization

    /**
     * Initialize configuration values.
     *
     * @param config Configuration object with updated values.
     */
    @Modified
    @Activate
    private void init(Config config) {
        databaseName = config.databaseName();
        inovanceFolderPath = config.inovanceFolderPath();
        ipmsProjectUrl = config.ipmsProjectUrl();
        pbiSalesCatalogUrl = config.pbiSalesCatalogUrl();
        pbisalecatUrl = config.pbisalecatUrl();
        pbicatalogUrl = config.pbicatalogUrl();
        username = config.username();
        password = config.password();
    }
}
