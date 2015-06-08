/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monco.calendarofveeshan;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author stephen.williams@monco.info
 */
public class RaidPhpController {
      private static final Logger logger = LoggerFactory.getLogger(RaidPhpController.class);

  public static void main(String[] args) throws Exception {
      String [] argy = {"",""};
      args = argy;
      args[0] = "C:\\webpageArchive\\";
      args[1] = "1";
              
      
    if (args.length != 2) {
      logger.info("Needed parameters: ");
      logger.info("\t rootFolder (it will contain intermediate crawl data)");
      logger.info("\t numberOfCralwers (number of concurrent threads)");
      return;
    }

    String rootFolder = args[0];
    int numberOfCrawlers = Integer.parseInt(args[1]);

    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(rootFolder);
    config.setMaxPagesToFetch(10);
    config.setPolitenessDelay(1000);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    //controller.addSeed("http://127.0.0.1/xampp/");
    controller.addSeed("https://www.project1999.com/raid.php");
    controller.start(RaidPhpCrawler.class, numberOfCrawlers);

    /*
    List<Object> crawlersLocalData = controller.getCrawlersLocalData();
    long totalLinks = 0;
    long totalTextSize = 0;
    int totalProcessedPages = 0;
    for (Object localData : crawlersLocalData) {
      CrawlStat stat = (CrawlStat) localData;
      totalLinks += stat.getTotalLinks();
      totalTextSize += stat.getTotalTextSize();
      totalProcessedPages += stat.getTotalProcessedPages();

    }

    logger.info("Aggregated Statistics:");
    logger.info("\tProcessed Pages: {}", totalProcessedPages);
    logger.info("\tTotal Links found: {}", totalLinks);
    logger.info("\tTotal Text Size: {}", totalTextSize);
    */
  }
}
