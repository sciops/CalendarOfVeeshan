<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<body>
<h2>Kills list curation form</h2><br>
<b>Uncheck</b> the boxes of the kills you want removed and submit.
<!--
<form:form method="POST" commandName="kForm">
<form:checkboxes element="div" items="${formlist}" path="checkedKills" /> 
<p><input type="submit" value="Submit" /><input type="reset" value="Reset" /></p>
</form:form>-->

<!-- https://stackoverflow.com/questions/6873360/spring-form-taglib-checkboxes-and-the-default-value -->
<sf:form modelAttribute="kForm" method="post">
	<form:checkboxes element="div" items="${formlist}" path="checkedKills" itemLabel="wkiLine" itemValue="wkiLine"/>
	<p><input type="submit" value="Submit" /><input type="reset" value="Reset" /></p>
</sf:form>
 
    <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      </tr>
      <tr>
        <td>
        	<a href="calendarofveeshan">CalendarOfVeeshan</a><br>
        	<a href="/raid">raid.php clone</a><br>
        	<a href="/setkills?json=[{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:01:03%20PM%22,%22killClass%22:%22FFA%22},{%22mobName%22:%22Trakanon%22,%22killTime%22:%22Jul%201,%202015%205:06:12%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Lord%20Nagafen%22,%22killTime%22:%22Jul%201,%202015%205:08:43%20PM%22,%22killClass%22:%22Class%20C%22},{%22mobName%22:%22Talendor%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22},{%22mobName%22:%22Venril%20Sathir%22,%22killTime%22:%22Jul%201,%202015%205:09:25%20PM%22,%22killClass%22:%22Class%20R%22}]">
        	Sample data import via JSON</a><br>
        	<a href="/wkiclone">whenikilledit clone</a><br>
        	<a href="/killsform">Curate the list</a><br>
        </td>
      </tr>
    </table>
</body>
</html>