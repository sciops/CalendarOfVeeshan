<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<body>
<h2>FORM</h2>
<form:form method="POST" commandName="kForm">
<form:checkboxes element="div" items="${formlist}" path="checkedKills" />
<p><input type="submit" value="Submit" /><input type="reset" value="Reset" /></p>
</form:form>

</body>
</html>