package model;

import atg.repository.Repository;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.repository.RepositoryView;
import atg.repository.rql.RqlStatement;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

import javax.servlet.ServletException;
import java.io.IOException;

public class BookSearchDroplet extends DynamoServlet {

	private static final String ALL = "ALL";

	private static final String ITEM_DESCRIPTOR = "book";

	private static final String OUTPUT = "output";
	private static final String OUTPUT_EMPTY = "outputEmpty";
	private static final String OUTPUT_START = "outputStart";
	private static final String OUTPUT_END = "outputEnd";

	private static final String PARAM_ITEMS_SIZE = "resultSize";
	private static final String PARAM_ITEM = "bookItem";
	private static final String PARAM_BOOK_TITLE = "title";
	private static final String PARAM_BOOK_GENRE = "genre";

	private static final String QUERY_GENRE_EQ = "genre EQUALS ?0";
	private static final String QUERY_TITLE_CONT = "title CONTAINS ?0";
	private static final String QUERY_TITLE_CONT_AND_GENRE_EQ = "title CONTAINS ?0 AND genre EQUALS ?1";


	private Repository booksRepository;

	public BookSearchDroplet() {
	}

	public Repository getBooksRepository() {
		return this.booksRepository;
	}

	public void setBooksRepository(Repository booksRepository) {
		this.booksRepository = booksRepository;
	}

	private RepositoryItem[] findItemsByTitleAndGenre(String title, String genre) throws RepositoryException {
		RepositoryView view = booksRepository.getItemDescriptor(ITEM_DESCRIPTOR).getRepositoryView();
		String query;
		RepositoryItem[] repositoryItems;

		if (title == null || title.isEmpty()) {
			if (genre.equalsIgnoreCase(ALL)) {
				repositoryItems = RqlStatement.parseRqlStatement(ALL).executeQuery(view, null);
			} else {
				query = QUERY_GENRE_EQ;
				repositoryItems = RqlStatement.parseRqlStatement(query).executeQuery(view, new Object[]{genre});
			}
		} else if (genre.equalsIgnoreCase(ALL)) {
			query = QUERY_TITLE_CONT;
			repositoryItems = RqlStatement.parseRqlStatement(query).executeQuery(view, new Object[]{title});
		} else {
			query = QUERY_TITLE_CONT_AND_GENRE_EQ;
			repositoryItems = RqlStatement.parseRqlStatement(query).executeQuery(view, new Object[]{title, genre});
		}

		return repositoryItems;
	}

	private void processOutput(RepositoryItem[] items,
	                           DynamoHttpServletRequest request,
	                           DynamoHttpServletResponse response)
		throws IOException, ServletException {

		if (items == null || items.length == 0) {
			request.serviceParameter(OUTPUT_EMPTY, request, response);
		} else {
			request.setParameter(PARAM_ITEMS_SIZE, items.length);
			request.serviceParameter(OUTPUT_START, request, response);

			for (RepositoryItem item : items) {
				request.setParameter(PARAM_ITEM, item);
				request.serviceParameter(OUTPUT, request, response);
			}

			request.serviceParameter(OUTPUT_END, request, response);
		}
	}

	@Override
	public void service(DynamoHttpServletRequest request,
	                    DynamoHttpServletResponse response)
		throws ServletException, IOException {
		String title = request.getParameter(PARAM_BOOK_TITLE);
		String genre = request.getParameter(PARAM_BOOK_GENRE);
		RepositoryItem[] items = null;
		try {
			if (genre != null)
				items = findItemsByTitleAndGenre(title, genre);

			processOutput(items, request, response);
		} catch (RepositoryException | NullPointerException ex) {
			logError(ex);
		}
	}
}
