function validateSpecialty(specialty) {
    const specialtyErrorMessage = document.getElementById('specialtyErrorMessage');
    if (!specialty || specialty.trim() === '') {
        specialtyErrorMessage.textContent = 'Please select a specialty.';
        return false;
    } else {
        specialtyErrorMessage.textContent = '';
        return true;
    }
}

function validateUniversityName(universityName) {
    const universityNameErrorMessage = document.getElementById('universityNameErrorMessage');
    if (!universityName || universityName.trim() === '') {
        universityNameErrorMessage.textContent = 'University Name is required.';
        return false;
    } else {
        universityNameErrorMessage.textContent = '';
        return true;
    }
}
function validatePassword(password) {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    const passwordErrorMessage = document.getElementById('passwordErrorMessage');
    if (!passwordRegex.test(password)) {
        passwordErrorMessage.textContent = 'Password must be at least 8 characters long and contain at least one letter and one digit.';
        return false;
    } else {
        passwordErrorMessage.textContent = '';
        return true;
    }
}