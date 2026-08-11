package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.example.JavaSoftware.println;
import static org.example.Software.loadSingleElement;
import static org.example.Software.press;
import static org.example.Variables.xpath;





@Execution(ExecutionMode.CONCURRENT)
public class MainParallelTest {

     @TestFactory
     Stream<DynamicTest> run5TimesInParallel() {
          
          return IntStream.range(0, 1)
                           .mapToObj(i -> DynamicTest.dynamicTest("Run-" + i, () -> {
                                
                                try (Playwright playwright = Playwright.create())
                                {
                                     
                                     Browser browser = playwright.chromium().launch(
                                               new BrowserType.LaunchOptions().setHeadless(false));
                                     BrowserContext context = browser.newContext();
                                     Page page = context.newPage();
                                     
                                     
                              
                                     page.navigate(xpath.MAIN_PAGE);
                                     
                                     
                                     for (int menu = 1; menu <= xpath.MENU_TOTAL; menu++)
                                     {
                                          String locator = xpath.TOP_MENU_ELEMENT + "[" + menu + "]";
                                          press(page, locator);
                                          println(locator);
                                          loadSingleElement(page, xpath.NEXT_PAGE);
                                          MainTest.nextPage(page);
                                     }

                                     println("Finished Run: " + i);
                                     
                                     browser.close();
                                }
                                
                           }));
     }
}