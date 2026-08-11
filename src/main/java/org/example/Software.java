package org.example;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.*;

import java.nio.file.Paths;


public class Software {

public static void printScreen(Page page){
          var URL = page.title();
     try{
          
          page.screenshot(new Page.ScreenshotOptions()
                                    .setPath(Paths.get("C:\\Users\\besty\\Desktop\\Programs\\PW\\screenshot"+URL+".png"))
                                    .setFullPage(true));
     }catch (Exception e){
          
          System.out.println("Failed to print screen");
     }
    
     
}


     public static void press(
               Page page,
               String locator)
     {
          try
          {
               page.locator(locator).waitFor(
                         new Locator.WaitForOptions()
                                   .setTimeout(5000)
               );
               page.locator(locator).click();
               
          } catch (Exception e) {
               
               var URL = page.url();
               
               page.screenshot(new Page.ScreenshotOptions()
                                         .setPath(Paths.get("C:\\Users\\besty\\Desktop\\Programs\\PW\\press_"+URL+".png"))
                                         .setFullPage(true));
               
               System.out.println("Failed to press: ");
               System.out.println("  "+locator);
               System.out.println(" ");
          }
     }
     
     
     public static boolean loadSingleElement(
               Page page,
               String locator)
     {
          try {
               page.locator(locator).waitFor(
               new Locator.WaitForOptions()
                         .setTimeout(5000)
               );
               //System.out.println("Loading element: " + locator);
               return true;
               
          } catch (Exception e) {
               page.screenshot(new Page.ScreenshotOptions()
                                         .setPath(Paths.get("C:\\Users\\besty\\Desktop\\Programs\\PW\\loadSingleElement.png"))
                                         .setFullPage(true));
               System.out.println("Failed to load SINGLE ELEMENT");
               return false;
          }
     }
     
     /**
      *
      * @param page           the Playwright {@link Page} instance used to interact with the browser
      * @param expectedCount  the maximum allowed number of elements before triggering a page reload
      * @param xpath          the XPath expression used to locate elements (e.g. "//div[@class='item']")
      *
     
      * <h3>Behavior:</h3>
      * <ul>
      *   <li>Counts elements matching the XPath on the current page</li>
      *   <li>If count &gt; expectedCount, reloads the page once</li>
      *   <li>Counts elements again after reload</li>
      *   <li>Returns the final count</li>
      * </ul>
      *
      * <h3>Notes:</h3>
      * <ul>
      *   <li>XPath is auto-detected if it starts with "//" or similar patterns</li>
      *   <li>Use "xpath=" prefix explicitly</li>
      *   <li>The method performs only one reload; it does not retry multiple times</li>
      * </ul>
      *
      * <h3>Example:</h3>
      * <pre>
      * int count = elementsSumAndReloadIfMore(page, 5, "xpath="+"//li[@class='MenuOverlay-Item']");
      * System.out.println("Final count: " + count);
      * </pre>
      */
     
     public static int elementsSumAndReloadIfMore(Page page, String xpath, int expectedCount) {
          var URL = page.url();
          int count = 0;
          try {
               Locator elements = page.locator(xpath);
               
               // Wait until at least one element is visible (or attached)
               elements.first().waitFor();
               
               count = elements.count();
               System.out.println("Items per page: " + count);
               
               // Reload only if strictly greater than expectedCount
               if (count > expectedCount) {
                    page.reload();
                    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
                    
                    elements = page.locator(xpath);
                    elements.first().waitFor();
                    
                    count = elements.count();
                    System.out.println("Count after reload: " + count);
               }
               
               
          } catch (Exception e) {
               page.screenshot(new Page.ScreenshotOptions()
                                         .setPath(Paths.get("C:\\Users\\besty\\Desktop\\Programs\\PW\\elementsSumAndReloadIfMore.png"))
                                         .setFullPage(true));
               System.out.println("Failed to load elementsSumAndReloadIfMore: " + xpath);
          }
          return count;
     }







}









