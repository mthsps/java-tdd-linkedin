package com.tddtesting.isbntools;

public class StockManager {

    private ExternalISBNDataService webservice;
    private ExternalISBNDataService databaseService;
    public String getLocatorCode(String isbn) {
        Book book = databaseService.lookup(isbn);
        if (book == null) {
            book = webservice.lookup(isbn);
        }
        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));
        locator.append(book.getAuthor().charAt(0));
        locator.append(book.getTitle().split( " ").length);
        return locator.toString();
    }

    public void setWebService(ExternalISBNDataService webService) {
        this.webservice = webService;
    }

    public void setDatabaseService(ExternalISBNDataService databaseService) {
        this.databaseService = databaseService;
    }
}
