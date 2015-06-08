/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stephen.williams@monco.info
 */
public class RaidPhpCrawler extends WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(RaidPhpCrawler.class);

    private static final Pattern FILTERS = Pattern.compile(
            ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private String path;

    CrawlStat myCrawlStat;

    public RaidPhpCrawler() {
        myCrawlStat = new CrawlStat();
        this.path = "webArchive\\";
    }

    public RaidPhpCrawler(String path) {
        myCrawlStat = new CrawlStat();
        this.path = path;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
    }

    @Override
    public void visit(Page page) {
        logger.info("Visited: {}", page.getWebURL().getURL());
        myCrawlStat.incProcessedPages();

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData parseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = parseData.getOutgoingUrls();
            myCrawlStat.incTotalLinks(links.size());
            try {
                myCrawlStat.incTotalTextSize(parseData.getText().getBytes("UTF-8").length);
                //attempt to save the webpage
                String content = new String(page.getContentData(), page.getContentCharset());
                WebURL weburl = page.getWebURL();
                String url = weburl.toString();
                path += url;

                path = path.replace("//", File.separator);
                path = path.replace("/", File.separator);
                path = path.replace(":", "");

                //TODO: Add a filename for TLD requests that don't specify a filename.
                //Can the filename of a website's index page be requested somehow?
                System.out.println("PATH: " + path);

                Path p = Paths.get(path);
                System.out.println("Creating directories...");

                //Files.createDirectories(p.getParent());
                Path parentDir = p.getParent();
                if (!Files.exists(parentDir)) {
                    Files.createDirectories(parentDir);
                }
                System.out.println("File object declaration...");
                File file = new File(path);

                System.out.println("File existance check...");
                if (!(file.exists())) {
                    //file.getParentFile().mkdirs();
                    file.createNewFile();
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.newLine();
                    bw.write(content);
                    bw.close();
                } else {
                    System.out.println("File exists already. Leaving it alone.");
                }

            } catch (UnsupportedEncodingException ignored) {
                // Do nothing
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RaidPhpCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // We dump this crawler statistics after processing every 50 pages
        if ((myCrawlStat.getTotalProcessedPages() % 50) == 0) {
            dumpMyData();
        }
    }

    /**
     * This function is called by controller to get the local data of this
     * crawler when job is finished
     */
    @Override
    public Object getMyLocalData() {
        return myCrawlStat;
    }

    /**
     * This function is called by controller before finishing the job. You can
     * put whatever stuff you need here.
     */
    @Override
    public void onBeforeExit() {
        dumpMyData();
    }

    public void dumpMyData() {
        int id = getMyId();
        // You can configure the log to output to file
        logger.info("Crawler {} > Processed Pages: {}", id, myCrawlStat.getTotalProcessedPages());
        logger.info("Crawler {} > Total Links Found: {}", id, myCrawlStat.getTotalLinks());
        logger.info("Crawler {} > Total Text Size: {}", id, myCrawlStat.getTotalTextSize());
    }
}
