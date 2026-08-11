package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

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
void main() {
     
     page.navigate(xpath.MAIN_PAGE);
     
     for (int menu = 1; menu <= MENU_TOTAL; menu++)
     {
          String menuElement = xpath.TOP_MENU_ELEMENT + "[" + menu + "]";
          
          
          press(page, menuElement);
          println(page.url() + " " + menuElement);
          
          nextPage(page);
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
     
     public static void nextPage (Page page1)
     {
          int pageNumber = 1;
          boolean nextPageArrow = true;
          
          
          while ( nextPageArrow )
          {
               var totalItemsPerPage = elementsSumAndReloadIfMore(page1, "xpath="+xpath.PRODUCT, 12 );
               
               
               pressItems(page1, xpath.PRODUCT, totalItemsPerPage);
               
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
          
     
     
     
     

}



