function preload(idSpinner, idButtonText) {
    const spinner = document.getElementById(idSpinner);
    const buttonText = document.getElementById(idButtonText);

    spinner.classList.remove('d-none');
    buttonText.textContent = 'Загрузка...';
}

const form = document.getElementById('formPreload');
const submitButton = document.getElementById('submitBtn');

form.addEventListener('submit', function(event) {
    submitButton.disabled = true;
    preload('spinner', 'buttonText')
} );