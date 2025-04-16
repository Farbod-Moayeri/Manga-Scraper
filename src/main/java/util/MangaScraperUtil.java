package util;

import org.openqa.selenium.WebElement;
import scraper.MangaScraper;
import swingComponents.FileChooser;
import swingComponents.InputBox;

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
            scraper.openMainPage();
            InputBox inputBox = new InputBox();
            try {
                mangaName = inputBox.getInput("What manga do you want to download?");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if(mangaName == null)
                break;
//            System.out.println("What manga do you want to download?");
//            mangaName = Util.readString();
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
            FileChooser fileChooser = new FileChooser();
            String path = fileChooser.getPath();

            manga.click();
            if (path != null) {
                try {
                    scraper.scrapeAllChapters(mangaNameFinal, path);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                continueOption = inputBox.getInput("Do you want to continue? (Y/N)");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (continueOption.toLowerCase().equals("n") || continueOption.toLowerCase().equals("no") || continueOption.toLowerCase().equals("no\n"))
            {
                cont = false;
            }

            scraper.closeMainPage();
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread((Runnable) this, "Thread");
            t.start();
        }
    }
}
