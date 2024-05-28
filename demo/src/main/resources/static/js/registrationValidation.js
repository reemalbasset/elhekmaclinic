$(document).ready(function() {
    // Form submission validation
    $('form').on('submit', function(event) {
        var isValid = true;

        // Empty field validation
        $('input[type="text"], input[type="password"], input[type="date"]').each(function() {
            var fieldValue = $(this).val().trim();
            if (!fieldValue) {
                $('#' + this.id + 'Error').text('This field is required.');
                isValid = false;
            } else {
                $('#' + this.id + 'Error').text('');
            }
        });
        // Empty name validation
        var name = $('#name').val().trim();
        if (!name) {
            $('#nameError').text('Name is required.');
            isValid = false;
        } else {
            $('#nameError').text('');
        }
        // Username validation
        var username = $('#username').val().trim();
        var regex = /^[a-zA-Z0-9_]+$/;
        if (!regex.test(username)) {
            $('#usernameError').text('Username can only contain letters, numbers, and underscores.');
            isValid = false;
        } else {
            $('#usernameError').text('');
        }

        // Password validation
        var password = $('#password').val();
        var regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
        if (!regex.test(password)) {
            $('#passwordError').text('Password must be at least 8 characters long and contain at least one letter and one number.');
            isValid = false;
        } else {
            $('#passwordError').text('');
        }

        // Confirm Password validation
        var confirmPassword = $('#confirmPassword').val();
        var password = $('#password').val();
        if (confirmPassword !== password) {
            $('#confirmPasswordError').text('Passwords do not match.');
            isValid = false;
        } else {
            $('#confirmPasswordError').text('');
        }

        // Date of Birth validation
        var dob = $('#dob').val();
        var dobDate = new Date(dob);
        var currentDate = new Date();
        var minAgeDate = new Date(currentDate.getFullYear() - 16, currentDate.getMonth(), currentDate.getDate());
        if (dobDate > minAgeDate) {
            $('#dobError').text('You must be at least 16 years old.');
            isValid = false;
        } else {
            $('#dobError').text('');
        }

        if (!isValid) {
            event.preventDefault(); // Prevent form submission if validation fails
        }
    });
});
