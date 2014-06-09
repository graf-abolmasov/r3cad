import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

driver = getPhantomJsDriver()

environments {
    chrome {
        driver = getChromeDriver()
    }
    ie {
        driver = getInternetExplorerDriver()
    }
    phantomjs {
        driver = getPhantomJsDriver()
    }
    ff {
        driver = getFirefoxDriver()
    }
}

private Closure<RemoteWebDriver> getChromeDriver() {
    String chromeDriverVersion = "2.10"

    String chromeDriverZipFileName
    String chromeDriverExecFileName

    if (Platform.current.is(Platform.WINDOWS)) {
        chromeDriverZipFileName = "chromedriver_win32.zip"
        chromeDriverExecFileName = "chromedriver.exe"
    } else if (Platform.current.is(Platform.MAC)) {
        chromeDriverZipFileName = "chromedriver_mac32.zip"
        chromeDriverExecFileName = "chromedriver"
    } else if (Platform.current.is(Platform.LINUX)) {
        chromeDriverZipFileName = "chromedriver_linux32.zip"
        chromeDriverExecFileName = "chromedriver"
    } else {
        throw new RuntimeException("Unsupported operating system [${Platform.current}]")
    }

    String chromeDriverDownloadFullPath = "http://chromedriver.storage.googleapis.com/${chromeDriverVersion}/${chromeDriverZipFileName}"

    File chromeDriverLocalFile = downloadDriver(chromeDriverDownloadFullPath, chromeDriverExecFileName, 'zip')

    System.setProperty('webdriver.chrome.driver', chromeDriverLocalFile.absolutePath)
    return { new ChromeDriver() }
}

private Closure<RemoteWebDriver> getInternetExplorerDriver() {
    String ieDriverVersion = "2.42.2"
    String ieDriverVersionMajor = ieDriverVersion.substring(0, ieDriverVersion.lastIndexOf('.'))

    String ieDriverZipFileName = "IEDriverServer_Win32_${ieDriverVersion}.zip"

    String ieDriverDownloadFullPath = "http://selenium-release.storage.googleapis.com/${ieDriverVersionMajor}/${ieDriverZipFileName}"

    File ieDriverLocalFile = downloadDriver(ieDriverDownloadFullPath, "IEDriverServer.exe", 'zip')

    System.setProperty('webdriver.ie.driver', ieDriverLocalFile.absolutePath)
    return { new InternetExplorerDriver() }
}

private Closure<RemoteWebDriver> getPhantomJsDriver() {
    String phantomJSVersion = '1.9.7'

    String platform
    String archiveExtension
    String execFilePath

    if (Platform.current.is(Platform.WINDOWS)) {
        execFilePath = 'phantomjs.exe'
        platform = 'windows'
        archiveExtension = 'zip'
    } else if (Platform.current.is(Platform.MAC)) {
        execFilePath = '/bin/phantomjs'
        platform = 'macosx'
        archiveExtension = 'zip'
    } else if (Platform.current.is(Platform.LINUX)) {
        execFilePath = '/bin/phantomjs'
        platform = 'linux-i686'
        archiveExtension = 'tar.bz2'
    } else {
        throw new RuntimeException("Unsupported operating system [${Platform.current}]")
    }

    String phantomjsExecPath = "phantomjs-${phantomJSVersion}-${platform}/${execFilePath}"

    String phantomJsFullDownloadPath = "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-${phantomJSVersion}-${platform}.${archiveExtension}"

    File phantomJSDriverLocalFile = downloadDriver(phantomJsFullDownloadPath, phantomjsExecPath, archiveExtension)

    System.setProperty('phantomjs.binary.path', phantomJSDriverLocalFile.absolutePath)
    return {
        Capabilities caps = DesiredCapabilities.phantomjs()
        def phantomJsDriver = new PhantomJSDriver(PhantomJSDriverService.createDefaultService(caps), caps)
        phantomJsDriver.manage().window().setSize(new Dimension(1028, 768))
        phantomJsDriver
    }
}

private Closure<RemoteWebDriver> getFirefoxDriver() {
    return { new FirefoxDriver() }
}

private File downloadDriver(String driverDownloadFullPath, String driverFilePath, String archiveFileExtension) {
    File destinationDirectory = new File("target/drivers")
    if (!destinationDirectory.exists()) {
        destinationDirectory.mkdirs()
    }

    File driverFile = new File("${destinationDirectory.absolutePath}/${driverFilePath}")

    String localArchivePath = "target/driver.${archiveFileExtension}"

    if (!driverFile.exists()) {
        def ant = new AntBuilder()
        ant.get(src: driverDownloadFullPath, dest: localArchivePath)

        if (archiveFileExtension == "zip") {
            ant.unzip(src: localArchivePath, dest: destinationDirectory)
        } else {
            ant.untar(src: localArchivePath, dest: destinationDirectory, compression: 'bzip2')
        }

        ant.delete(file: localArchivePath)
        ant.chmod(file: driverFile, perm: '700')
    }

    return driverFile
}