package com.tddtesting.isbntools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

public class StockManagementTests {

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
    };

    @Test
    public void testCanGetACorrectLocatorCode() {
        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDAtaIsPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup("0140177396");
        verify(webService, never()).lookup(anyString());
        //verify(webService, times(0)).lookup(anyString());
    }


    @Test
    public void webServiceIsUsedIfDataIsNotPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);

        //verify(databaseService, times(1)).lookup("0140177396");
        verify(databaseService).lookup("0140177396");
        //verify(webService, times(1)).lookup("0140177396");
        verify(webService).lookup("0140177396");

    }

}
