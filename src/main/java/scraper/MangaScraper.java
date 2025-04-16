package scraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import swingComponents.InputBox;
import util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MangaScraper {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String url = "https://weebcentral.com/";
    private Random rand = new Random();

    public MangaScraper() {
        WebDriverManager.firefoxdriver().setup();
    }

    public void openMainPage() {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    public void closeMainPage() {
        driver.close();
    }

    public List<WebElement> searchManga(String title) throws InterruptedException {
        Thread.sleep(rand.nextInt(1000) + 1000);
        driver.findElement(By.id("quick-search-input")).sendKeys(title);
        Thread.sleep(rand.nextInt(1000) + 1000);
        return driver.findElements(By.xpath("//*[@id=\"quick-search-result\"]/div[2]/a"));
    }

    public WebElement findManga(List<WebElement> elements) throws Exception {
        StringBuilder allManga = new StringBuilder("These are the mangas that you searched\n\n");

        for (WebElement element : elements) {
            allManga.append(element.getText()).append("\n");
        }

        allManga.append("\nWhich manga do you want to scrape?\n");

        InputBox inputBox = new InputBox();
        String read = inputBox.getInput(allManga.toString());

        if (read == null) {

        } else {
            for (WebElement element : elements) {
                if (element.getText().equalsIgnoreCase(read)) {
                    return element;
                }
            }
        }

        return elements.get(new Random().nextInt(elements.size()));
    }


    public List<WebElement> getChapters() throws InterruptedException {
        Thread.sleep(rand.nextInt(1000) + 1000);

        WebElement showAllChapters = null;

        try {
            showAllChapters = driver.findElement(By.xpath("//*[contains(text(), 'Show All Chapters')]"));
        } catch (NoSuchElementException e)
        {

        }

        if(showAllChapters != null) {
            showAllChapters.click();
        }

        Thread.sleep(rand.nextInt(1000) + 3000);

        return driver.findElements(By.xpath("//*[@id=\"chapter-list\"]/div/a"));
    }

    public List<WebElement> getChapterNames() {
        return driver.findElements(By.xpath("//*[@id=\"chapter-list\"]/div/a/span[2]/span[1]"));
    }

    public void scrapeAllChapters(String mangaName, String path) throws InterruptedException, IOException {
        int j = 0;
        String mangaPath = path + "/" + mangaName;
        Util.createFolder(mangaName, path);

        List<WebElement> elements = this.getChapters();
        List<String> chapterLinks = new ArrayList<>();
        List<WebElement> chapterNames = this.getChapterNames();
        List<String> chapterNamesList = new ArrayList<>();

        for (WebElement element : elements) {
            chapterLinks.add(element.getAttribute("href"));
        }

        for (WebElement element : chapterNames) {
            chapterNamesList.add(element.getText());
        }

        chapterNamesList = chapterNamesList.reversed();
        chapterLinks = chapterLinks.reversed();

        for (WebElement element : elements) {
            if(Util.checkFolder(mangaPath + "/" + chapterNamesList.get(j)))
            {
                j++;
                continue;
            }
            String mangaUrl = chapterLinks.get(j);
            Util.createFolder(chapterNamesList.get(j), mangaPath);
            this.scrapeChapter(mangaUrl, mangaPath + "/" + chapterNamesList.get(j));

            j++;
        }
    }

    public void scrapeChapter(String chapterUrl, String path) throws InterruptedException, IOException {
        int i = 1;

        Thread.sleep(rand.nextInt(1000) + 1000);
        driver.get(chapterUrl);
        Thread.sleep(rand.nextInt(1000) + 5000);
        List<WebElement> elements = driver.findElements(By.xpath("/html/body/main/section[3]/img"));

        for (WebElement element : elements) {
            String url = element.getAttribute("src");
            Thread.sleep(rand.nextInt(1000) + 1000);
            Util.downloadImage(url, path + "/" + "page"+ i++ + ".png"); // bug here
        }

    }


}

