<title>New fantasy books</title>
<dsp:page>
	<dsp:droplet name="/atg/dynamo/droplet/RQLQueryForEach">
		<dsp:param name="repository" value="/forte/catalog/Books"/>
		<dsp:param name="itemDescriptor" value="book"/>
		<dsp:param name="queryRQL" value="genre = 1 and publishingDate > DATE(\"2015-01-01\") ORDER BY title"/>
		<dsp:oparam name="output">
			<p><dsp:valueof param="element.title"/>
		</dsp:oparam>
	</dsp:droplet>
</dsp:page>