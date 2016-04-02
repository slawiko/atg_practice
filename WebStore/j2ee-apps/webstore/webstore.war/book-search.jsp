<title>Book search</title>

<dsp:page>

	<dsp:importbean bean="/forte/catalog/BookSearchFormHandler"/>

	<dsp:form action="" method="post" synchronized="/forte/catalog/BookSearchFormHandler">
		<label for="search-input">Search:</label>
		<dsp:input id="search-input" type="text" bean="BookSearchFormHandler.bookTitle"/>

		<label for="genre-input">Genre:</label>
		<dsp:select id="genre-input" bean="BookSearchFormHandler.genre">
			<dsp:option value="all">All</dsp:option>
			<dsp:option value="0">Sci-Fi</dsp:option>
			<dsp:option value="1">Fantasy</dsp:option>
			<dsp:option value="2">Novella</dsp:option>
		</dsp:select>

		<dsp:input type="submit" value="search" bean="BookSearchFormHandler.search"/>
	</dsp:form>

	<dsp:droplet name="/forte/catalog/BookSearchDroplet">
		<dsp:param name="genre" bean="BookSearchFormHandler.genre"/>
		<dsp:param name="title" bean="BookSearchFormHandler.bookTitle"/>

		<dsp:oparam name="outputStart">
			<p> We have found <dsp:valueof param="resultSize"/> book(s): </p>
			<ul>
		</dsp:oparam>

		<dsp:oparam name="output">
			<li>
				<dsp:a href="book-detail.jsp">
					<dsp:valueof param="bookItem.title"/>
					<dsp:param name="bookId" param="bookItem.repositoryId"/>
				</dsp:a>
			</li>
		</dsp:oparam>

		<dsp:oparam name="outputEnd">
			</ul>
		</dsp:oparam>

		<dsp:oparam name="outputEmpty">
			<p> Nothing to show
		</dsp:oparam>
	</dsp:droplet>

</dsp:page>