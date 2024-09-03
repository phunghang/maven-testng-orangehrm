package commons;

import org.aeonbits.owner.Config;

@Config.Sources({"file:environmentConfig/${environment}.properties"})
public interface Environment extends Config {
    @Key("App.Url")
    String getUrl();

    @Key("user")
    String getUsername ();

    @Key("password")
    String getPassword ();

}
