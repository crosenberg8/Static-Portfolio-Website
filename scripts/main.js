console.log('It works!');

$(document).ready(function() {
	$('.submit').click(function(event) {
		console.log('Clicked Button');

		var email = $('.email').val();
		var subject = $('.subject').val();
		var message = $('.message').val();
		var statusElm = $('.status');
		statusElm.empty();

		if (email.length > 5 && email.includes('@') && email.includes('.')) {
			statusElm.append('<div>Email is valid.</div>');
		} else {
			event.preventDefault();
			statusElm.append('<div>Email is not valid.</div>');
		}

		if (subject.length >= 2) {
			statusElm.append('<div>Subject is valid.</div>');
		} else {
			event.preventDefault();
			statusElm.append('<div>Subject must be greater than or equal to 2 characters.</div>');
		}

		if (message.length >= 10) {
			statusElm.append('<div>Message is valid.</div>');
		} else {
			event.preventDefault();
			statusElm.append('<div>Message must be greater than or equal to 10 characters in length.</div>');
		}
	})
})