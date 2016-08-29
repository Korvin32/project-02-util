function handleAjaxWithBlockUI(data) {
	console.log("In 'handleAjaxWithBlockUI()'. data = " + JSON.stringify(data));
	
	var status = data.status;
	
	console.log("In 'handleAjaxWithBlockUI()'. Status='" + status + "'");
	
	switch (status) {
	case "begin":
		$(document).ready(function() {
			$('#container').block({ message: '<h4>Waiting for response. Just a moment...</h4>' });
		});
		break;
	case "complete":
		break;
	case "success":
		$(document).ready(function() {
			$('#container').unblock();
		});
		break;
	}
}