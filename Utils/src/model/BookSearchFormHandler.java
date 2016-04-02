package model;

import atg.droplet.GenericFormHandler;

public class BookSearchFormHandler extends GenericFormHandler {
	private String bookTitle;
	private String genre;
	private boolean startSearch = false;

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean getStartSearch() {
		return this.startSearch;
	}

	public void setStartSearch(boolean startSearch) {
		this.startSearch = startSearch;
	}

	public boolean handleSearch() {
		this.startSearch = true;
		return true;
	}
}
