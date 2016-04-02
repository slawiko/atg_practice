<title>Book detail</title>

<dsp:page>
	<dsp:droplet name="/forte/catalog/BookDetailDroplet">
		<dsp:param name="bookId" param="bookId"/>
		<dsp:oparam name="output">
			<h1><dsp:valueof param="title"/></h1>
			<p>Number of page:
					<dsp:valueof param="numberOfPages"/>
			<p>Genre:
					<dsp:valueof param="genre"/>
			<p>Publishing date: <dsp:valueof param="publishingDate"/>
			<%--<p><dsp:valueof param="authors"/>--%>
		</dsp:oparam>
		<dsp:oparam name="outputEmpty">
			<p> This book doesn't exist
		</dsp:oparam>
	</dsp:droplet>
</dsp:page>