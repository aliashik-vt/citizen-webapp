package uk.gov.service.bluebadge.test.acceptance.webdriver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages WebDriver (the client component of the WebDriver).
 *
 * <p>Together with {@linkplain WebDriverServiceProvider} implements <a
 * href="https://sites.google.com/a/chromium.org/chromedriver/getting-started#Controlling_'s_lifetime">
 * recommendations for performant use of the ChromeDriver</a>, where the WebDriver service is
 * started and stopped only once per testing session (costly operation, should be performed as few
 * times as possible) with only the WebDriver client being cycled with each test (cheap operation,
 * can be performed frequently).
 */
public class WebDriverProvider {

  private static final Logger log = getLogger(WebDriverProvider.class);

  private static ChromeOptions chromeOptions;
    private final boolean isBstackMode;
    private WebDriverServiceProvider webDriverServiceProvider;

  /**
   * When 'true', the WebDriver will be operating in a headless mode, i.e. not displaying web
   * browser window.
   */
  private boolean isHeadlessMode;

  /** When 'true', the WebDriver will be operating through zap proxy. */
  private boolean isZapMode;

  /** Location that the web browser will be downloading content into. */
  private Path downloadDirectory;

  private WebDriver webDriver;

  public WebDriverProvider(
      final WebDriverServiceProvider webDriverServiceProvider,
      final boolean isHeadlessMode,
      final Path downloadDirectory,
      final boolean isZapMode,
      boolean isBstackMode) {
    this.webDriverServiceProvider = webDriverServiceProvider;
    this.isHeadlessMode = isHeadlessMode;
    this.isZapMode = isZapMode;
    this.isBstackMode = isBstackMode;
    this.downloadDirectory = downloadDirectory;
  }

  public WebDriver getWebDriver() {
    if (webDriver == null) {
      initialise();
    }

    return webDriver;
  }

  /**
   * Initialises the WebDriver (client).
   *
   * <p>This method should be called once before each test to ensure that the session state doesn't
   * bleed from one test to another (such as user being logged in).
   */
  public void initialise() {

    if (null == chromeOptions) {
      chromeOptions = new ChromeOptions();

      final Map<String, Object> chromePrefs = new HashMap<>();
      log.info("Setting WebDriver download directory to '{}'.", downloadDirectory);
      chromePrefs.put("download.default_directory", downloadDirectory.toAbsolutePath().toString());

      chromeOptions.setExperimentalOption("prefs", chromePrefs);
      log.info(
          "Configuring WebDriver to run in {} mode.",
          isHeadlessMode ? "headless" : "full, graphical");
      chromeOptions.addArguments("window-size=1920,1080");
      if (isHeadlessMode) {
        chromeOptions.addArguments("--headless");
      }
    }

    if (isZapMode) {

      Proxy proxy = new Proxy();
      proxy.setHttpProxy("localhost:9999");
      proxy.setFtpProxy("localhost:9999");
      proxy.setSslProxy("localhost:9999");
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.PROXY, proxy);

      capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
      webDriver = new RemoteWebDriver(webDriverServiceProvider.getUrl(), capabilities);

    } else if (isBstackMode) {

      final String USERNAME = "sampathmahavitha2";
      final String AUTOMATE_KEY = "TgSoo4cFJycJxqXkzHxT";
      final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

      DesiredCapabilities caps = new DesiredCapabilities();
      //caps.setCapability("browser", "chrome");
      //caps.setCapability("browser_version", "62.0");
      caps.setCapability("browser", "Firefox");
      caps.setCapability("browser_version", "64.0 beta");
      //caps.setCapability("browser", "IE");
      //caps.setCapability("browser_version", "11.0");
      caps.setCapability("os", "Windows");
      caps.setCapability("os_version", "10");
      caps.setCapability("resolution", "1024x768");
      caps.setCapability("browserstack.local", "true");
      caps.setCapability("browserstack.localIdentifier", "Test123");
      caps.setCapability("browserstack.debug","true");

        try {
            webDriver = new RemoteWebDriver(new URL(URL), caps);
            //webDriver.get("http://google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    } else {
        webDriver = new RemoteWebDriver(webDriverServiceProvider.getUrl(), chromeOptions);
    }
  }

  /**
   * Stops the WebDriver client.
   *
   * <p>This method should be called once after each test to ensure that the session state doesn't
   * bleed from one test to another (such as user being logged in).
   */
  public void dispose() {
    webDriver.quit();
    webDriver = null;
  }
}
