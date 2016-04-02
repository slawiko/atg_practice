package model;

import atg.adapter.gsa.EnumPropertyDescriptor;
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

public class BookDetailDroplet extends DynamoServlet {
	private static final String ITEM_DESCRIPTOR = "book";

	private static final String OUTPUT = "output";
	private static final String OUTPUT_EMPTY = "outputEmpty";

	private static final String PARAM_BOOK_ID = "bookId";
	private static final String PARAM_BOOK_TITLE = "title";
	private static final String PARAM_BOOK_NUM_OF_PAGES = "numberOfPages";
	private static final String PARAM_BOOK_GENRE = "genre";
	private static final String PARAM_BOOK_PUBLISH_DATE = "publishingDate";

	private static final String PROP_BOOK_TITLE = "title";
	private static final String PROP_BOOK_NUM_OF_PAGES = "numberOfPages";
	private static final String PROP_BOOK_GENRE = "genre";
	private static final String PROP_BOOK_PUBLISH_DATE = "publishingDate";

	private static final String QUERY_ID_EQ = "id EQUALS ?0";

	private Repository booksRepository;

	public BookDetailDroplet() {
	}

	public Repository getBooksRepository() {
		return this.booksRepository;
	}

	public void setBooksRepository(Repository booksRepository) {
		this.booksRepository = booksRepository;
	}

	private RepositoryItem getBookById(String id) throws RepositoryException {
		RepositoryView view = booksRepository.getItemDescriptor(ITEM_DESCRIPTOR).getRepositoryView();
		RepositoryItem[] result;
		RepositoryItem book;

		result = RqlStatement.parseRqlStatement(QUERY_ID_EQ).executeQuery(view, new Object[]{id});
		if (result != null)
			book = result[0];
		else
			book = null;
		return book;
	}

	private void processOutput(RepositoryItem item,
	                           DynamoHttpServletRequest request,
	                           DynamoHttpServletResponse response) throws ServletException, IOException, RepositoryException {

		if (item == null) {
			request.serviceParameter(OUTPUT_EMPTY, request, response);
		} else {
			request.setParameter(PARAM_BOOK_TITLE, item.getPropertyValue(PROP_BOOK_TITLE));
			request.setParameter(PARAM_BOOK_NUM_OF_PAGES, item.getPropertyValue(PROP_BOOK_NUM_OF_PAGES));
			request.setParameter(PARAM_BOOK_GENRE, ((EnumPropertyDescriptor) item.getItemDescriptor()
				.getPropertyDescriptor(PROP_BOOK_GENRE))
				.getEnumeratedValues()[(int) item.getPropertyValue(PROP_BOOK_GENRE)]);
			request.setParameter(PARAM_BOOK_PUBLISH_DATE, item.getPropertyValue(PROP_BOOK_PUBLISH_DATE));

			request.serviceParameter(OUTPUT, request, response);

		}
	}

	@Override
	public void service(DynamoHttpServletRequest request,
	                    DynamoHttpServletResponse response)
		throws ServletException, IOException {

		String id = request.getParameter(PARAM_BOOK_ID);
		RepositoryItem item;

		if (id != null) {
			try {
				item = getBookById(id);
				processOutput(item, request, response);
			} catch (RepositoryException e) {
				logError(e);
			}
		}
	}
}
