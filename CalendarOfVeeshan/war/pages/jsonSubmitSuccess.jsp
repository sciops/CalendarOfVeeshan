<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>JSON submitted successfully.</h2>
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