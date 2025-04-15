package util;

import org.openqa.selenium.WebElement;
import scraper.MangaScraper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MangaScraperUtil implements Runnable{

    static final String PATH = "C:\\Users\\Farbod Moayeri\\IdeaProjects\\MangaScraper\\src\\main\\resources\\mangas";
    private Thread t;

    public void run () {
        boolean cont = true;
        String continueOption = "";
        String mangaName = "";
        String mangaNameFinal = "";
        WebElement manga = null;
        List<WebElement> listOfManga = new ArrayList<>();
        MangaScraper scraper = new MangaScraper();

        while (cont)
        {
            System.out.println("What manga do you want to download?");
            mangaName = Util.readString();
            try {
                listOfManga = scraper.searchManga(mangaName);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                manga = scraper.findManga(listOfManga);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            mangaNameFinal = manga.getText();
            manga.click();

            try {
                scraper.scrapeAllChapters(mangaNameFinal, PATH);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Do you want to continue? (Y/N)");
            continueOption = Util.readString();

            if (continueOption.toLowerCase().equals("n"))
            {
                cont = false;
            }
            scraper.openMainPage();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread((Runnable) this, "Thread");
            t.start();
        }
    }
}
