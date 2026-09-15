package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.example.JavaSoftware.println;

import static org.example.Software.*;
import static org.example.Variables.xpath;
import static org.example.Variables.xpath.MENU_TOTAL;

public class MainTest {

private Playwright playwright;
private Browser browser;
private BrowserContext context;
private static Page page;

@BeforeEach
void setUp() {
     
     playwright = Playwright.create();
     browser = playwright.chromium().launch(
               new BrowserType.LaunchOptions().setHeadless(false));
     context = browser.newContext(
               new Browser.NewContextOptions().setIgnoreHTTPSErrors(true));
     page = context.newPage();
}

@Test()
void menu() {
     
     page.navigate(xpath.MAIN_PAGE);
     
     
// Loop over TOP MENU
     for (int menu = 1; menu <= MENU_TOTAL; menu++)
     {
          String menuElement = xpath.TOP_MENU_ELEMENT + "[" + menu + "]";
          
          
          press(page, menuElement);
          println(page.url() + " " + menuElement);
          
          nextPage(page); // Loop OVER button "->" next page
     }
     
     page.waitForTimeout(1000);
     
     println(page.title());
     println(page.url());
}

@AfterEach
void tearDown() {
     context.close();
     browser.close();
     playwright.close();
}




/**
 * Loops over the "->" Next Page button to process all available pages.
 *
 * <p>
 * The method:
 * <ul>
 *     <li>Starts from page 1</li>
 *     <li>Counts the items available on the current page</li>
 *     <li>Processes all items using {@code pressItems()}</li>
 *     <li>Attempts to click the "->" Next Page button</li>
 *     <li>Increases the page number after successfully moving to the next page</li>
 *     <li>Continues until the Next Page button is no longer available</li>
 *     <li>Stops the loop when the next-page action throws an exception</li>
 * </ul>
 *
 * <p>
 * This allows the test to process an unknown number of pagination pages
 * without requiring a predefined number of pages.
 */

public static void nextPage (Page page1)
{
     int pageNumber = 1;
     boolean nextPageArrow = true;
     
     
     while ( nextPageArrow )
     {
          var totalItemsPerPage = elementsSumAndReloadIfMore(page1, "xpath="+xpath.PRODUCT, 12 );
          
          
          pressItems(page1, xpath.PRODUCT, totalItemsPerPage); // Loop over each item per page
          
          try
          {
               println("Pressed    PAGE "+pageNumber);
               println("");
               press(page1, xpath.NEXT_PAGE);
               
               pageNumber++;
               
          } catch (Exception e) {
               nextPageArrow = false;
          }
          
     }
     
}





/**
 * Loops over each item on the current page and processes the items individually.
 *
 * @param page2        Playwright Page used to interact with the current browser page.
 * @param locator      XPath locator used to find the items on the page.
 * @param itemsPerPage Maximum expected number of items on the page.
 *                     If more items are loaded than this value, the page is reloaded
 *                     until the number of loaded items is the same as or less than
 *                     the specified maximum.
 *
 * <p>
 * The method also checks whether each item is available, retries missing items,
 * reloads the page when necessary, and continues with the next item when an
 * item cannot be found.
 */
public static void pressItems(Page page2, String locator, int itemsPerPage)
{
     var URL = page2.url();
     int skipped = 0 ;
     for ( int i = 1; i <= itemsPerPage; i++ )
     {
          
          int itemToPress = 0;
          boolean flake = true;
          int pagesReload = 0;
          
          
          
          //  BEFORE loop
          if (!loadSingleElement(page2, locator + "[" + i + "]"))
          {
               println("Item [" + i + "] not found. Reloading.");
               page2.navigate(URL);
          }
          if (!loadSingleElement(page2, locator + "[" + i + "]"))
          {
               
               skipped++;
               if(skipped >2) {
                    break;
               }
               println("Item [" + i + "] not found. Skipping.");
               continue; // skip to next item
          }
          while ( flake )
          {
               boolean loadSingleItem = loadSingleElement(page2, locator+"["+i+"]");
               
               try
               {
                    itemToPress++;
                    if (itemToPress>1){
                         println("      Attempt to Press "+itemToPress);
                    }
                    
                    //println("");
                    
                    press(page2, locator+"["+i+"]");
                    
                    itemPage(); // Framework for pressed item
                    
                    page2.goBack();
                    flake = false;
                    
                    println("3rd loop item"+"["+i+"]");
               
               } catch (Exception e){
                    //printScreen(page2);
                    pagesReload++;
                    println("      Loop "+ i +", items per page "+ itemsPerPage);
                    
                    
                    if( !loadSingleItem ){
                         flake = false;
                    }
                    
                    println("     "+URL + " Page reload = " + pagesReload);
                    println("");
                    page2.navigate(URL);
                    
                    
               }
          }
          
          
     }
     
     
          
}
          
public static void itemPage(){
     // Write program to use PLD with this framework
}

     
     
     

}



