package com.tddtesting.isbntools;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

public class StockManagementTests {

    /* Using Stubs instead of Mocks
    ExternalISBNDataService webService = new ExternalISBNDataService() {
        @Override
        public Book lookup(String isbn) {
            return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
        }
    };
    ExternalISBNDataService databaseService = new ExternalISBNDataService() {
        @Override
        public Book lookup(String isbn) {
            return null;
        }
    }; */

    ExternalISBNDataService webService;
    ExternalISBNDataService databaseService;
    StockManager stockManager;

    @Before
    public void setup() {
        webService = mock(ExternalISBNDataService.class);
        databaseService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {

        // Using Mocks
        when(databaseService.lookup(anyString())).thenReturn(null);
        when(webService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDAtaIsPresent() {

        when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup("0140177396");
        verify(webService, never()).lookup(anyString());
        //verify(webService, times(0)).lookup(anyString());
    }


    @Test
    public void webServiceIsUsedIfDataIsNotPresent() {

        when(databaseService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        //verify(databaseService, times(1)).lookup("0140177396");
        verify(databaseService).lookup("0140177396");
        //verify(webService, times(1)).lookup("0140177396");
        verify(webService).lookup("0140177396");

    }

}
