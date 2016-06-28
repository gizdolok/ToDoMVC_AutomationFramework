package todomvc.webdriver;

/**
 * Created by Daniel Kolodzig on 25.09.2015.
 */
public interface Config {
    String todomvcReactURL            = System.getProperty("todomvcReactURL","http://todomvc.com/examples/react/");
    String browser                    = System.getProperty("browser", System.getenv().get("BROWSER"));
}
