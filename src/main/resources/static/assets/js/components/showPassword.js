const togglePassword = document
    .querySelector('.togglePassword');
const password = document.querySelector('.password');
togglePassword.addEventListener('click', () => {
    const type = password
        .getAttribute('type') === 'password' ?
        'text' : 'password';
    password.setAttribute('type', type);
});

const duplicatePassword = document.querySelector('.togglePasswordDuplicate');
if(duplicatePassword != null) {
    const passwordElem = document.querySelector('.passwordDuplicate');
    duplicatePassword.addEventListener('click', () => {
        const typeElem = passwordElem.getAttribute('type') === 'password' ?
            'text' : 'password';
        passwordElem.setAttribute('type', typeElem);
    })
}